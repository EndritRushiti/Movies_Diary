package com.rushiti.endrit.moviesdiary;

public class FilmateShikuara {
    private  String Emri;
    private  Double Rating;
    private  String Description;

    public String getEmri() {
        return Emri;
    }

    public Double getRating() {
        return Rating;
    }

    public String getDescription() {
        return Description;
    }

    public FilmateShikuara(String emri, Double rating, String Description) {
        Emri = emri;
        Rating = rating;
        this.Description=Description;
    }

    public void setEmri(String emri) {
        Emri = emri;
    }

    public void setRating(Double rating) {
        Rating = rating;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
