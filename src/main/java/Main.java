import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
  public static void main(String[] args) {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.out.println("Logs from your program will appear here!");

    //  Uncomment this block to pass the first stage
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        int port = 6379;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);
            // Wait for connection from client.
            while(true) {
                clientSocket = serverSocket.accept();

                Socket finalClientSocket = clientSocket;
                ServerSocket finalServerSocket = serverSocket;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BufferedReader commandReader = new BufferedReader(new InputStreamReader(finalClientSocket.getInputStream()));
                            PrintWriter writer = new PrintWriter(finalClientSocket.getOutputStream(), true);
                            System.out.print("============ DEBUGGING CODE =============");
                            while (!finalServerSocket.isClosed()) {
                                String command = commandReader.readLine();
                                if (command.equalsIgnoreCase("PING")) writer.println("+PONG\r");
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } finally {
                            try {
                                finalServerSocket.close();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });


            }

        } catch (IOException e) {
          System.out.println("IOException: " + e.getMessage());
        } finally {
          try {
            if (clientSocket != null) {
              clientSocket.close();
            }
          } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
          }
        }
  }
}
