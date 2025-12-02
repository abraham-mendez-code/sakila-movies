import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.Scanner;

public class App {

    // CLASS ATTRIBUTES
    private static Scanner scanner = new Scanner(System.in);

    // MAIN METHOD
    public static void main(String[] args) {
        String username = System.getenv("username");
        String password = System.getenv("password");

        // create a new BasicDataSource
        try (BasicDataSource ds = new BasicDataSource()) {

            // set the url, username and password for the datasource(db)
            ds.setUrl("jdbc:mysql://localhost:3306/sakila");
            ds.setUsername(username);
            ds.setPassword(password);

            // create a new connection with the datasource connection
            Connection connection = ds.getConnection();

            // call methods to search and display info from the database
            displayActorsByLastName(connection);
            displayActorsByFullName(connection);

        } catch (SQLException e) {
            System.out.println("Failed to connect to DB: " + e);
            System.exit(1);
        }
    }

    // this method searches for actors matching an input last name then displays it
    private static void displayActorsByLastName(Connection connection) throws SQLException {

        // prompt user for input of a last name
        String lastName = getAString("Enter the last name of your favorite actor: ");

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

        // declare and initialize the prepared statement with the query string
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // replace the placeholder values in the query
        preparedStatement.setString(1, lastName);

        // execute the query and store the results
        ResultSet results = preparedStatement.executeQuery();

        // display the results
        printResults(results);
    }

    private static void displayActorsByFullName(Connection connection) throws SQLException {
        // descriptive header
        System.out.println("Movie Search");
        // get the First Name and Last Name of the actor
        String firstName = getAString("Enter the first name of an actor: ");
        String lastName = getAString("Enter the last name of an actor: ");

        // declare the query string
        String sql = """
                SELECT
                	a.Actor_ID,
                	CONCAT(a.First_Name, ' ', a.Last_Name) as actor_name,
                	f.filmID,
                	f.Title,
                	f.description,
                	f.Release_Year,
                	f.length
                FROM
                	Actor a
                	JOIN Film_Actor fa ON fa.Actor_ID = a.Actor_ID
                	JOIN Film f ON f.Film_ID = fa.Film_ID
                WHERE
                	a.First_Name = ? AND a.Last_Name = ?
                ORDER BY
                    Actor_ID
                """;

        // declare and initialize the prepared statement
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // replace placeholder values with user input
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);

        // execute the query and store the results
        ResultSet results = preparedStatement.executeQuery();

        // display the results
        printResults(results);
    }

    // this method will be used in the displayMethods to actually print the results to the screen
    private static void printResults(ResultSet results) throws SQLException {
        // get the metadata so we have access to the field names
        ResultSetMetaData metaData = results.getMetaData();
        // get the number of rows returned
        int columnCount = metaData.getColumnCount();

        // prints an empty line to make the results prettier
        System.out.println();

        // if there are results...
        if (results.next()) {
            // get the results
            while (results.next()) {

                for (int i = 1; i <= columnCount; i++) {
                    // gets the current column name
                    String columnName = metaData.getColumnName(i);

                    // get the current column value
                    String value = results.getString(i);

                    // print out the column name and column value
                    System.out.println(columnName + ": " + value + " ");
                }

                // prints an empty line to make the results prettier
                System.out.println();

            }
        } else {
            // no results...
            System.out.println("No matches!");
        }

    }

    // This method gets a string
    public static String getAString(String message) {

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

}
