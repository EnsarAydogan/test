import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChatSender {
    private static final String SERVER_IP = "10.50.205.1";
    private static final int SERVER_PORT = 5001;
    private static final int MESSAGE_PORT = 5001;

    public static void main(String[] args) {
        String name = "Ensar"; // Peer name

        ChatMessage message = new ChatMessage(name+" Test");

        try (Socket socket = new Socket(SERVER_IP, MESSAGE_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            // Send peer registration information to the central server
            outputStream.writeObject(message);

            // Receive the registration response from the server
            RegistrationResponse response = (RegistrationResponse) inputStream.readObject();

            if (response.isSuccess()) {
                System.out.println("Peer registration successful");
                for ( PeerInfo pi : response.getRegisteredPeers()) {
                	System.out.println("Peer: " + pi.getName() + " (" + 
                			pi.getIpAddress() + ")");
                }
            } else {
                System.out.println("Peer registration failed");
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}