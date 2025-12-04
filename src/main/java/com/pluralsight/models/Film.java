package com.pluralsight.models;

import java.sql.Date;
import java.time.LocalDateTime;

public class Film {

    // CLASS ATTRIBUTES
    int filmID;
    String title;
    String description;
    Date releaseYear;
    String length;
    String actorFirstName;
    String actorLastName;

    // CONSTRUCTOR
    public Film(int filmID, String title, String description, Date releaseYear, String length, String actorFirstName, String actorLastName) {
        this.filmID = filmID;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.length = length;
        this.actorFirstName = actorFirstName;
        this.actorLastName = actorLastName;
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
                ", actorFirstName='" + actorFirstName + '\'' +
                ", actorLastName='" + actorLastName + '\'' +
                '}';
    }

}
