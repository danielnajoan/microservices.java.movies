package com.microservices.java.movies.entity.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MovieViewModel {
	@JsonProperty("id")
    private Integer id;
	@JsonProperty("title")
    @NotBlank(message = "Title is Empty")
    @NotNull(message = "Title is NULL")
    private String title;
	@JsonProperty("description")
    @NotNull(message = "Description is NULL")
    private String description;
	@JsonProperty("rating")
    @NotNull(message = "Rating is NULL")
    private Float rating;
	@JsonProperty("image")
    @NotNull(message = "Image is NULL")
    private String image;
	@JsonProperty("created_at")
    @NotNull(message = "Created At is NULL")
    private String createdAt;
	@JsonProperty("updated_at")
    @NotNull(message = "Updated At is NULL")
    private String updatedAt;
}
