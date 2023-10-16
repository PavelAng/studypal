package com.edu.ruse.studypal.services;

import com.edu.ruse.studypal.controllers.AuthController;
import com.edu.ruse.studypal.controllers.EventsController;
import com.edu.ruse.studypal.dtos.EventGetDto;
import com.edu.ruse.studypal.dtos.EventPostDto;
import com.edu.ruse.studypal.dtos.SubjectGetDto;
import com.edu.ruse.studypal.entities.Event;
import com.edu.ruse.studypal.entities.EventTypeEnum;
import com.edu.ruse.studypal.entities.RoleEnum;
import com.edu.ruse.studypal.entities.Subject;
import com.edu.ruse.studypal.exceptions.NotFoundOrganizationException;
import com.edu.ruse.studypal.mappers.EventMapper;
import com.edu.ruse.studypal.repositories.EventRepository;
import com.edu.ruse.studypal.security.jwt.JwtUtils;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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
    private static final int PAGE_SIZE = 5;
    private static final Logger LOGGER = LogManager.getLogger(EventsController.class);
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    public EventService(EventRepository eventRepository, EventMapper eventMapper, SubjectService subjectService) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.subjectService = subjectService;
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
        } else if  (eventPostDto.getType().equalsIgnoreCase("TYPE_CLASS")) {
            event.setType(EventTypeEnum.TYPE_CLASS);
        } else if (eventPostDto.getType().equalsIgnoreCase("TYPE_OTHER")) {
            event.setType(EventTypeEnum.TYPE_OTHER);
        } else throw new NoSuchElementException("Type of event not found!");
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
            throw new EntityNotFoundException("Event not found");
        }

        return toUpdate;
    }

    /**
     *
     * @return current logged user visible events
     */
    public List<EventGetDto> getUserEvents() {
        String jwt = AuthController.jwt;
       return jwtUtils.getUserEvents(jwt).stream().map(eventMapper::toDto).toList();
    }

    /**
     * checks if current events' discipline is teached by teacher
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
}
