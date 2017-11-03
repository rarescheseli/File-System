public class Mkdir extends Command {
	
	public Mkdir(String toExecute) {
		super(toExecute);
	}

	/**
	 * Creates a new directory to the specified path, if possible
	 */
	@Override
	public int tryToExecute() {
		if (toExecute.equals("mkdir /")) {
			return -1;
		}
		
		String newDir = "";
		FileSystem fSystem = FileSystem.getInstance();
		String crtUser = fSystem.getCurrentUser();
		
		int startIndex = toExecute.indexOf(' ');
		int endIndex = toExecute.lastIndexOf('/');
		if (endIndex > 0) {
			if (startIndex + 1 == endIndex) {
				fSystem.setCurrentDirectory(fSystem.getRootDirectory());
				newDir = toExecute.substring(endIndex + 1);
			} else {
				if (endIndex + 1 == toExecute.length()) {
					String tmp = toExecute.substring(startIndex, endIndex);
					newDir = tmp.substring(tmp.lastIndexOf('/') + 1, tmp.length());
				} else {
					String parent = "cd";
					parent += toExecute.substring(startIndex, endIndex);
					Command tmp = CommandFactory.CreateCommand(parent);
					
					int error = tmp != null ? tmp.tryToExecute() : 0;
					if (error != 0) {
						return error;
					}
					newDir = toExecute.substring(toExecute.lastIndexOf('/') + 1);
				}
			}
		} else {
			newDir = toExecute.substring(toExecute.lastIndexOf(' ') + 1);
		}
		
		if (!crtUser.equals(fSystem.getRoot()) &&
				fSystem.getCurrentDirectory().equals(fSystem.getRootDirectory())){
			return -5;
		}
		
		Directory crtDir = fSystem.getCurrentDirectory();	
		if (!crtDir.getContent().isEmpty()) {
			Repository target = AssistantCommands.checkForRepo(newDir);
			if (target != null) {
				if (target.getContent() != null) {
					return -1;
				} else {
					return -3;
				}
			}
		} else {
			if (!crtUser.equals(fSystem.getRoot())
				&& (AssistantCommands.checkWritingRights(crtDir) != 0)) {
					return -5;
			}
		}
		
		crtDir.add(AssistantCommands.createRepo(2, crtUser, newDir, crtDir));
		return 0;
	}
}
