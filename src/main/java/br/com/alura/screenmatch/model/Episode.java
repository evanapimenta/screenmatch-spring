package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Episode(
        @JsonAlias("Title") String title,
        @JsonAlias("Episode") Integer number,
        @JsonAlias("Runtime") String duration,
        @JsonAlias("imdbRating") String rating,
        @JsonAlias("Plot") String plot,
        @JsonAlias("Released") String releaseDate
) {
}
