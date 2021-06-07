package com.devsuperior.bds04.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.RoleDTO;
import com.devsuperior.bds04.dto.UserDTO;
import com.devsuperior.bds04.dto.UserInsertDTO;
import com.devsuperior.bds04.entities.Role;
import com.devsuperior.bds04.entities.User;
import com.devsuperior.bds04.repositories.RoleRepository;
import com.devsuperior.bds04.repositories.UserRepository;
import com.devsuperior.bds04.services.exception.DataViolationException;
import com.devsuperior.bds04.services.exception.EntityNotFoundExceptions;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public Page<UserDTO> findAllPaged(Pageable pageable) {
		Page<User> result = repository.findAll(pageable);
		return result.map(x -> new UserDTO(x));
	}

	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {		
		Optional<User> obj = repository.findById(id);
		User entity = obj.orElseThrow(() -> new EntityNotFoundExceptions("Entity not found"));
		return new UserDTO(entity);

	}

	@Transactional
	public UserDTO insert(UserInsertDTO dto) {
		User entity = new User();
		copyDtoToentity(dto, entity);
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		entity = repository.save(entity);
		return new UserDTO(entity);
	}

	@Transactional
	public UserDTO update(Long id, UserDTO dto) {
		try {
			User entity = repository.getOne(id);
			copyDtoToentity(dto, entity);
			entity = repository.save(entity);
			return new UserDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundExceptions("sorry id: " + id + " not found");
		}
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			throw new EntityNotFoundExceptions("sorry id: " + id + " not found");
		}
		catch(DataIntegrityViolationException e) {
			throw new DataViolationException("Integrity violation ");
		}
	}

	public void copyDtoToentity(UserDTO dto, User entity) {
		entity.setEmail(dto.getEmail());

		entity.getRoles().clear();
		for(RoleDTO roleDto : dto.getRoles()) {
			Role role = roleRepository.getOne(roleDto.getId());
			entity.getRoles().add(role);
		}
	}


}