package com.devsuperior.bds04.controller.exceptions;

import java.io.Serializable;

public class FieldMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	private String fieldName;
	private String message;

	public FieldMessage() {

	}

	public FieldMessage(String fieldname, String message) {
		super();
		fieldName = fieldname;
		this.message = message;
	}


	public String getfieldName() {
		return fieldName;
	}

	public void setfieldName(String fieldname) {
		fieldName = fieldname;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}