package com.microservices.java.movies.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.java.movies.entity.vm.ManagerViewModel;
import com.microservices.java.movies.entity.vm.MovieViewModel;
import com.microservices.java.movies.manager.MovieManager;
import com.microservices.java.movies.util.ErrorDetailInfoList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MovieController.class)
class MovieControllerTest extends ErrorDetailInfoList {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieManager movieManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllMovies() throws Exception {
        // Mock movie data
        MovieViewModel movie1 = new MovieViewModel();
        movie1.setId(1);
        movie1.setTitle("Movie 1");

        MovieViewModel movie2 = new MovieViewModel();
        movie2.setId(2);
        movie2.setTitle("Movie 2");

        List<MovieViewModel> movies = new ArrayList<>();
        movies.add(movie1);
        movies.add(movie2);

        // Mock the movie manager to return the movies
        ManagerViewModel<List<MovieViewModel>> managerViewModel = new ManagerViewModel<>();
        managerViewModel.setContent(movies);
		managerViewModel.setInfo(getInfoOk("Success"));
		managerViewModel.setTotalRows(movies.size());

        Mockito.when(movieManager.getAllMovie(Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString()))
                .thenReturn(managerViewModel);

        // Perform GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/movies/")
                .param("page", "1")
                .param("size", "10")
                .param("orderby", "id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].title").value("Movie 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].title").value("Movie 2"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
    
    @Test
    void getMovieById() throws Exception {
        // Mock movie data from the database
        MovieViewModel movie = new MovieViewModel();
        movie.setId(1);
        movie.setTitle("Movie 1");

        // Mock the movie manager to return a movie from the database
        ManagerViewModel<List<MovieViewModel>> managerViewModel = new ManagerViewModel<>();
        managerViewModel.setContent(List.of(movie));
		managerViewModel.setInfo(getInfoOk("Success"));
		managerViewModel.setTotalRows(managerViewModel.getContent().size());

        Mockito.when(movieManager.getMovieById(Mockito.anyLong(), Mockito.eq(1)))
                .thenReturn(managerViewModel);

        // Perform GET request and print response
        mockMvc.perform(MockMvcRequestBuilders.get("/movies/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void createMovie() throws Exception {
        // Mock movie data to be created
        MovieViewModel movie = MovieViewModel.builder()
                .id(1)
                .title("New Movie")
                .description("This is a mock movie description.")
                .rating(8.5f)
                .image("path/to/image.jpg")
                .createdAt("2023-09-30 12:00:00")
                .updatedAt("2023-09-30 14:30:00")
                .build();

        // Mock the movie manager to return the created movie
        ManagerViewModel<MovieViewModel> managerViewModel = new ManagerViewModel<>();
        managerViewModel.setContent(movie);
		managerViewModel.setInfo(getInfoOk("Success"));
		managerViewModel.setTotalRows(1);

        Mockito.when(movieManager.insertMovie(Mockito.anyLong(), Mockito.any()))
                .thenReturn(managerViewModel);

        // Perform POST request to create a movie and print response
        mockMvc.perform(MockMvcRequestBuilders.post("/movies/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void updateMovie() throws Exception {
        // Mock movie data to be updated
        MovieViewModel movie = MovieViewModel.builder()
                .id(1)
                .title("Updated Movie")
                .description("This is a mock movie description.")
                .rating(8.5f)
                .image("path/to/image.jpg")
                .createdAt("2023-09-30 12:00:00")
                .updatedAt("2023-09-30 14:30:00")
                .build();

        // Mock the movie manager to return the updated movie
        ManagerViewModel<MovieViewModel> managerViewModel = new ManagerViewModel<>();
        managerViewModel.setContent(movie);
		managerViewModel.setInfo(getInfoOk("Success"));
		managerViewModel.setTotalRows(1);

        Mockito.when(movieManager.updateMovieById(Mockito.anyLong(), Mockito.eq(1), Mockito.any()))
                .thenReturn(managerViewModel);

        // Perform PATCH request to update a movie and print response
        mockMvc.perform(MockMvcRequestBuilders.patch("/movies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void deleteMovieById() throws Exception {
        // Mock the movie manager to return success for movie deletion
        ManagerViewModel<MovieViewModel> managerViewModel = new ManagerViewModel<>();
        managerViewModel.setContent(null);  // Movie deletion usually returns null content
		managerViewModel.setInfo(getInfoOk("Success Delete"));
		managerViewModel.setTotalRows(1);

        Mockito.when(movieManager.deleteMovieById(Mockito.anyLong(), Mockito.eq(1)))
                .thenReturn(managerViewModel);

        // Perform DELETE request to delete a movie and print response
        mockMvc.perform(MockMvcRequestBuilders.delete("/movies/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
