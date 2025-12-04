package com.pluralsight;

import com.pluralsight.dao.ActorDao;
import com.pluralsight.dao.FilmDao;
import com.pluralsight.models.Actor;
import com.pluralsight.models.Film;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class App {

    // CLASS ATTRIBUTES
    private static final Scanner scanner = new Scanner(System.in);

    // MAIN METHOD
    public static void main(String[] args) {
        String username = System.getenv("username");
        String password = System.getenv("password");

        // create a new BasicDataSource
        try (BasicDataSource ds = new BasicDataSource()) {

            // set the DS config
            ds.setUrl("jdbc:mysql://localhost:3306/sakila");
            ds.setUsername(username);
            ds.setPassword(password);

            ActorDao actorDao = new ActorDao(ds);
            FilmDao filmDao = new FilmDao(ds);

            String message = """
                    What do you want to do?
                        1) Display Actors By Last Name
                        2) Display Actors By Full Name
                        3) Display Films by Actor Name
                        0) Exit
                    Enter your selection:\s""";

            while (true) {

                // get the selection
                int selection = getAInteger(message);

                switch (selection) {
                    case 1:
                        displayActorsByLastName(actorDao);
                        break;
                    case 2:
                        displayActorsByFullName(actorDao);
                        break;
                    case 3:
                        displayFilmsByActorName(filmDao);
                        break;
                    case 0:
                        System.out.println("Goodbye!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid selection");
                }

            }

        } catch (SQLException e) {
            System.out.println("Failed to connect to DB: " + e);
            System.exit(1);
        }
    }

    // this method searches for actors matching an input last name then displays it
    private static void displayActorsByLastName(ActorDao actorDao) {

        String lastName = getAString("What is the last name? ");
        System.out.println("Searching for " + lastName + "...");

        ArrayList<Actor> actors = actorDao.actorsByLastName(lastName);

        if (actors.isEmpty()) {
            System.out.println("No results");
        } else {
            actors.forEach(System.out::println);
        }
    }

    // this method searches for actors matching an input first and last name then displays it
    private static void displayActorsByFullName(ActorDao actorDao) {

        // get the First Name and Last Name of the actor
        String firstName = getAString("Enter the first name of an actor: ");
        String lastName = getAString("Enter the last name of an actor: ");

        System.out.println("Searching for " + firstName + " " + lastName + "...");

        ArrayList<Actor> actors = actorDao.actorsByFullName(firstName, lastName);

        if (actors.isEmpty()) {
            System.out.println("No results");
        } else {
            actors.forEach(System.out::println);
        }
    }

    // this method searches for films matching an input actor first and last name then displays ut
    private static void displayFilmsByActorName(FilmDao filmDao) {

        // descriptive header
        System.out.println("Movie Search");

        // get the First Name and Last Name of the actor
        String firstName = getAString("Enter the first name of an actor: ");
        String lastName = getAString("Enter the last name of an actor: ");
        System.out.println("Searching for " + firstName + " " + lastName + "...");

        ArrayList<Film> films = filmDao.filmsByActor(firstName, lastName);
        if (films.isEmpty()) {
            System.out.println("No results");
        } else {
            films.forEach(System.out::println);
        }
    }

    // this method gets a string
    private static String getAString(String message) {

        String input;
        // get a valid string
        while (true) {

            System.out.print(message);
            input = scanner.nextLine().trim();

            if (input.matches("[0-9]+")) {
                System.out.println("No numbers allowed.");
                continue;
            }
            if (input.isBlank()) {
                System.out.println("Cannot be empty.");
                continue;
            }
            break;
        }
        return input;
    }

    // this method gets an integer
    private static int getAInteger(String message) {
        int input;
        while (true) {
            try {
                System.out.print(message);
                input = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Only numbers allowed");
            }
        }
        return input;
    }

}
