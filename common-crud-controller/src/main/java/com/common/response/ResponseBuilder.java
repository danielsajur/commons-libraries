package com.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

public class ResponseBuilder<R> {

	private BuilderError builderError;

	private BuilderSuccess builderSuccess;

	public BuilderSuccess withData(R data) {
		return withData(Arrays.asList(data));
	}

	public BuilderSuccess withData(List<R> data) {
		if (builderSuccess == null) {
			builderSuccess = new BuilderSuccess(data);
		}
		return builderSuccess;
	}
	
	public BuilderSuccess withMessage(String message) {
		if (builderSuccess == null) {
			builderSuccess = new BuilderSuccess(message);
		}
		return builderSuccess;
	}

	public BuilderError withError(Error error) {
		if (builderError == null) {
			builderError = new BuilderError(error);
		}
		return builderError;
	}

	public ResponseBuilder() {
		
	}

	public abstract class Builder {

		protected HttpStatus http;

		protected Response<R> response;

		public Builder withHttp(HttpStatus http) {
			this.http = http;
			return this;
		}

		public ResponseEntity<Response<R>> build() {
			return ResponseEntity.status(http).body(response);
		}
	}

	public class BuilderError extends Builder {

		public BuilderError(Error error) {
			response = new Response<>();
		}

	}

	public class BuilderSuccess extends Builder {

		public BuilderSuccess(List<R> data) {
			response = new Response<>();
			response.getData().addAll(data);
		}
		
		public BuilderSuccess(String message) {			
			
			if(response == null) {
				response = new Response<>();
				return;
			}
			
			response.add(message);
		}
		public BuilderSuccess withMessage(String message) {
			
			if(response == null) {
				throw new IllegalArgumentException("response == null");
			}
			response.add(message);
			return this;
		}
	}

}
