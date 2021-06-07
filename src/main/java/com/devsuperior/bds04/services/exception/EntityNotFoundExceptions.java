package com.devsuperior.bds04.services.exception;

public class EntityNotFoundExceptions extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public EntityNotFoundExceptions(String msg) {
		super(msg);
	}

}
