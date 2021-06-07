package com.devsuperior.bds04.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.services.EventService;

@RestController
@RequestMapping(value = "/events")
public class EventController {

	@Autowired
	private EventService service;
	
	@GetMapping
	public ResponseEntity<Page<EventDTO>> findAll(Pageable pageable){
		Page<EventDTO> result = service.findAllPaged(pageable);
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping
	public ResponseEntity<EventDTO> insert(@RequestBody EventDTO newEvent){
		EventDTO eventDTO = service.insert(newEvent);
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("{/id}").buildAndExpand(eventDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).body(eventDTO);
	}

	
}
