package com.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Response<R> {

	private String message;

	private final List<R> data = new ArrayList<>();

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private final LocalDateTime timestamp = LocalDateTime.now();

	public void add(R data){
		this.data.add(data);
	}

	public void addAll(List<R> data){
		this.data.addAll(data);
	}

	public List<R> getData(){
		return Collections.unmodifiableList(data);
	}

	public void add(String message) {
		this.message = message;
	}
}
