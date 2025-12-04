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

    // GETTER AND SETTER METHODS
    public int getActorID() {
        return actorID;
    }

    public void setActorID(int actorID) {
        this.actorID = actorID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
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
