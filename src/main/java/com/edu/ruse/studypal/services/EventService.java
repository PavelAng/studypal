package com.edu.ruse.studypal.services;

import com.edu.ruse.studypal.controllers.AuthController;
import com.edu.ruse.studypal.controllers.EventsController;
import com.edu.ruse.studypal.dtos.EventGetDto;
import com.edu.ruse.studypal.dtos.EventPostDto;
import com.edu.ruse.studypal.dtos.FileGetDto;
import com.edu.ruse.studypal.dtos.FilePostDto;
import com.edu.ruse.studypal.entities.*;
import com.edu.ruse.studypal.exceptions.NotFoundOrganizationException;
import com.edu.ruse.studypal.mappers.EventMapper;
import com.edu.ruse.studypal.mappers.FileMapper;
import com.edu.ruse.studypal.repositories.EventRepository;
import com.edu.ruse.studypal.security.jwt.JwtUtils;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author anniexp
 */
@Service
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final SubjectService subjectService;
    private static final int PAGE_SIZE = 10;
    private static final Logger LOGGER = LogManager.getLogger(EventsController.class);
    private final FileMapper fileMapper;
    private final FileService fileService;
    private final JwtUtils jwtUtils;

    @Autowired
    public EventService(EventRepository eventRepository, EventMapper eventMapper, SubjectService subjectService, FileMapper fileMapper, FileService fileService, JwtUtils jwtUtils) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.subjectService = subjectService;
        this.fileMapper = fileMapper;
        this.fileService = fileService;
        this.jwtUtils = jwtUtils;
    }

    public EventGetDto createEvent(EventPostDto eventPostDto) {
        Event event = eventMapper.toEntityFromPostDto(eventPostDto);
        isUserTeacherOfDiscipline(event);

        subjectService.getSubjectById(eventPostDto.getSubjectId());
        setType(eventPostDto, event);


        Event savedEntity = eventRepository.save(event);
        EventGetDto res = eventMapper.toDto(savedEntity);
        res.setSubjectGetDto(subjectService.getSubjectById(eventPostDto.getSubjectId()));
        LOGGER.info("Created Event with id = " + res.getEventId());

        return res;
    }

    private void setType(EventPostDto eventPostDto, Event event) {
        if (eventPostDto.getType().equalsIgnoreCase("TYPE_EXAM")) {
            event.setType(EventTypeEnum.TYPE_EXAM);
        } else if (eventPostDto.getType().equalsIgnoreCase("TYPE_CLASS")) {
            event.setType(EventTypeEnum.TYPE_CLASS);
        } else if (eventPostDto.getType().equalsIgnoreCase("TYPE_OTHER")) {
            event.setType(EventTypeEnum.TYPE_OTHER);
        } else throw new NotFoundOrganizationException("Type of event not found!");
    }

    public List<EventGetDto> getAllEvents(int page) {
        Pageable pageable = Pageable.ofSize(PAGE_SIZE).withPage(page);
        Page<Event> all = eventRepository.findAll(pageable);
        List<EventGetDto> res = all.getContent().stream().map(eventMapper::toDto).collect(Collectors.toList());

        return res;
    }

    public EventGetDto getEventById(long id) {
        Optional<Event> eventOptional = eventRepository.findById(id);

        if (eventOptional.isEmpty()) {
            throw new NotFoundOrganizationException("Could not extract Event entity with id " + id);
        }
        Event event = eventOptional.get();
        System.out.println(event.getType());

        return eventMapper.toDto(event);
    }

    //to do
    public EventGetDto updateEvent(long id, EventPostDto eventPostDto) {
        Event event = eventMapper.toEntityFromPostDto(eventPostDto);
        isUserTeacherOfDiscipline(event);
        return null;
    }

    public void deleteEvent(long id) {
        Optional<Event> toDelete = getEventEntity(id);
        isUserTeacherOfDiscipline(toDelete.get());

        eventRepository.delete(toDelete.get());
    }

    private Optional<Event> getEventEntity(long id) {
        Optional<Event> toUpdate = eventRepository.findById(id);

        if (toUpdate.isEmpty()) {
            LOGGER.error("Event with id {} not found", id);
            throw new NotFoundOrganizationException("Event not found");
        }

        return toUpdate;
    }

    /**
     * @return current logged user visible events
     */
    public List<EventGetDto> getUserEvents() {
        String jwt = AuthController.jwt;
        return jwtUtils.getUserEvents(jwt).stream().map(eventMapper::toDto).toList();
    }

    /**
     * checks if current events' discipline is teached by teacher
     *
     * @param event - current event, for which we check if teacher have permission to crud
     * @return - does teacher have permission to crud event
     */
    public boolean isUserTeacherOfDiscipline(Event event) {
        String jwt = AuthController.jwt;
        Subject subject = event.getSubject();

        boolean doesTeacherHavePermission =
                jwtUtils.getUserFromToken(jwt).getTeachesSubjects().stream()
                        .filter(teacherSubject -> teacherSubject.equals(subject))

                        .toList().size() > 0;
        return doesTeacherHavePermission;
    }

    /**
     * create and add  file to event
     *
     * @param file
     * @param
     */
    public EventGetDto addMaterialToEvent(MultipartFile file, EventGetDto eventGetDto,
                                          @RequestBody String filePath) throws IOException {
        //set filePostDto
        FilePostDto newFile = generateFilePostDto(file, filePath);
        File dto = fileService.createFile(newFile);

        return setMaterialToEvent(eventMapper.toEntity(eventGetDto), dto);
    }

    public EventGetDto addExerciseToEvent(MultipartFile file, EventGetDto eventGetDto,
                                          @RequestBody String filePath) throws IOException {
        //set filePostDto
        FilePostDto newFile = generateFilePostDto(file, filePath);
        File dto = fileService.createFile(newFile);

        return setExerciseToEvent(eventMapper.toEntity(eventGetDto), dto);
    }

    public EventGetDto addSolutionToEvent(MultipartFile file, EventGetDto eventGetDto,
                                          @RequestBody String filePath) throws IOException {
        //set filePostDto
        FilePostDto newFile = generateFilePostDto(file, filePath);
        File dto = fileService.createFile(newFile);

        return setSolutionToEvent(eventMapper.toEntity(eventGetDto), dto);
    }


    private FilePostDto generateFilePostDto(MultipartFile file, String filePath) throws IOException {
        FilePostDto newFile = new FilePostDto();
        String fileName = file.getOriginalFilename();
        newFile.setFileName(fileName);
        newFile.setFileContent(file.getBytes());
        newFile.setFilePath(filePath);

        return newFile;
    }

    private EventGetDto setMaterialToEvent(Event event, File savedFile) {
        List<File> materials = event.getEventMaterials();
        if (materials == null) {
            materials = new ArrayList<>();
        }
        materials.add(savedFile);
        event.setEventMaterials(materials);
        Event res = eventRepository.save(event);
        return eventMapper.toDto(res);
    }

    private EventGetDto setExerciseToEvent(Event event, File savedFile) {
        List<File> materials = event.getEventExercises();
        materials.add(savedFile);
        event.setEventExercises(materials);
        Event res = eventRepository.save(event);
        return eventMapper.toDto(res);
    }

    private EventGetDto setSolutionToEvent(Event event, File savedFile) {
        List<File> materials = event.getEventSolutions();
        materials.add(savedFile);
        event.setEventSolutions(materials);
        Event res = eventRepository.save(event);
        return eventMapper.toDto(res);
    }

    public List<FileGetDto> getMaterialsGetDtos(long id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new NotFoundOrganizationException("Event Not Found"));
        return event.getEventMaterials().stream().map(fileMapper::toDto).collect(Collectors.toList());
    }

    public List<FileGetDto> getExercisesGetDtos(long id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new NotFoundOrganizationException("Event Not Found"));
        return event.getEventExercises().stream().map(fileMapper::toDto).collect(Collectors.toList());
    }

    public List<FileGetDto> getSolutionGetDtos(long id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new NotFoundOrganizationException("Event Not Found"));
        return event.getEventSolutions().stream().map(fileMapper::toDto).collect(Collectors.toList());
    }
}
