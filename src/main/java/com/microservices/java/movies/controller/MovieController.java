package com.microservices.java.movies.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.microservices.java.movies.entity.vm.ManagerViewModel;
import com.microservices.java.movies.entity.vm.MovieViewModel;
import com.microservices.java.movies.manager.MovieManager;
import com.microservices.java.movies.util.Utils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@Tag(name = "Movie", description = "Operations pertaining to Movie")
@RequestMapping("/movies")
public class MovieController {
	private static final Logger logger = LogManager.getLogger(MovieController.class);
	
	@Autowired
    private MovieManager movieManager;

	private long hashing;

	private void setCurrentHashing() {
		this.hashing = new Date().getTime();
	}
	
    @Operation(summary = "Get All Movie", description = "Get All Movie", tags = "Movie")
	@ApiResponses(value = {
	           @ApiResponse(responseCode = "200", description = "Successful Operation", 
	                   content = @Content(schema = @Schema(implementation = ManagerViewModel.class)))})
	@GetMapping("/")
	public ResponseEntity<ManagerViewModel<List<MovieViewModel>>> getAllMovies(
			@Parameter(description = "Page")
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@Parameter(description = "Size")
			@RequestParam(value = "size", required = false, defaultValue = "10") int size,
			@Parameter(description = "Order By")
			@RequestParam(value = "orderby", required = false, defaultValue = "id") String orderby){
    	setCurrentHashing();
		long startTime = System.currentTimeMillis();
		logger.info("("+hashing+") Get All Movie");
		ManagerViewModel<List<MovieViewModel>> mvm = movieManager.getAllMovie(hashing, page, size, orderby);
		long endTime = System.currentTimeMillis();
		mvm.getInfo().setExecutiontime(Utils.executiontime(startTime, endTime));
		return new ResponseEntity<ManagerViewModel<List<MovieViewModel>>>(mvm, HttpStatus.OK);
	}
    
    @Operation(summary = "Get Movie By Id", description = "Get Movie By Id", tags = "Movie")
	@ApiResponses(value = {
	           @ApiResponse(responseCode = "200", description = "Successful Operation", 
	                   content = @Content(schema = @Schema(implementation = ManagerViewModel.class)))})
    @GetMapping("/{id}")
    public ResponseEntity<ManagerViewModel<List<MovieViewModel>>> getMovieById(
    		@Parameter(description = "Id Value", required = true) @PathVariable("id") int id) {
    	setCurrentHashing();
		long startTime = System.currentTimeMillis();
		logger.info("("+hashing+") Get Movie By " + id);
		ManagerViewModel<List<MovieViewModel>> mvm = movieManager.getMovieById(hashing, id);
		long endTime = System.currentTimeMillis();
		mvm.getInfo().setExecutiontime(Utils.executiontime(startTime, endTime));
		return new ResponseEntity<ManagerViewModel<List<MovieViewModel>>>(mvm, HttpStatus.OK);
    }
    
    @Operation(summary = "Create Movie", description = "Create Movie", tags = "Movie")
	@ApiResponses(value = {
	           @ApiResponse(responseCode = "200", description = "Successful Operation", 
	                   content = @Content(schema = @Schema(implementation = ManagerViewModel.class)))})
    @PostMapping("/")
    public ResponseEntity<ManagerViewModel<MovieViewModel>> createMovie(@Valid @RequestBody MovieViewModel vm) {
    	setCurrentHashing();
		long startTime = System.currentTimeMillis();
		logger.info("("+hashing+") Create Movie");
		ManagerViewModel<MovieViewModel> mvm = movieManager.insertMovie(hashing, vm);
		long endTime = System.currentTimeMillis();
		mvm.getInfo().setExecutiontime(Utils.executiontime(startTime, endTime));
		return new ResponseEntity<ManagerViewModel<MovieViewModel>>(mvm, HttpStatus.OK);
    }
    
    @Operation(summary = "Update Movie By Id", description = "Update Movie", tags = "Movie")
	@ApiResponses(value = {
	           @ApiResponse(responseCode = "200", description = "Successful Operation", 
	                   content = @Content(schema = @Schema(implementation = ManagerViewModel.class)))})
    @PatchMapping("/{id}")
    public ResponseEntity<ManagerViewModel<MovieViewModel>> updateMovie(
    		@Parameter(description = "Id Value", required = true) @PathVariable("id") int id,
    		@Valid @RequestBody MovieViewModel vm) {
    	setCurrentHashing();
		long startTime = System.currentTimeMillis();
		logger.info("("+hashing+") Update Movie");
		ManagerViewModel<MovieViewModel> mvm = movieManager.updateMovieById(hashing, id, vm);
		long endTime = System.currentTimeMillis();
		mvm.getInfo().setExecutiontime(Utils.executiontime(startTime, endTime));
		return new ResponseEntity<ManagerViewModel<MovieViewModel>>(mvm, HttpStatus.OK);
    }
    
    @Operation(summary = "Delete Movie By Id", description = "Delete Movie", tags = "Movie")
	@ApiResponses(value = {
	           @ApiResponse(responseCode = "200", description = "Successful Operation", 
	                   content = @Content(schema = @Schema(implementation = ManagerViewModel.class)))})
    @DeleteMapping("/{id}")
    public ResponseEntity<ManagerViewModel<MovieViewModel>> deleteMovieById(
    		@Parameter(description = "Id Value", required = true) @PathVariable("id") int id) {
    	setCurrentHashing();
		long startTime = System.currentTimeMillis();
		logger.info("("+hashing+") Delete Movie By Id " + id);
		ManagerViewModel<MovieViewModel> mvm = movieManager.deleteMovieById(hashing, id);
		long endTime = System.currentTimeMillis();
		mvm.getInfo().setExecutiontime(Utils.executiontime(startTime, endTime));
		return new ResponseEntity<ManagerViewModel<MovieViewModel>>(mvm, HttpStatus.OK);
    }
}
