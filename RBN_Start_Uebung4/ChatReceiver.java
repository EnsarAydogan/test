import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatReceiver {
    private static final int SERVER_PORT = 5001;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("ChatReceiver started and listening on port " + SERVER_PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New message received from: " + clientSocket.getInetAddress());

                try (ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
                     ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream())) {

                    ChatMessage receivedMessage = (ChatMessage) inputStream.readObject();
                    System.out.println("Received message: " + receivedMessage.getContent());

                    // Process the received message and prepare the response
                    boolean success = true; // Dummy logic, replace with your own processing

                    MessageResponse response = new MessageResponse(success);

                    // Send the response back to the sender
                    outputStream.writeObject(response);

                } catch (ClassNotFoundException e) {
                    System.out.println("Error reading the received message: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error starting the ChatReceiver: " + e.getMessage());
        }
    }
}