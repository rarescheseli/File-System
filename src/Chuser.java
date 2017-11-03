
public class Chuser extends Command{
	
	public Chuser(String toExecute) {
		super(toExecute);
	}
	
	/**
	 * Changes the current unser to the specified user, if possible, otherwise
	 * generates the proper error code 
	 */
	@Override
	public int tryToExecute() {
		FileSystem fsInstance = FileSystem.getInstance();
		String userToBeChanged = toExecute.substring(toExecute.indexOf(' ')+1);
		if (fsInstance.exists(userToBeChanged)) {
			fsInstance.setCurrentUser(userToBeChanged);
		} else {
			return -8;
		}
		return 0;
	}
}
