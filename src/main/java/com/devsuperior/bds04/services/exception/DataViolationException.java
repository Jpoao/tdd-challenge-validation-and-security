package com.devsuperior.bds04.services.exception;

public class DataViolationException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public DataViolationException(String msg){
		super(msg);
	}
}
