import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    // Insert a new customer
    public void insertCustomer(Customer customer) {
        String query = "INSERT INTO customers (name, email, phone, passport_number) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPhone());
            stmt.setString(4, customer.getPassportNumber());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting customer: " + e.getMessage());
        }
    }

    // Update an existing customer
    public void updateCustomer(Customer customer) {
        String query = "UPDATE customers SET name=?, email=?, phone=?, passport_number=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPhone());
            stmt.setString(4, customer.getPassportNumber());
            stmt.setInt(5, customer.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating customer: " + e.getMessage());
        }
    }

    // Retrieve all customers
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("passport_number")
                );
                customers.add(customer);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving customers: " + e.getMessage());
        }
        return customers;
    }

    // Retrieve a customer by ID
    public Customer getCustomerById(int id) {
        String query = "SELECT * FROM customers WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("passport_number")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving customer by ID: " + e.getMessage());
        }
        return null;
    }

    // Search customers by passport number
    public Customer searchCustomerByPassport(String passportNumber) {
        String query = "SELECT * FROM customers WHERE passport_number=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, passportNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("passport_number")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error searching customer: " + e.getMessage());
        }
        return null;
    }
}
