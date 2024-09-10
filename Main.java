import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();

        User user1 = new User(0, "Usama", "usama@example.com", "Pakistan");
        userDAO.addUser(user1);
        printAllUsers(userDAO);

        User user2 = new User(0, "Shaheer", "shaheer@gmail.com", "Pakistan");
        userDAO.addUser(user2);
        printAllUsers(userDAO);

        User user3 = new User(0, "Elon Musk", "elon.musk@gmail.com", "USA");
        userDAO.addUser(user3);
        printAllUsers(userDAO);

        List<User> users = userDAO.getAllUsers();
        for (User user : users) {
            System.out.println("User ID: " + user.getId() + ", Name: " + user.getName());
        }

        int shaheerId = findUserIdByName(userDAO, "Shaheer");
        if (shaheerId != -1) {
            userDAO.deleteUser(shaheerId);
        }
        printAllUsers(userDAO);

        User user4 = new User(0, "Shahbaz Khan", "shahbaz@gmail.com", "Pakistan");
        userDAO.addUser(user4);
        printAllUsers(userDAO);
    }

    private static void printAllUsers(UserDAO userDAO) {
        List<User> users = userDAO.getAllUsers();
        System.out.println("Current users:");
        for (User user : users) {
            System.out.println(user.getId() + " | " + user.getName() + " | " + user.getEmail() + " | " + user.getCountry());
        }
        System.out.println(); // Add an empty line for better readability
    }
    private static int findUserIdByName(UserDAO userDAO, String name) {
        List<User> users = userDAO.getAllUsers();
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user.getId();
            }
        }
        return -1;
    }
}
