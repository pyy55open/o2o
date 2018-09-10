package com.csy.o2o.dto;

import java.io.InputStream;

public class ImgHolder {

	private String name;
	private InputStream inputStream;
	
	public ImgHolder(String name,InputStream inputStream) {
		this.name=name;
		this.inputStream=inputStream;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	
}
