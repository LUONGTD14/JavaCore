import java.io.*;
import java.net.*;

public class PeerClientB {
    private static final int PORT = 12346;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Client B listen " + PORT);

            Socket socketToA = new Socket("localhost", 12345);
            PrintWriter outputToA = new PrintWriter(socketToA.getOutputStream(), true);

            Socket socketFromA = serverSocket.accept();
            BufferedReader inputFromA = new BufferedReader(new InputStreamReader(socketFromA.getInputStream()));

            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            new Thread(() -> {
                String message;
                try {
                    while ((message = consoleInput.readLine()) != null) {
                        outputToA.println(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            String receivedMessage;
            while ((receivedMessage = inputFromA.readLine()) != null) {
                System.out.println("Client A: " + receivedMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
