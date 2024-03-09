import java.io.*;
import java.net.Socket;
class ClientHandler implements Runnable {
    private final Socket clientSocket;
    Parser parser = new Parser();
    Evaluator eval = new Evaluator();

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            String queryStart;
            while ((queryStart = in.readLine()) != null) {
                parser.parse(in, queryStart);
                String result = eval.evaluate(parser.getCommand(), parser.getArgs());
                out.print(result);
                out.flush();
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