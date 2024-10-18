import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AirlineManagementSystem {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel, adminFunctionsPanel, userPanel, homePanel;
    private JTextField searchField, nameField, emailField, phoneField, passportField, flightNumberField, destinationField, departureTimeField;
    private JTextArea flightListArea;
    private CustomerDAO customerDAO;
    private FlightDAO flightDAO;

    public AirlineManagementSystem() {
        customerDAO = new CustomerDAO(); // Initialize DAOs
        flightDAO = new FlightDAO();

        frame = new JFrame("Airline Management System");
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        createHomePanel();  // Home screen with buttons for User/Admin panels
        createUserPanel();  // User panel for searching flights
        createAdminFunctionsPanel();  // Admin panel for managing customers and flights

        mainPanel.add(homePanel, "Home");
        mainPanel.add(userPanel, "User");
        mainPanel.add(adminFunctionsPanel, "Admin");

        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);

        // Show Home Panel first
        cardLayout.show(mainPanel, "Home");
    }

    // Home panel with buttons for User and Admin
    private void createHomePanel() {
        homePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        JButton userButton = new JButton("User Panel");
        JButton adminButton = new JButton("Admin Panel");

        userButton.setPreferredSize(new Dimension(200, 50));
        adminButton.setPreferredSize(new Dimension(200, 50));

        gbc.gridx = 0;
        gbc.gridy = 0;
        homePanel.add(userButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        homePanel.add(adminButton, gbc);

        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "User"); // Switch to User Panel
            }
        });

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = JOptionPane.showInputDialog(frame, "Enter Admin Password:");
                if ("airline123".equals(password)) {
                    cardLayout.show(mainPanel, "Admin"); // Switch to Admin Panel
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // User panel for searching flights by destination
    private void createUserPanel() {
        userPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        userPanel.add(new JLabel("Search Flights by Destination:"), gbc);

        searchField = new JTextField(15);
        gbc.gridx = 1;
        userPanel.add(searchField, gbc);

        JButton searchButton = new JButton("Search");
        gbc.gridx = 1;
        gbc.gridy = 1;
        userPanel.add(searchButton, gbc);

        flightListArea = new JTextArea(10, 30);
        flightListArea.setEditable(false);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        userPanel.add(new JScrollPane(flightListArea), gbc);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String destination = searchField.getText();
                List<Flight> flights = flightDAO.getAllFlights(); // Retrieving all flights
                flightListArea.setText(""); // Clear previous results
                boolean found = false;

                for (Flight flight : flights) {
                    if (flight.getDestination().equalsIgnoreCase(destination)) {
                        flightListArea.append("Flight Number: " + flight.getFlightNumber() +
                                "\nDestination: " + flight.getDestination() +
                                "\nDeparture Time: " + flight.getDepartureTime() + "\n\n");
                        found = true;
                    }
                }

                if (!found) {
                    JOptionPane.showMessageDialog(frame, "No flights found for destination: " + destination,
                            "Search Result", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        JButton backButton = new JButton("Back to Home");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        userPanel.add(backButton, gbc);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Home"); // Back to home screen
            }
        });
    }





    // Admin panel for managing customers and flights
    private void createAdminFunctionsPanel() {
        adminFunctionsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Customer fields
        nameField = new JTextField(15);
        emailField = new JTextField(15);
        phoneField = new JTextField(15);
        passportField = new JTextField(15);

        gbc.gridx = 0;
        gbc.gridy = 0;
        adminFunctionsPanel.add(new JLabel("Customer Name:"), gbc);
        gbc.gridx = 1;
        adminFunctionsPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        adminFunctionsPanel.add(new JLabel("Customer Email:"), gbc);
        gbc.gridx = 1;
        adminFunctionsPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        adminFunctionsPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        adminFunctionsPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        adminFunctionsPanel.add(new JLabel("Passport:"), gbc);
        gbc.gridx = 1;
        adminFunctionsPanel.add(passportField, gbc);

        JButton addCustomerButton = new JButton("Add Customer");
        gbc.gridx = 1;
        gbc.gridy = 4;
        adminFunctionsPanel.add(addCustomerButton, gbc);

        addCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameField.getText().isEmpty() || emailField.getText().isEmpty() ||
                        phoneField.getText().isEmpty() || passportField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Customer customer = new Customer(nameField.getText(), emailField.getText(), phoneField.getText(), passportField.getText());
                    customerDAO.insertCustomer(customer);
                    JOptionPane.showMessageDialog(frame, "Customer added successfully!");
                }
            }
        });

        // Flight fields
        flightNumberField = new JTextField(15);
        destinationField = new JTextField(15);
        departureTimeField = new JTextField(15);

        gbc.gridx = 0;
        gbc.gridy = 5;
        adminFunctionsPanel.add(new JLabel("Flight Number:"), gbc);
        gbc.gridx = 1;
        adminFunctionsPanel.add(flightNumberField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        adminFunctionsPanel.add(new JLabel("Destination:"), gbc);
        gbc.gridx = 1;
        adminFunctionsPanel.add(destinationField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        adminFunctionsPanel.add(new JLabel("Departure Time:"), gbc);
        gbc.gridx = 1;
        adminFunctionsPanel.add(departureTimeField, gbc);

        JButton addFlightButton = new JButton("Add Flight");
        gbc.gridx = 1;
        gbc.gridy = 8;
        adminFunctionsPanel.add(addFlightButton, gbc);

        addFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (flightNumberField.getText().isEmpty() || destinationField.getText().isEmpty() ||
                        departureTimeField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Flight flight = new Flight(flightNumberField.getText(), destinationField.getText(), departureTimeField.getText());
                    flightDAO.insertFlight(flight);
                    JOptionPane.showMessageDialog(frame, "Flight added successfully!");
                }
            }
        });

        JButton backButton = new JButton("Back to Home");
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        adminFunctionsPanel.add(backButton, gbc);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Home"); // Back to home screen
            }
        });
    }

    public static void main(String[] args) {
        new AirlineManagementSystem();
    }
}
