import java.util.ArrayList;

/**
 * Helper class with static methods to check for Repositories or change the
 * current directory
 */
public class AssistantCommands {
	
	/**
	 * Checks if a Repository exists in the current directory
	 * @param target Repository to be checked
	 * @return a Repository, if the target exists and null otherwise
	 */
	public static Repository checkForRepo(String target) {
		Repository crtDir = FileSystem.getInstance().getCurrentDirectory();		
		ArrayList<Repository> tmpContent = crtDir.getContent();
		if (tmpContent.isEmpty() == false) {
			for (Repository repo : tmpContent) {
				if (repo.getName().equals(target)) {
					return repo;
				}
			}
		}
		return null;
	}
	
	/**
	 * Checks if a Repository has reading rights
	 * @param target the Repository that needs to be checked
	 * @return 0 if the Repository has writting rights, -4 otherwise
	 */
	public static int checkReadingRights(Repository target) {
		FileSystem fSystem = FileSystem.getInstance();
		if (fSystem.getCurrentUser().equals(target.getOwner())) {
			if (target.getOwnerRights() < 4) {
				return -4;
			}
		} else {
			if (target.getOtherRights() < 4) {
				return -4;
			}
		}
		return 0;
	}
	
	/**
	 * Checks if a Repository has writing rights
	 * @param target the Repository that needs to be checked
	 * @return 0 if the Repository has writting rights, -5 otherwise
	 */
	public static int checkWritingRights(Repository target) {
		FileSystem fSystem = FileSystem.getInstance();
		
		if (fSystem.getCurrentUser().equals(target.getOwner()) 
				&& (target.getOwnerRights() & 2) == 0) {
			return -5;
		}
		if (!fSystem.getCurrentUser().equals(target.getOwner())
				&& (target.getOtherRights() & 2) == 0) {	
			return -5;							
		}
		
		return 0;
	}
	
	/**
	 * Checks if a Repository has executing rights
	 * @param target the Repository that needs to be checked
	 * @return 0 if the Repository has writting rights, -6 otherwise
	 */
	static int checkExecutingRights(Repository target) {
		FileSystem fSystem = FileSystem.getInstance();
		
		if (fSystem.getCurrentUser().equals(target.getOwner()) 
				&& (target.getOwnerRights() % 2) == 0) {
			return -6;
		}
		if (!fSystem.getCurrentUser().equals(target.getOwner())
				&& (target.getOtherRights() % 2) == 0) {	
			return -6;							
		}
		
		return 0;
	}
	
	/**
	 * Processes a command and extracts the last Repository specified in the
	 * parameter path of the command and tries to change the current directory
	 * to the parent of that Repository
	 * @param command given from input
	 * @return the name of the last Repository if the cd was successful, an
	 * error code otherwise
	 */
	static String process(String command) {
		int startIndex = command.indexOf(' ');
		int endIndex = command.lastIndexOf('/');
		if (startIndex + 1 == endIndex) {
			++endIndex;
		}
		
		if (endIndex > 0) {	
			String parent = "cd";
			parent += command.substring(startIndex, endIndex);
			Command tmp = CommandFactory.CreateCommand(parent);

			int error = 0;
			if (tmp != null) {
				error = tmp.tryToExecute();
			}

			if (error != 0) {
				return Integer.toString(error);
			}
			
			return command.substring(command.lastIndexOf('/') + 1);
		} else {
			return command.substring(command.lastIndexOf(' ') + 1);
		}
	}
	
	/**
	 * Creates a new Repository
	 * @param type is 1 if I want to create a SimpleFile and 2 if I want to
	 * create a Directory
	 * @param owner the owner of the Repository
	 * @param name the name of the Repository
	 * @param parent the parent of the Repository
	 * @return a new Repository
	 */
	static Repository createRepo(int type, String owner, String name, Directory parent) {
		if (type == 1) {
			return new SimpleFile(owner, name, parent);
		} else {
			return new Directory(owner, name, parent);
		}
	}
}
