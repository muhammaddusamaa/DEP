import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection connection;

    public UserDAO() {
        connection = DBConnection.getConnection();
    }

    public void addUser(User user) {
        String sql = "INSERT INTO users (name, email, country) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getCountry());
            statement.executeUpdate();
            System.out.println("User added successfully!");
            printAllUsers(); // Print all users after adding
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("country")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    public void updateUser(User user) {
        String sql = "UPDATE users SET name = ?, email = ?, country = ? WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getCountry());
            statement.setInt(4, user.getId());
            statement.executeUpdate();
            System.out.println("User updated successfully!");
            printAllUsers(); // Print all users after updating
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.executeUpdate();
            System.out.println("User deleted successfully!");
            printAllUsers(); // Print all users after deleting
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void printAllUsers() {
        List<User> users = getAllUsers();
        System.out.println("Current users:");
        for (User user : users) {
            System.out.println(user.getId() + " | " + user.getName() + " | " + user.getEmail() + " | " + user.getCountry());
        }
        System.out.println();
    }
}
