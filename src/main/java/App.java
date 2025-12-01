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

        try (BasicDataSource ds = new BasicDataSource()) {

            ds.setUrl("jdbc:mysql://localhost:3306/sakila");
            ds.setUsername(username);
            ds.setPassword(password);

            Connection connection = ds.getConnection();
            displayActorsByLastName(connection);

        } catch (SQLException e) {
            System.out.println("Failed to connect to DB: " + e);
            System.exit(1);
        }
    }

    private static void displayActorsByLastName(Connection connection) throws SQLException {
        String actor = getAString("Enter the last name of your favorite actor: ");

        PreparedStatement preparedStatement = connection.prepareStatement(String.format("""
                SELECT
                    Actor_ID,
                    First_Name,
                    Last_Name
                FROM
                    Actor
                WHERE
                    Last_Name = "%s"
                ORDER BY
                    Actor_ID
                """, actor));

        ResultSet results = preparedStatement.executeQuery();

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
