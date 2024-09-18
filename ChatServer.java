import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServer {
    private static final int PORT = 12347;
    private static ConcurrentHashMap<String, ClientHandler> clientHandlers = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        System.out.println("Chat server started...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                try {
                    new ClientHandler(serverSocket.accept()).start();
                } catch (IOException e) {
                    System.err.println("Error accepting client connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Register new user
                username = in.readLine();
                if (clientHandlers.containsKey(username)) {
                    out.println("Username already taken.");
                    return;
                }
                clientHandlers.put(username, this);
                broadcast(username + " has joined.");
                updateUserList();

                String message;
                while ((message = in.readLine()) != null) {
                    if (message.startsWith("/private")) {
                        sendPrivateMessage(message);
                    } else if (message.startsWith("/add")) {
                        addClient(message);
                    } else if (message.startsWith("/end")) {
                        break;
                    } else {
                        broadcastMessageToEveryone(username, message);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error in client handler: " + e.getMessage());
            } finally {
                closeResources();
                clientHandlers.remove(username);
                broadcast(username + " has left.");
                updateUserList();
            }
        }

        private void broadcast(String message) {
            clientHandlers.values().forEach(client -> {
                if (client.out != null) {
                    client.out.println(message);
                }
            });
        }

        private void sendPrivateMessage(String message) {
            String[] parts = message.split(" ", 3);
            String recipient = parts[1];
            String privateMessage = parts[2];
            ClientHandler recipientHandler = clientHandlers.get(recipient);
            if (recipientHandler != null) {
                recipientHandler.out.println("(private) " + username + ": " + privateMessage);
            } else {
                out.println("User " + recipient + " not found.");
            }
        }

        private void broadcastMessageToEveryone(String fromUser, String message) {
            if (!message.trim().isEmpty()) {
                broadcast("(everyone) " + fromUser + ": " + message);
            }
        }

        private void addClient(String message) {
            String[] parts = message.split(" ", 2);
            String newUsername = parts[1];
            if (clientHandlers.containsKey(newUsername)) {
                out.println("Username already taken.");
                return;
            }
            // Broadcast new user joined
            broadcast(newUsername + " has joined.");
            updateUserList();
        }

        private void updateUserList() {
            StringBuilder userList = new StringBuilder("/users");
            clientHandlers.keySet().forEach(username -> userList.append(" ").append(username));
            broadcast(userList.toString());
        }

        private void closeResources() {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}