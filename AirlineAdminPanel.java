import java.sql.*;
import java.util.Scanner;

public class AirlineAdminPanel {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USERNAME = "DEP_MU";
    private static final String PASSWORD = "abc123";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Admin Panel");
        System.out.println("1. Add Customer");
        System.out.println("2. Add Flight");
        System.out.println("Enter your choice: ");
        int choice = scanner.nextInt();

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (choice == 1) {
                addCustomer(connection, scanner);
            } else if (choice == 2) {
                addFlight(connection, scanner);
            } else {
                System.out.println("Invalid choice.");
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addCustomer(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter Customer ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("Enter Name: ");
        String name = scanner.nextLine();

        System.out.println("Enter Email: ");
        String email = scanner.nextLine();

        System.out.println("Enter Phone: ");
        String phone = scanner.nextLine();

        System.out.println("Enter Passport Number: ");
        String passportNumber = scanner.nextLine();

        String insertSQL = "INSERT INTO Customers (ID, NAME, EMAIL, PHONE, PASSPORT_NUMBER) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(insertSQL);
        statement.setInt(1, id);
        statement.setString(2, name);
        statement.setString(3, email);
        statement.setString(4, phone);
        statement.setString(5, passportNumber);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Customer added successfully.");
        }
    }

    private static void addFlight(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter Flight ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("Enter Flight Number: ");
        String flightNumber = scanner.nextLine();

        System.out.println("Enter Origin: ");
        String origin = scanner.nextLine();

        System.out.println("Enter Destination: ");
        String destination = scanner.nextLine();

        System.out.println("Enter Departure Time (yyyy-mm-dd hh:mm:ss): ");
        String departureTime = scanner.nextLine();

        System.out.println("Enter Arrival Time (yyyy-mm-dd hh:mm:ss): ");
        String arrivalTime = scanner.nextLine();

        String insertSQL = "INSERT INTO Flights (ID, FLIGHT_NUMBER, ORIGIN, DESTINATION, DEPARTURE_TIME, ARRIVAL_TIME) VALUES (?, ?, ?, ?, TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS'))";
        PreparedStatement statement = connection.prepareStatement(insertSQL);
        statement.setInt(1, id);
        statement.setString(2, flightNumber);
        statement.setString(3, origin);
        statement.setString(4, destination);
        statement.setString(5, departureTime);
        statement.setString(6, arrivalTime);

        try {
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Flight added successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error inserting flight: " + e.getMessage());
        }
    }
}
