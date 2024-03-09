import java.util.List;
public class Evaluator {
    private static final String DELIMITER = "\r\n";
    private static final String PING = "ping";
    private static final String ECHO = "echo";
    private static final String PONG = "+PONG";
    public String evaluate(String command, List<String> args) {
        switch (command) {
            case PING -> {
                return PONG + DELIMITER;
            }
            case ECHO -> {
                String echoed = args.getFirst();
                String length = "$" + echoed.length();
                return length + DELIMITER + echoed + DELIMITER;
            }
        }
        throw new RuntimeException("unknown command");
    }
}