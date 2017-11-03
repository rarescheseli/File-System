public class Rm extends Command {

	public Rm(String toExecute) {
		super(toExecute);
	}

	/**
	 * Removes the repository, if possible
	 */
	@Override
	public int tryToExecute() {
		if (toExecute.equals("rm -r .") ||
				toExecute.equals("rm -r ..") ||
				toExecute.equals("rm -r /")) {
			return -13;
		}
		
		String toDelete;
		String param[] = toExecute.split(" ");
		FileSystem fSystem = FileSystem.getInstance();
	
		int size = param.length - 1;
		int endIndex = param[size].lastIndexOf('/');		
		if (endIndex >= 0) {
			if (endIndex == 0) {
				fSystem.setCurrentDirectory(fSystem.getRootDirectory());
			} else {
				String parent = "cd ";
				parent += param[size].substring(0, endIndex);
				Command tmp = CommandFactory.CreateCommand(parent);
				
				int error = tmp != null ? tmp.tryToExecute() : 0;
				if (error != 0) {
					return error;
				}
			}
			
			toDelete = param[size].substring(param[size].lastIndexOf('/') + 1);
		} else {
			toDelete = param[size].substring(param[size].lastIndexOf(' ') + 1);
		}
		
		if (toDelete.equals(".") || toDelete.equals("..")) {
			return -13;
		}
		
		Repository crtDir = fSystem.getCurrentDirectory();
		if (!fSystem.getCurrentUser().equals(fSystem.getRoot())) {
			if (AssistantCommands.checkWritingRights(crtDir) != 0) {
				return -5;
			}
		}
		
		if (crtDir.getContent().isEmpty()) {
			return param[1].equals("-r") ? -12 : -11;
		}
		
		Repository target = AssistantCommands.checkForRepo(toDelete);
		if (target != null) {			
			if (!param[1].equals("-r")) {
				if (target.getContent() != null) {
					return -1;
				}
			}		
			crtDir.getContent().remove(target);
		} else {
			return param[1].equals("-r") ? -12 : -11;
		}
		return 0;
	}
}
