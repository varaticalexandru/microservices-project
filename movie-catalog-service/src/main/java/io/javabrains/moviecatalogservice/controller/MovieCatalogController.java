package io.javabrains.moviecatalogservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import io.javabrains.moviecatalogservice.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/movie-catalog")
@RequiredArgsConstructor
public class MovieCatalogController {

    private final RestTemplate restTemplate;
    private final WebClient.Builder webClientBuilder;
    private final DiscoveryClient discoveryClient;

    @GetMapping("/user/{userId}")
    public MovieCatalog getMovieCatalog(@PathVariable String userId) {
        // call movie-rating service get all rated movie for a user
        MovieRatings movieRatings = restTemplate.getForObject("http://movie-rating-service/api/v1/movie-ratings/user/" + userId, MovieRatings.class);

//        // alternative using WebClient (Spring WebFlux)
//        /*Movie movie = webClientBuilder.build()
//                .get()
//                .uri("http://localhost:8002/api/v1/movies/" + movieId)
//                .retrieve()
//                .bodyToMono(Movie.class)
//                .block();*/

        // for each movie ID, call movie-info service and get details
        List<Movie> movies = movieRatings.getMovieRatings()
                .stream()
                .map(MovieRating::getMovieId)
                .distinct()
                .map(movieId ->
                    restTemplate.getForObject("http://movie-info-service/api/v1/movies/" + movieId, Movie.class)
                )
                .collect(Collectors.toList());

        Map<String, Movie> movieMap = new HashMap<>();
        movies.forEach(movie -> movieMap.putIfAbsent(movie.getId(), movie));

        // put them together
        List<CatalogItem> catalogItemList = new ArrayList<>();
        movieRatings.getMovieRatings().forEach(movieRating -> catalogItemList.add(
                CatalogItem.builder()
                        .movieName(movieMap.get(movieRating.getMovieId()).getName())
                        .movieDescription(movieMap.get(movieRating.getMovieId()).getDescription())
                        .rating(movieRating.getRating())
                        .build()
        ));

        return MovieCatalog.builder()
                .userId(userId)
                .movieRatings(catalogItemList)
                .build();
    }
}
