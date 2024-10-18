import java.sql.*;
import java.util.Scanner;

public class AirlineUserPanel {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USERNAME = "DEP_MU";
    private static final String PASSWORD = "abc123";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("User Panel");
        System.out.println("Enter Flight ID to search: ");
        int flightID = scanner.nextInt();

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            retrieveFlightByID(connection, flightID);
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving flights: " + e.getMessage());
        }
    }

    private static void retrieveFlightByID(Connection connection, int flightID) throws SQLException {
        String selectSQL = "SELECT * FROM Flights WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(selectSQL);
        statement.setInt(1, flightID);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            System.out.println("Flight ID: " + resultSet.getInt("ID"));
            System.out.println("Flight Number: " + resultSet.getString("FLIGHT_NUMBER"));
            System.out.println("Origin: " + resultSet.getString("ORIGIN"));
            System.out.println("Destination: " + resultSet.getString("DESTINATION"));
            System.out.println("Departure Time: " + resultSet.getTimestamp("DEPARTURE_TIME"));
            System.out.println("Arrival Time: " + resultSet.getTimestamp("ARRIVAL_TIME"));
        } else {
            System.out.println("No flight found for Flight ID: " + flightID);
        }
    }
}
