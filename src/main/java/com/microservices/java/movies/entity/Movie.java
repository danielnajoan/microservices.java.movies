package com.microservices.java.movies.entity;

import java.util.Date;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
@Entity
@Table(name = "MOVIE")
public class Movie {
	@Id
	@SequenceGenerator(name = "movie_seq", sequenceName = "movie_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_seq")
	@Column(name = "ID")
    private Integer id;
	@Column(name = "TITLE")
    private String title;
	@Column(name = "DESCRIPTION")
    private String description;
	@Column(name = "RATING")
    private Float rating;
	@Column(name = "IMAGE")
    private String image;
	@Column(name = "CREATED_AT")
    private Date createdAt;
	@Column(name = "UPDATED_AT")
    private Date updatedAt;
}
