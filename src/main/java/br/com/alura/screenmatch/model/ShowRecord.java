package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ShowRecord(
        @JsonAlias("Title") String name,
        @JsonAlias("Plot") String plot,
        @JsonAlias("imdbRating") String rating,
        @JsonAlias("totalSeasons") Integer numberOfSeasons
) {
}
