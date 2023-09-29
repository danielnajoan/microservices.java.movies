package com.microservices.java.movies.manager;

import java.util.List;

import com.microservices.java.movies.entity.Movie;
import com.microservices.java.movies.entity.vm.ManagerViewModel;
import com.microservices.java.movies.entity.vm.MovieViewModel;

public interface MovieManager {
	public ManagerViewModel<List<MovieViewModel>> getAllMovie(long hashing, int page, int size, String orderby);
	public ManagerViewModel<List<MovieViewModel>> getMovieById(long hashing, int id);
	public ManagerViewModel<MovieViewModel> insertMovie(long hashing, MovieViewModel vm);
	public ManagerViewModel<MovieViewModel> updateMovieById(long hashing, int id, MovieViewModel vm);
	public ManagerViewModel<MovieViewModel> deleteMovieById(long hashing, int id);
	public boolean isMovieExistById(long hashing, int id);
	public MovieViewModel entityToViewModel(Movie entity);
	public Movie viewModelToEntity(MovieViewModel vm);
}
