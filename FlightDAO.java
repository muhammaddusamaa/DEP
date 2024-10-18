import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlightDAO {

    // Insert a new flight
    public void insertFlight(Flight flight) {
        String query = "INSERT INTO flights (flight_number, destination, departure_time) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, flight.getFlightNumber());
            stmt.setString(2, flight.getDestination());
            stmt.setString(3, flight.getDepartureTime());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting flight: " + e.getMessage());
        }
    }

    // Update an existing flight
    public void updateFlight(Flight flight) {
        String query = "UPDATE flights SET flight_number=?, destination=?, departure_time=? WHERE flight_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, flight.getFlightNumber());
            stmt.setString(2, flight.getDestination());
            stmt.setString(3, flight.getDepartureTime());
            stmt.setInt(4, flight.getFlightId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating flight: " + e.getMessage());
        }
    }

    // Delete a flight
    public void deleteFlight(int flightId) {
        String query = "DELETE FROM flights WHERE flight_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, flightId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting flight: " + e.getMessage());
        }
    }

    // Retrieve all flights
    public List<Flight> getAllFlights() {
        List<Flight> flights = new ArrayList<>();
        String query = "SELECT * FROM flights";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Flight flight = new Flight(
                        rs.getInt("flight_id"),
                        rs.getString("flight_number"),
                        rs.getString("destination"),
                        rs.getString("departure_time")
                );
                flights.add(flight);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving flights: " + e.getMessage());
        }
        return flights;
    }
}
