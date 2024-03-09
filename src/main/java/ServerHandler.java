import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
class Server {
    private static final int PORT = 6379;
    private static final int numberOfThreads = 5;
    public void run() {
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            serverSocket.setReuseAddress(true);
            while (true) {
                clientSocket = serverSocket.accept();
                executor.submit(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            executor.shutdown();
        }
    }
}