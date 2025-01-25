package com.mrdeveloper.nestedjsonapipractice.Model;

import java.util.List;

public class Movies {

    private String Title;
//    private String Year;
//    private String Rated;
    private String Released;
    private String Runtime;
    private String Genre;
//    private String Director;
//    private String Writer;
//    private String Actors;
    private String Plot;
//    private String Language;
//    private String Country;
//    private String Awards;
    private String Poster;
//    private RatingModel Ratings;
    private String imdbRating;
//    private String imdbVotes;
//    private String BoxOffice;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }


    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public String getReleased() {
        return Released;
    }

    public void setReleased(String released) {
        Released = released;
    }

    public String getRuntime() {
        return Runtime;
    }

    public void setRuntime(String runtime) {
        Runtime = runtime;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getPlot() {
        return Plot;
    }

    public void setPlot(String plot) {
        Plot = plot;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }
}
