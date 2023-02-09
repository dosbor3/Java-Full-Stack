package com.sg.dvdlibrary.dto;

import java.time.*;
import java.time.format.*;

public class Dvd {
    private String title;
    private String genre;
    private String rating;
    private LocalDate releaseDate;
    private String directorName;
    private String studio;
    private String year;
    private String note_or_rating;

    public Dvd (String input_title) {
        this.title = input_title.toUpperCase();
    }

    public String getTitle() {return title;}
    public String getGenre() {return genre;}
    public void setGenre(String input_genre) {this.genre = input_genre;}
    public String getRating() {return this.rating;}
    public void setRating(String input_rating){this.rating = input_rating;}
    public LocalDate getReleaseDate() {
        return this.releaseDate;
    }
    public void setReleaseDate(LocalDate input_ReleaseDate) {
        this.releaseDate = input_ReleaseDate;
    }
    public String getReleaseYear() {return this.year;}
    public void setReleaseYear(String year) {this.year = year;}
    public String getDirectorName() {return this.directorName;}
    public void setDirectorName(String dirctor_name){this.directorName = dirctor_name;}
    public String getStudio() {return this.studio;}
    public void setStudio(String studio){this.studio = studio;}
    public String getNote_or_rating() {return this.note_or_rating;}
    public void setNote_or_rating(String note_or_rating) {this.note_or_rating = note_or_rating;}
}
