package br.com.alura.screenmatch.model;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
    private Integer season;
    private String title;
    private Integer episodeNumber;
    private Double rating;
    private LocalDate releaseDate;

    public Episodio(String title, Integer number, String rating, String s, String runtime, String plot) {
    }

    public Episodio(Integer number, EpisodeRecord e) {
        this.season = number;
        this.title = e.title();
        this.episodeNumber = e.number();
        try {
            this.rating = Double.valueOf(e.rating());
        } catch (NumberFormatException exception ) {
            this.rating = 0.0;
        }
        try {
            this.releaseDate = LocalDate.parse(e.releaseDate());
        } catch (DateTimeParseException exception) {
            this.releaseDate = null;
        }
    }


    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        if (season > 0) {
            this.season = season;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        if (episodeNumber > 0) {
            this.episodeNumber = episodeNumber;
        }
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        if (rating > 0) {
            this.rating = rating;
        }
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return
                "season=" + season +
                ", title='" + title + '\'' +
                ", episodeNumber=" + episodeNumber +
                ", rating=" + rating +
                ", releaseDate=" + releaseDate;
    }
}
