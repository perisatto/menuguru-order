package com.perisatto.fiapprj.menuguru_order.handler.errors;

import com.fasterxml.jackson.annotation.JsonFormat;

public class HttpErrorHandler {
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private String type;
	private String title;
	private String detail;
	private String instance;
	
	public HttpErrorHandler(String title, String detail, String instance) {
		this.type = "about:blank";
		this.title = title;
		this.detail = detail;
		this.setInstance(instance);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	
	
}
