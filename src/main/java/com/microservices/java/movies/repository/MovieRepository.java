package com.microservices.java.movies.repository;

import java.util.List;

import com.microservices.java.movies.entity.Movie;

public interface MovieRepository {
	public List<Movie> getAllMovie(long hashing, int page, int size, String orderby);
	public List<Movie> getMovieById(long hashing, int id);
	public Movie saveMovie(long hashing, Movie movie);
	public Movie updateMovie(long hashing, Movie movie);
	public boolean deleteMovieById(long hashing, int id);
	public boolean isMovieExistById(long hashing, int id);
}
