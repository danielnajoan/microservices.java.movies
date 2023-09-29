package com.microservices.java.movies.repository.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import jakarta.persistence.Query;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import com.microservices.java.movies.entity.Movie;
import com.microservices.java.movies.repository.MovieRepository;

@SuppressWarnings({"unchecked"})
@Repository
public class MovieRepositoryImpl implements MovieRepository {
	private static final Logger logger = LogManager.getLogger(MovieRepositoryImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Movie> getAllMovie(long hashing, int page, int size, String orderby) {
		try {
	        String sqlQuery = "SELECT * FROM MOVIE ORDER BY " + orderby + " LIMIT :size OFFSET :offset";
	        
	        Query query = entityManager.createNativeQuery(sqlQuery, Movie.class);
	        query.setParameter("size", size);
	        query.setParameter("offset", (page - 1) * size);

	        return query.getResultList();
		} catch (Exception e) {
			String errMsg = e.getLocalizedMessage();
			logger.error("(" + hashing + ") " + errMsg);
		}
		return null;
	}

	@Override
	public List<Movie> getMovieById(long hashing, int id) {
		try {
	        String sqlQuery = "SELECT * FROM MOVIE WHERE ID = :id";

	        Query query = entityManager.createNativeQuery(sqlQuery, Movie.class);
	        query.setParameter("id", id);

	        List<Movie> resultList = query.getResultList();
	        return resultList;
		} catch (Exception e) {
			String errMsg = e.getLocalizedMessage();
			logger.error("(" + hashing + ") " + errMsg);
		}
		return null;
	}

	@Override
	public Movie saveMovie(long hashing, Movie movie) {
		try {
	        String sqlQuery = "INSERT INTO MOVIE (ID, TITLE, DESCRIPTION, RATING, IMAGE, CREATED_AT, UPDATED_AT) " +
	                "VALUES (nextval('movie_id_seq'), :title, :description, :rating, :image, :createdAt, :updatedAt) " +
	                "RETURNING *";

	        Query query = entityManager.createNativeQuery(sqlQuery, Movie.class);
	        query.setParameter("title", movie.getTitle());
	        query.setParameter("description", movie.getDescription());
	        query.setParameter("rating", movie.getRating());
	        query.setParameter("image", movie.getImage());
	        query.setParameter("createdAt", movie.getCreatedAt());
	        query.setParameter("updatedAt", movie.getUpdatedAt());

	        return (Movie) query.getSingleResult();
		} catch (Exception e) {
			String errMsg = e.getLocalizedMessage();
			logger.error("(" + hashing + ") " + errMsg);
		}
		return null;
	}

	@Override
	public Movie updateMovie(long hashing, Movie movie) {
		try {
	        String sqlQuery = "UPDATE MOVIE SET TITLE = :title, DESCRIPTION = :description, " +
	                "RATING = :rating, IMAGE = :image, UPDATED_AT = :updatedAt WHERE ID = :id " +
	                "RETURNING *";

	        Query query = entityManager.createNativeQuery(sqlQuery, Movie.class);
	        query.setParameter("id", movie.getId());
	        query.setParameter("title", movie.getTitle());
	        query.setParameter("description", movie.getDescription());
	        query.setParameter("rating", movie.getRating());
	        query.setParameter("image", movie.getImage());
	        query.setParameter("updatedAt", movie.getUpdatedAt());

	        return (Movie) query.getSingleResult();
		} catch (Exception e) {
			String errMsg = e.getLocalizedMessage();
			logger.error("(" + hashing + ") " + errMsg);
		}
		return null;
	}

	@Override
	public boolean deleteMovieById(long hashing, int id) {
		try {
	        String sqlQuery = "DELETE FROM MOVIE WHERE ID = :id";

	        Query query = entityManager.createNativeQuery(sqlQuery);
	        query.setParameter("id", id);

	        int rowsAffected = query.executeUpdate();

	        return rowsAffected > 0;
		} catch (Exception e) {
			String errMsg = e.getLocalizedMessage();
			logger.error("(" + hashing + ") " + errMsg);
		}
		return false;
	}

	@Override
	public boolean isMovieExistById(long hashing, int id) {
		try {
	        String sqlQuery = "SELECT COUNT(*) FROM MOVIE WHERE ID = :id";

	        Query query = entityManager.createNativeQuery(sqlQuery);
	        query.setParameter("id", id);

	        int count = ((Number) query.getSingleResult()).intValue();

	        return count > 0;
		} catch (Exception e) {
			String errMsg = e.getLocalizedMessage();
			logger.error("(" + hashing + ") " + errMsg);
		}
		return false;
	}
}
