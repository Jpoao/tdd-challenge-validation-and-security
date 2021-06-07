package com.devsuperior.bds04.controller.exceptions;

import java.io.Serializable;

public class FieldMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	private String Fieldname;
	private String message;

	public FieldMessage() {

	}

	public FieldMessage(String fieldname, String message) {
		super();
		Fieldname = fieldname;
		this.message = message;
	}


	public String getFieldname() {
		return Fieldname;
	}

	public void setFieldname(String fieldname) {
		Fieldname = fieldname;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}