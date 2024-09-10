import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "DEP_MU";
    private static final String PASSWORD = "abc123";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error connecting to Oracle DB: " + e.getMessage());
        }
        return connection;
    }
}
