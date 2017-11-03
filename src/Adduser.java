
public class AddUser extends Command{
	
	public AddUser(String toExecute) {
		super(toExecute);
	}

	@Override
	public int tryToExecute() {
		FileSystem fsInstance = FileSystem.getInstance();
		String crtUser = fsInstance.getCurrentUser();
		if (crtUser.equals(fsInstance.getRoot())) {
			String newUser =
					toExecute.substring(toExecute.indexOf(' ') + 1);
			if (!fsInstance.exists(newUser)) {
				fsInstance.addUser(newUser);
			} else {
				return -9;
			}
		} else {
			return -10;
		}
		return 0;
	}
}
