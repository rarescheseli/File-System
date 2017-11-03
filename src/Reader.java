import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Reader {
	private BufferedReader br;
	public Reader(String fileName) throws IOException {
		br = new BufferedReader(new FileReader(fileName));
	}
	
	/**
	 * Read input line by line
	 * @return a linked list of strings representing the commands from the * input file chronologically ordered
	 */
	LinkedList <String> read() {
		LinkedList<String> input = new LinkedList<>();
		try {
		    String line = br.readLine();

		    while (line != null) {
		    	input.addLast(line);
		        line = br.readLine();
		    }

		    br.close();
		} catch (IOException e) {
            e.printStackTrace();
        }

		return input;
	}
}
