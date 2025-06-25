import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;

public class SimpleServer {
    public static void main(String[] args) {
        int port = 5001; // Choose an available port
        try (ServerSocket serverSocket = new ServerSocket(5001)) {
            System.out.println("Server listening on port " + port);
            // ... further code to accept client connections
            // Inside the try block from the previous example
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                // Handle client communication in a separate thread or method
                // For example: new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + port);
            e.printStackTrace();
        }
    }
}
