package com.edu.ruse.studypal.services;

import com.edu.ruse.studypal.controllers.EventsController;
import com.edu.ruse.studypal.dtos.EventGetDto;
import com.edu.ruse.studypal.dtos.EventPostDto;
import com.edu.ruse.studypal.entities.Event;
import com.edu.ruse.studypal.entities.EventTypeEnum;
import com.edu.ruse.studypal.exceptions.NotFoundOrganizationException;
import com.edu.ruse.studypal.mappers.EventMapper;
import com.edu.ruse.studypal.repositories.EventRepository;
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
    private static final int PAGE_SIZE = 2;
    private static final Logger LOGGER = LogManager.getLogger(EventsController.class);

    @Autowired
    public EventService(EventRepository eventRepository, EventMapper eventMapper, SubjectService subjectService) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.subjectService = subjectService;
    }

    public EventGetDto createEvent(EventPostDto eventPostDto) {
        Event event = eventMapper.toEntityFromPostDto(eventPostDto);
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
        return null;
    }

    public void deleteEvent(long id) {
        Optional<Event> toDelete = getEventEntity(id);
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
}
