import java.util.HashMap;

/**
 * Singleton class that handles the errors
 */
public class Errors {
	private HashMap<Integer, String> errors;
	
	private static Errors instance = null;
	private Errors() {
		errors = new HashMap<>();
		errors.put(-1, ": Is a directory");
		errors.put(-2, ": No such directory");
		errors.put(-3, ": Not a directory");
		errors.put(-4, ": No rights to read");
		errors.put(-5, ": No rights to write");
		errors.put(-6, ": No rights to execute");
		errors.put(-7, ": File already exists");
		errors.put(-8, ": User does not exist");
		errors.put(-9, ": User already exists");
		errors.put(-10, ": No rights to change user status");
		errors.put(-11, ": No such file");
		errors.put(-12, ": No such file or directory");
		errors.put(-13, ": Cannot delete parent or current directory");
		errors.put(-14, ": Non empty directory");
	}
	
	public static Errors getInstance() {
		if (instance == null) {
			instance = new Errors();
		} 
		
		return instance;
	}
	
	/**
	 * Prints the error associated with the error code key and the current command
	 * @param key error code associated with the current command
	 * @param command current command
	 */
	void generateError(int key, String command) {
		String error = Integer.toString(key) + ": " + command;
		error += (errors.get(key));
		System.out.println(error);
	}
}
