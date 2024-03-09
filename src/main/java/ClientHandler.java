import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

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

            HashMap<String, String> storage = new HashMap<>();

            String text;
            while ((text = input.readLine()) != null) {
              System.out.println("Command = " + text);
                if (text.equalsIgnoreCase("PING")) output.println("+PONG\r");
                else if (text.equalsIgnoreCase("ECHO")) {
                    String nextTextLength;
                    if ((nextTextLength = input.readLine()) != null && !nextTextLength.equalsIgnoreCase("-1")) {
                        output.println("+" + input.readLine() + "\r");
                    }
                }
                else if (text.equalsIgnoreCase("set")) {
                    String nextTextLength;
                    if ((nextTextLength = input.readLine()) != null && !nextTextLength.equalsIgnoreCase("-1")) {
                        String setText = input.readLine();
                        if((nextTextLength = input.readLine()) != null && !nextTextLength.equalsIgnoreCase("-1")) {
                            String setData = input.readLine();
                            storage.put(setText, setData);
                        }
                    }
                }
                else if (text.equalsIgnoreCase("get")) {
                    String nextTextLength;
                    if ((nextTextLength = input.readLine()) != null && !nextTextLength.equalsIgnoreCase("-1")) {
                        output.println("+" + storage.get(input.readLine()) + "\r");
                    }
                }
            }

            socket.close();
        } catch (Exception e) {
            System.out.println("Exception in ClientHandler: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
