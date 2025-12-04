package com.pluralsight.models;

public class Actor {

    // CLASS ATTRIBUTES
    int actorID;
    String firstName;
    String lastName;

    public Actor (int actorID, String firstName, String lastName)
    {
        this.actorID = actorID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // toString method to get Actor information
    @Override
    public String toString() {
        return "Actor{" +
                "actorID=" + actorID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
