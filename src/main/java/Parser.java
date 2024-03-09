import static java.lang.Integer.parseInt;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class Parser {
    List<String> elements = new ArrayList<>();

    public void parse(BufferedReader in, String queryStart) throws IOException {
        int numElements = parseInt(queryStart.substring(1));
        System.out.println("found " + numElements + " number of elements");
        for (int n = 0; n < numElements; n++) {
            String lengthOfNextElement = in.readLine();
            String element = in.readLine();
            System.out.println("found: " + lengthOfNextElement + " " + element);
            elements.add(element);
        }
        System.out.println("done parsing");
    }

    public String getCommand() {
        return elements.get(0);
    }

    public List<String> getArgs() {
        return elements.subList(1, elements.size());
    }
}