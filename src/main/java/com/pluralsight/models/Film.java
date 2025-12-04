package com.pluralsight.models;

public class Film {

    // CLASS ATTRIBUTES
    int filmID;
    String title;
    String description;
    int releaseYear;
    String length;

    // CONSTRUCTOR
    public Film(int filmID, String title, String description, int releaseYear, String length) {
        this.filmID = filmID;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.length = length;
    }

    // GETTER AND SETTER METHODS
    public int getFilmID() {
        return filmID;
    }

    public void setFilmID(int filmID) {
        this.filmID = filmID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    // toString method to print class attributes
    @Override
    public String toString() {
        return "Film{" +
                "filmID=" + filmID +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", releaseYear=" + releaseYear +
                ", length='" + length + '\'' +
                '}';
    }

}
