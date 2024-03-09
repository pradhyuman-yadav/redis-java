import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            String text;
            while ((text = input.readLine()) != null) {
//              System.out.println("Command = " + command);
                if (text.equalsIgnoreCase("PING")) output.println("+PONG\r");
            }

            socket.close();
        } catch (Exception e) {
            System.out.println("Exception in ClientHandler: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
