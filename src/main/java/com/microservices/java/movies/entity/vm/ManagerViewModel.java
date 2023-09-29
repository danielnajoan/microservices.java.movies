package com.microservices.java.movies.entity.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microservices.java.movies.util.ErrorDetail;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ManagerViewModel<T> {
    @JsonProperty("info")
	private ErrorDetail info;
    @JsonProperty("total_rows")
    private int totalRows;
    @JsonProperty("content")
	private T content;
}