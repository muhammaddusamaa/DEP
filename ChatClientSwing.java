import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClientSwing {
    private BufferedReader in;
    private PrintWriter out;
    private JFrame frame = new JFrame("Chat Client");
    private JTextArea messageArea = new JTextArea(16, 50);
    private JTextField textField = new JTextField(40);
    private JComboBox<String> fromComboBox = new JComboBox<>();
    private JComboBox<String> toComboBox = new JComboBox<>();
    private JButton sendButton = new JButton("Send");
    private JButton endButton = new JButton("End");
    private JButton addClientButton = new JButton("Add Client");
    private String username;
    private Socket socket;

    public ChatClientSwing(String serverAddress) {
        // GUI Setup
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.add(new JLabel("From:"));
        panel.add(fromComboBox);
        panel.add(new JLabel("Message:"));
        panel.add(textField);
        panel.add(new JLabel("Send to:"));
        panel.add(toComboBox);
        panel.add(addClientButton);
        panel.add(sendButton);
        panel.add(endButton);
        frame.getContentPane().add(panel, BorderLayout.SOUTH);

        // Event Handling
        sendButton.addActionListener(e -> sendMessage());
        endButton.addActionListener(e -> endConnection());
        addClientButton.addActionListener(e -> addClient());

        // Connect to Server
        try {
            socket = new Socket(serverAddress, 12347);  // Change 12346 to 12347

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Ask for username
            username = JOptionPane.showInputDialog(frame, "Enter your username:");
            if (username != null && !username.trim().isEmpty()) {
                out.println(username);
                fromComboBox.addItem(username);
                fromComboBox.setSelectedItem(username);
            } else {
                System.out.println("Username cannot be empty.");
                System.exit(1);
            }

            // Background thread to receive messages
            new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        // Process and handle messages
                        if (message.startsWith("/users")) {
                            updateUserList(message);
                        } else {
                            // Display regular messages in the chat area
                            messageArea.append(message + "\n");
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Error receiving message: " + e.getMessage());
                }
            }).start();

            // Finalize UI
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
            JOptionPane.showMessageDialog(frame, "Error connecting to server: " + e.getMessage());
            System.exit(1);
        }
    }

    private void sendMessage() {
        String message = textField.getText();
        String fromUser = (String) fromComboBox.getSelectedItem();
        String toUser = (String) toComboBox.getSelectedItem();

        if (!message.trim().isEmpty() && fromUser != null && !fromUser.equals(toUser)) {
            if (toUser.equals("everyone")) {
                out.println(message); // Send message to everyone
            } else {
                out.println("/private " + toUser + " " + message); // Send private message
            }
            textField.setText("");
        } else if (fromUser.equals(toUser)) {
            JOptionPane.showMessageDialog(frame, "Cannot send message to yourself.");
        }
    }

    private void endConnection() {
        try {
            if (out != null) {
                out.println("/end");
            }
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null) {
                socket.close();
            }
            frame.dispose();
            System.exit(0);
        } catch (IOException e) {
            System.err.println("Error ending connection: " + e.getMessage());
        }
    }

    private void addClient() {
        String newUsername = JOptionPane.showInputDialog(frame, "Enter new client's username:");
        if (newUsername != null && !newUsername.trim().isEmpty()) {
            out.println("/add " + newUsername);
            // Request an updated user list from the server
            out.println("/users"); // Adjust this based on your server's protocol
        } else {
            JOptionPane.showMessageDialog(frame, "Username cannot be empty.");
        }
    }

    private void updateUserList(String message) {
        System.out.println("Received user list message: " + message);

        if (message.startsWith("/users")) {
            // Extract user list, skipping "/users" part
            String[] users = message.substring(7).split(" ");

            // Clear existing items from both combo boxes
            SwingUtilities.invokeLater(() -> {
                fromComboBox.removeAllItems();
                toComboBox.removeAllItems();
                System.out.println("Cleared combo boxes");
            });

            // Add each user to the combo boxes
            SwingUtilities.invokeLater(() -> {
                for (String user : users) {
                    if (!user.isEmpty()) {
                        fromComboBox.addItem(user);
                        toComboBox.addItem(user);
                    }
                }

                // Add "everyone" to the toComboBox for broadcasting messages
                toComboBox.addItem("everyone");

                // Ensure the current user is selected in the "From" combo box
                fromComboBox.setSelectedItem(username);

                // Repaint the combo boxes
                fromComboBox.repaint();
                toComboBox.repaint();
                System.out.println("Updated combo boxes");
            });
        }
    }

    public static void main(String[] args) {
        new ChatClientSwing("localhost");  // Use 'localhost' or server IP
    }
}