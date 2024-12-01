import java.io.*;
import java.net.*;

public class PeerClientA {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try {
            // Listen client B
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Client A listen " + PORT);

            // socket connect client B
            Socket socketToB = new Socket("localhost", 12346);
            PrintWriter outputToB = new PrintWriter(socketToB.getOutputStream(), true);

            // socket read client B
            Socket socketFromB = serverSocket.accept();
            BufferedReader inputFromB = new BufferedReader(new InputStreamReader(socketFromB.getInputStream()));

            // input console
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            // send to client B
            new Thread(() -> {
                String message;
                try {
                    while ((message = consoleInput.readLine()) != null) {
                        outputToB.println(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // receive from client B
            String receivedMessage;
            while ((receivedMessage = inputFromB.readLine()) != null) {
                System.out.println("Client B: " + receivedMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
