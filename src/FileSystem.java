import java.util.ArrayList;

/**
 * Singleton class that holds the entire file system
 */
public class FileSystem {
	private Directory rootDir;
	private String currentUser;
	private ArrayList<String> users;
	private Directory currentDirectory;
	private static final ArrayList<String> rights = new ArrayList<String>();
	
	private static FileSystem instance = null;
	private FileSystem() {
		currentUser = "root";
		users = new ArrayList<String>();
		users.add(currentUser);
		
		rights.add("---");
		rights.add("--x");
		rights.add("-w-");
		rights.add("-wx");
		rights.add("r--");
		rights.add("r-x");
		rights.add("rw-");
		rights.add("rwx");

		rootDir = new Directory(currentUser, "/", null);
		rootDir.setOtherRights(5);
		rootDir.setParent(rootDir);
		currentDirectory = rootDir;
	}
	
	/**
	 * 
	 * @param index the rights associated to index binary representation
	 * @return a String associated with the rights
	 */
	public String getRights(int index) {
		return rights.get(index);
	}

	public static FileSystem getInstance() {
		if (instance == null) {
			instance = new FileSystem();
		}
		
		return instance;
	}
	
	public Directory getRootDirectory() {
		return rootDir;
	}
	
	/**
	 * @return the user that is now active in the system
	 */
	public String getCurrentUser() {
		return currentUser;
	}
	
	/**
	 * Changes the current user
	 * @param someUser the user that is to be set active
	 */
	void setCurrentUser(String someUser) {
		if (someUser.equals("root")) { 					
			currentDirectory = getRootDirectory();
			currentUser = getRoot();
			return;
		}
		
		for (Repository repo : rootDir.getContent()) {	
			if (repo.getContent() != null && repo.getName().equals(someUser)) {
				currentDirectory = (Directory) repo;
				currentUser = someUser;
				break;
			}
		}
	}
	
	/**
	 * @param i the index of the wanted user
	 * @return the ith user in the system
	 * Note: The user with index 0 will always be the root
	 */
	String getUser(int i) {
		return users.get(i);
	}
	
	/**
	 * Removes a user from the system
	 * @param userToBeDeleted the removed user
	 */
	void deleteUser(String userToBeDeleted) {
		users.remove(userToBeDeleted);
	}
	
	/**
	 * Checks if a user already exists in the system
	 * @param someUser user to be checked if already exists in the system
	 * @return true if someUser already exists and false otherwise
	 */
	public boolean exists(String someUser) {
		return users.contains(someUser);
	}
	
	public String getRoot() {
		return getUser(0);
	}

	public Directory getCurrentDirectory() {
		return currentDirectory;
	}
	
	void setCurrentDirectory(Repository repo) {
		currentDirectory = (Directory) repo;
	}
	
	/**
	 * adds a user to the system
	 * @param someUser user to be added
	 */
	void addUser(String someUser) {
		users.add(someUser);
		rootDir.add(new Directory(someUser, someUser, getRootDirectory()));
	}
	
	/**
	 * Prints the entire file hierarchy
	 * @param currentRepository the Repository whose content is to be printed
	 * @param level the level of the current Repository in the file hierarchy
	 */
	void printHierarchy(Repository currentRepository, int level) {
		for (int i = 0; i < level; ++i) {
			System.out.print('\t');
		}
		
		System.out.println(currentRepository.toString());
		if (currentRepository.getContent() != null) {
			for (Repository repo : currentRepository.getContent()) {
				printHierarchy(repo, level + 1);
			}
		}
	}
}
