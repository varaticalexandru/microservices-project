package io.javabrains.movieinfoservice.controller;

import io.javabrains.movieinfoservice.model.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieInfoController {

    private static final List<Movie> movies = List.of(
        Movie.builder().id("1").name("Avengers").description("Movie about super heroes.").build(),
        Movie.builder().id("2").name("Garfield").description("Movie about an orange cat.").build(),
        Movie.builder().id("3").name("Transformers").description("Movie about vehicle-robots.").build()
    );

    @GetMapping("/{movieId}")
    public Movie getMovieInfo(@PathVariable String movieId) {
        return movies.stream()
                .filter(movie -> movie.getId().equals(movieId))
                .findFirst()
                .orElse(Movie.builder().id("").build());
    }
}
