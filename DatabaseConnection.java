import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; // JDBC URL for Oracle
    private static final String USERNAME = "Airline_Host"; // Your Oracle DB username
    private static final String PASSWORD = "airline123"; // Your Oracle DB password

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver"); // Load the Oracle JDBC driver
            System.out.println("Trying to connect to: " + URL);
            return DriverManager.getConnection(URL, USERNAME, PASSWORD); // Establish connection
        } catch (ClassNotFoundException e) {
            throw new SQLException("Oracle Driver not found", e); // Handle driver not found
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage()); // Log SQL exception
            throw e; // Rethrow after logging
        }
    }
}
