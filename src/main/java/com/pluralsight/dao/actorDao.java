package com.pluralsight.dao;

import com.pluralsight.models.Actor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class actorDao {

    // CLASS ATTRIBUTES
    DataSource dataSource;

    // CONSTRUCTOR
    public actorDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // this method searches for actors matching an input last name then displays it
    public ArrayList<Actor> actorsByLastName(String lastName) {

        // create an empty list
        ArrayList<Actor> actors = new ArrayList<>();

        // declare and initialize the sql query string
        String sql = """
                SELECT
                    Actor_ID,
                    First_Name,
                    Last_Name
                FROM
                    Actor
                WHERE
                    Last_Name = ?
                ORDER BY
                    Actor_ID
                """;

        // try to run a query amd get the results
        try (
                // Get a connection
                Connection connection = this.dataSource.getConnection();

                // declare and initialize the prepared statement with the query string
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            // replace the placeholder values in the query
            preparedStatement.setString(1, lastName);

            // execute the query and store the results
            try (ResultSet results = preparedStatement.executeQuery()) {

                // Add the results to the actors list
                addResults(results, actors);
            }
        } catch (SQLException e) {
            System.out.println("Unable to execute the query: " + e);
            System.exit(1);
        }

        // return the list
        return actors;
    }

    // this method searches the database for movies by an actor using the actor's full name
    public ArrayList<Actor> actorsByFullName(String firstName, String lastName) {

        // create an empty list
        ArrayList<Actor> actors = new ArrayList<>();

        // declare the query string
        String sql = """
                SELECT
                	Actor_ID,
                	First_Name,
                	Last_Name
                FROM
                	Actor
                WHERE
                	First_Name = ? AND Last_Name = ?
                ORDER BY
                    Actor_ID
                """;

        try (Connection connection = this.dataSource.getConnection();
             // declare and initialize the prepared statement
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            // replace placeholder values with user input
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);

            // execute the query and store the results
            try (ResultSet results = preparedStatement.executeQuery()) {

                // Add the results to the actors list
                addResults(results, actors);
            }
        } catch (SQLException e) {
            System.out.println("Unable to execute the query: " + e);
        }

        // return the list
        return actors;
    }

    // this method loop over results to create actor objects and add them to the list
    private static void addResults(ResultSet results, ArrayList<Actor> actors) throws SQLException {
        // loop over results to create actor objects and add them to the list
        while (results.next()) {
            // create a new Actor from the results returned from DB
            Actor newActor = new Actor(
                    results.getInt("ActorID"),
                    results.getString("First_Name"),
                    results.getString("Last_Name")
            );

            // add the actor to the list
            actors.add(newActor);
        }
    }
}
