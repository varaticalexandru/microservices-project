package io.javabrains.moviecatalogservice.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovieCatalog {
    String userId;
    List<CatalogItem> movieRatings;
}
