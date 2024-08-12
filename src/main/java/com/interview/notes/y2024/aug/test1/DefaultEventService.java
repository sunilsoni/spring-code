package com.interview.notes.y2024.aug.test1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class DefaultEventService implements EventService {
    private final EventRepository eventRepository;

    @Autowired
    DefaultEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    @Override
    public List<Event> top3By(String by) {
        if (!isValidAttribute(by)) {
            throw new IllegalArgumentException("Invalid attribute: " + by);
        }
        List<Event> allEvents = eventRepository.findAll();
        allEvents.sort((e1, e2) -> {
            if (by.equals("cost")) {
                return e2.getCost().compareTo(e1.getCost());
            } else {
                return e2.getDuration().compareTo(e1.getDuration());
            }
        });
        return allEvents.stream().limit(3).collect(Collectors.toList());
    }

    @Override
    public Integer totalBy(String by) {
        if (!isValidAttribute(by)) {
            throw new IllegalArgumentException("Invalid attribute: " + by);
        }
        List<Event> allEvents = eventRepository.findAll();
        return allEvents.stream()
                .mapToInt(event -> by.equals("cost") ? event.getCost() : event.getDuration())
                .sum();
    }

    private boolean isValidAttribute(String by) {
        return "cost".equals(by) || "duration".equals(by);
    }
}
