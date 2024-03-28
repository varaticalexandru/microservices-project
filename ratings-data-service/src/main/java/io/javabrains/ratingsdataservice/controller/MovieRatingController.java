package io.javabrains.ratingsdataservice.controller;

import io.javabrains.ratingsdataservice.model.MovieRating;
import io.javabrains.ratingsdataservice.model.MovieRatings;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/movie-ratings")
public class MovieRatingController {

    private static final MovieRatings movieRatings = MovieRatings.builder()
            .movieRatings(
                    List.of(
                            MovieRating.builder().movieId("1").userId("1").rating(4.1f).build(),
                            MovieRating.builder().movieId("1").userId("2").rating(3.9f).build(),
                            MovieRating.builder().movieId("2").userId("3").rating(2.1f).build(),
                            MovieRating.builder().movieId("3").userId("3").rating(3.4f).build()
                    )
            )
            .build();

    @GetMapping("/user/{userId}")
    public MovieRatings getMovieRatingsByUser(@PathVariable String userId) {

        List<MovieRating> movieRatingList = movieRatings.getMovieRatings().stream()
                .filter(movieRating -> movieRating.getUserId().equals(userId))
                .collect(Collectors.toList());

        return movieRatingList.isEmpty()
                ? MovieRatings.builder().movieRatings(Collections.emptyList()).build()
                : MovieRatings.builder().movieRatings(movieRatingList).build();
    }

    @GetMapping("/movie/{movieId}")
    public MovieRatings getMovieRatingsByMovie(@PathVariable String movieId) {

        List<MovieRating> movieRatingList = movieRatings.getMovieRatings().stream()
                .filter(movieRating -> movieRating.getMovieId().equals(movieId))
                .collect(Collectors.toList());

        return movieRatingList.isEmpty()
                ? MovieRatings.builder().movieRatings(Collections.emptyList()).build()
                : MovieRatings.builder().movieRatings(movieRatingList).build();
    }
}
