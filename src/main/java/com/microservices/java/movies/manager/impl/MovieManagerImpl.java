package com.microservices.java.movies.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.java.movies.entity.Movie;
import com.microservices.java.movies.entity.vm.ManagerViewModel;
import com.microservices.java.movies.entity.vm.MovieViewModel;
import com.microservices.java.movies.manager.MovieManager;
import com.microservices.java.movies.repository.MovieRepository;
import com.microservices.java.movies.util.ErrorDetailInfoList;
import com.microservices.java.movies.util.Utils;

@Service
public class MovieManagerImpl extends ErrorDetailInfoList implements MovieManager{
	private static final Logger logger = LogManager.getLogger(MovieManagerImpl.class);
	
	@Autowired 
	MovieRepository movieRepository;
	
	@Override
	public ManagerViewModel<List<MovieViewModel>> getAllMovie(long hashing, int page, int size, String orderby) {
		ManagerViewModel<List<MovieViewModel>> mvm = new ManagerViewModel<>();
		List<MovieViewModel> lvm = new ArrayList<>();
		try {
			List<Movie> listData = movieRepository.getAllMovie(hashing, page, size, orderby);
			if (null != listData && listData.size() > 0) {
		        lvm = listData.stream().map(this::entityToViewModel).collect(Collectors.toList());
				mvm.setContent(lvm);
				mvm.setInfo(getInfoOk("Success"));
				mvm.setTotalRows(lvm.size());
			} else {
				mvm.setContent(null);
				mvm.setInfo(getInfoNoContent("No Data Found"));
				mvm.setTotalRows(0);
			}
		} catch (Exception e) {
			String errMsg = e.getLocalizedMessage();
			logger.error("(" + hashing + ") " + errMsg);
			mvm.setContent(null);
			mvm.setInfo(getInfoConflict("Error"));
			mvm.setTotalRows(0);
		}
        return mvm;
	}

	@Override
	public ManagerViewModel<List<MovieViewModel>> getMovieById(long hashing, int id) {
        ManagerViewModel<List<MovieViewModel>> mvm = new ManagerViewModel<>();
		List<MovieViewModel> lvm = new ArrayList<>();
		try {
			if(isMovieExistById(hashing, id)) {
				List<Movie> listData = movieRepository.getMovieById(hashing, id);
				if (null != listData && listData.size() > 0) {
			        lvm = listData.stream().map(this::entityToViewModel).collect(Collectors.toList());
					mvm.setContent(lvm);
					mvm.setInfo(getInfoOk("Success"));
					mvm.setTotalRows(lvm.size());
				} else {
					mvm.setContent(null);
					mvm.setInfo(getInfoNoContent("No Data Found"));
					mvm.setTotalRows(0);
				}
			} else {
				mvm.setInfo(getInfoBadRequest("Movie By Id "+id+" is not found"));
				mvm.setTotalRows(0);
				mvm.setContent(null);
			}
		} catch (Exception e) {
			String errMsg = e.getLocalizedMessage();
			logger.error("(" + hashing + ") " + errMsg);
			mvm.setContent(null);
			mvm.setInfo(getInfoConflict("Error"));
			mvm.setTotalRows(0);
		}
        return mvm;
	}

	@Override
	public ManagerViewModel<MovieViewModel> insertMovie(long hashing, MovieViewModel vm) {
        ManagerViewModel<MovieViewModel> mvm = new ManagerViewModel<>();
        try {
        	ManagerViewModel<Boolean> validateMvm = validateDataRequest(1, vm);
        	if(validateMvm.getContent().booleanValue()) {
            	vm.setId(null);
            	Movie movie = viewModelToEntity(vm);
    			Movie newData = movieRepository.saveMovie(hashing, movie);
        		if(newData.getId() != null) {
            		BeanUtils.copyProperties(newData, vm);
    				mvm.setContent(vm);
    				mvm.setInfo(getInfoOk("Success"));
    				mvm.setTotalRows(1);
        		} else {
    				mvm.setContent(null);
    				mvm.setInfo(getInfoInternalServerError("Id failed to be created"));
    				mvm.setTotalRows(0);
        		}
        	} else {
        		mvm.setContent(null);
        		mvm.setInfo(validateMvm.getInfo());
        		mvm.setTotalRows(0);
        	}
		} catch (Exception e) {
			String errMsg = e.getLocalizedMessage();
			logger.error("(" + hashing + ") " + errMsg);
			mvm.setContent(null);
			mvm.setInfo(getInfoConflict("Error"));
			mvm.setTotalRows(0);
		}
        return mvm;
	}

	@Override
	public ManagerViewModel<MovieViewModel> updateMovieById(long hashing, int id, MovieViewModel vm) {
        ManagerViewModel<MovieViewModel> mvm = new ManagerViewModel<>();
        try {
			if(isMovieExistById(hashing, id)) {
				vm.setId(id);
	        	ManagerViewModel<Boolean> validateMvm = validateDataRequest(2, vm);
	        	if(validateMvm.getContent().booleanValue()) {
	            	Movie movie = viewModelToEntity(vm);
	    			Movie newData = movieRepository.updateMovie(hashing, movie);
	        		BeanUtils.copyProperties(newData, vm);
					mvm.setContent(vm);
					mvm.setInfo(getInfoOk("Success"));
					mvm.setTotalRows(1);
	        	} else {
	        		mvm.setContent(null);
	        		mvm.setInfo(validateMvm.getInfo());
	        		mvm.setTotalRows(0);
	        	}
			} else {
				mvm.setInfo(getInfoBadRequest("Movie By Id "+id+" is not found"));
				mvm.setTotalRows(0);
				mvm.setContent(null);
			}
		} catch (Exception e) {
			String errMsg = e.getLocalizedMessage();
			logger.error("(" + hashing + ") " + errMsg);
			mvm.setContent(null);
			mvm.setInfo(getInfoConflict("Error"));
			mvm.setTotalRows(0);
		}
        return mvm;
	}

	@Override
	public ManagerViewModel<MovieViewModel> deleteMovieById(long hashing, int id) {
		ManagerViewModel<MovieViewModel> mvm = new ManagerViewModel<>();
        try {
			if(isMovieExistById(hashing, id)) {
				boolean status = movieRepository.deleteMovieById(hashing, id);
	    		if(status == true) {
					mvm.setContent(null);
					mvm.setInfo(getInfoOk("Success"));
					mvm.setTotalRows(0);
	    		} else {
					mvm.setContent(null);
					mvm.setInfo(getInfoConflict("Failed To Delete"));
					mvm.setTotalRows(0);
	    		}
			} else {
				mvm.setInfo(getInfoBadRequest("Movie By Id "+id+" is not found"));
				mvm.setTotalRows(0);
				mvm.setContent(null);
			}
		} catch (Exception e) {
			String errMsg = e.getLocalizedMessage();
			logger.error("(" + hashing + ") " + errMsg);
			mvm.setContent(null);
			mvm.setInfo(getInfoConflict("Error"));
			mvm.setTotalRows(0);
		}
        return mvm;
	}
	
	@Override
	public boolean isMovieExistById(long hashing, int id) {
		return movieRepository.isMovieExistById(hashing, id);
	}
	
	@Override
	public MovieViewModel entityToViewModel(Movie entity) {
    	MovieViewModel vm = new MovieViewModel();
		BeanUtils.copyProperties(entity, vm);
		vm.setCreatedAt(Utils.convertDateToString(entity.getCreatedAt()));
		vm.setUpdatedAt(Utils.convertDateToString(entity.getUpdatedAt()));
        return vm;
    }

	@Override
	public Movie viewModelToEntity(MovieViewModel vm) {
		Movie entity = new Movie();
		BeanUtils.copyProperties(vm, entity);
		entity.setCreatedAt(Utils.convertStringToDate(vm.getCreatedAt()));
		entity.setUpdatedAt(Utils.convertStringToDate(vm.getUpdatedAt()));
		return entity;
	}
	
	private ManagerViewModel<Boolean> validateDataRequest(int requestType, MovieViewModel vm) {
		ManagerViewModel<Boolean> mvm = new ManagerViewModel<Boolean>();
		mvm.setContent(false);
		mvm.setTotalRows(0);
		if(vm.getRating() < 0.0 || vm.getRating() > 10.0) {
			mvm.setInfo(getInfoBadRequest("Rating range is 0 - 10"));
		} else if(!Utils.isValid(vm.getCreatedAt())&& requestType == 1) {
			mvm.setInfo(getInfoBadRequest("Created At fromat is yyyy-MM-dd HH:mm:ss"));
		}else if(!Utils.isValid(vm.getUpdatedAt()) && requestType == 2) {
			mvm.setInfo(getInfoBadRequest("Updated At fromat is yyyy-MM-dd HH:mm:ss"));
		} else {
			mvm.setContent(true);
		}
		return mvm;
	}

}
