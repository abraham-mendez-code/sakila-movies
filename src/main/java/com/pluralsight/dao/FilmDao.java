package com.pluralsight.dao;

import com.pluralsight.models.Film;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FilmDao {

    // CLASS ATTRIBUTES
    DataSource dataSource;

    // CONSTRUCTOR
    public FilmDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // this method searches for films by a certain actor using the firstName and lastName
    public ArrayList<Film> filmsByActor(String firstName, String lastName){

        // create the empty list
        ArrayList<Film> films = new ArrayList<>();

        // declare the query string
        String sql = """
                SELECT
                	a.Actor_ID,
                	CONCAT(a.First_Name, ' ', a.Last_Name) as Actor_Name,
                	f.FilmID,
                	f.Title,
                	f.Description,
                	f.Release_Year,
                	f.Length
                FROM
                	Actor a
                	JOIN Film_Actor fa ON fa.Actor_ID = a.Actor_ID
                	JOIN Film f ON f.Film_ID = fa.Film_ID
                WHERE
                	a.First_Name = ? AND a.Last_Name = ?
                ORDER BY
                    Actor_Name,
                    FilmID
                """;
        // try to run a query amd get the results
        try (
                // get a connection
                Connection connection = this.dataSource.getConnection();

                // declare and initialize the prepared statement with the query string
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ){

            // replace the placeholder values in the query
            preparedStatement.setString(1,  firstName);
            preparedStatement.setString(1, lastName);

            try(ResultSet results = preparedStatement.executeQuery()) {

                // Add the results to the films list
                addResults(results, films);
            }
        } catch (SQLException e) {
            System.out.println("Unable to execute the query: " + e);
            System.exit(1);
        }

        return films;
    }

    // this method loop over results to create actor objects and add them to the list
    private static void addResults(ResultSet results, ArrayList<Film> films) throws SQLException {
        // loop over results to create actor objects and add them to the list
        while (results.next()) {
            // create a new Actor from the results returned from DB
            Film newFilm = new Film(
                    results.getInt("FilmID"),
                    results.getString("Title"),
                    results.getString("Description"),
                    results.getDate("ReleaseYear"),
                    results.getString("Length")
            );

            // add the actor to the list
            films.add(newFilm);
        }
    }
}
