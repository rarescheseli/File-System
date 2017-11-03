public class Chmod extends Command {

	public Chmod(String toExecute) {
		super(toExecute);
	}

	/**
	 * Changes permisions on Repositories, if possible
	 */
	@Override
	public int tryToExecute() {
		String toChange;
		String[] tmpCommand = toExecute.split(" ");
		FileSystem fSystem = FileSystem.getInstance();
		int endIndex = tmpCommand[2].lastIndexOf('/');
		int newOtherR = Integer.parseInt(tmpCommand[1]) % 10;
		int newOwnerR = Integer.parseInt(tmpCommand[1]) / 10;
		
		if (endIndex >= 0) {		
			if (endIndex == 0) {
				fSystem.setCurrentDirectory(fSystem.getRootDirectory());
				toChange = toExecute.substring(toExecute.lastIndexOf('/') + 1);
			} else {		
				if (endIndex + 1 == tmpCommand[2].length()) {
					toChange = tmpCommand[2].substring(0, endIndex);
				} else {
					String parent = "cd ";
					endIndex = tmpCommand[2].lastIndexOf('/');			
					parent += tmpCommand[2].substring(0, endIndex);
					Command tmp = CommandFactory.CreateCommand(parent);

					int error = tmp != null ? tmp.tryToExecute() : 0;
					if (error != 0) {
						return error;
					}
					toChange = toExecute.substring(toExecute.lastIndexOf('/') + 1);
				}
			}
		} else {
			toChange = tmpCommand[2];
		}
		
		Repository target = AssistantCommands.checkForRepo(toChange);			
		if (target != null) {
			if (fSystem.getCurrentUser().equals(fSystem.getRoot())) {	
				target.setOwnerRights(newOwnerR);
				target.setOtherRights(newOtherR);
				return 0;			
			}			
			if (AssistantCommands.checkWritingRights(target) != 0) {
				return -5;
			}
			
			target.setOwnerRights(newOwnerR);
			target.setOtherRights(newOtherR);
		} else {
			return -12;
		}
		return 0;
	}
}
