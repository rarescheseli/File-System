import java.io.IOException;
import java.util.LinkedList;

public class Test {
	
	/**
	 * Executes the commands from the input file in chronological order
	 * @param input linked list with the commands from the input file
	 */
	protected static void process(LinkedList<String> input) {
		input.poll();
		while (input.size() > 0) {	
			Command command = CommandFactory.CreateCommand(input.pollFirst());
			if (command != null) {
				command.execute();
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		Reader r = new Reader(args[0]);
		Test.process(r.read());
		FileSystem fileSystem = FileSystem.getInstance();
		fileSystem.printHierarchy(fileSystem.getRootDirectory(),0);
	}
}
