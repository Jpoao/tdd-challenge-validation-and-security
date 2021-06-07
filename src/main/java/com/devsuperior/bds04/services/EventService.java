package com.devsuperior.bds04.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.repositories.EventRepository;

@Service
public class EventService {

	@Autowired
	private EventRepository repository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Transactional(readOnly = true)
	public Page<EventDTO> findAllPaged(Pageable pageRequest){
		Page<Event> result = repository.findAll(pageRequest);
		return result.map(x -> new EventDTO(x));
	}
	
	@Transactional
	public EventDTO insert(EventDTO newEvent) {
		Event event = new Event();
		City city = cityRepository.getOne(newEvent.getCityId());
		event.setCity(city);
		event.setDate(newEvent.getDate());
		event.setName(newEvent.getName());
		event.setUrl(newEvent.getUrl());
		repository.save(event);
		return new EventDTO(event);
	}
}
