public class Touch extends Command {

	public Touch(String toExecute) {
		super(toExecute);
	}

	/**
	 * Creates a new SimpleFile, if possible
	 */
	@Override
	public int tryToExecute() {
		FileSystem fSystem = FileSystem.getInstance();
		String crtUser = fSystem.getCurrentUser();
		Directory crtDir;
		String newFile = AssistantCommands.process(toExecute);
		
		if (newFile.startsWith("-")) {
			return Integer.parseInt(newFile);
		}
		
		crtDir = fSystem.getCurrentDirectory();		
		if (!crtUser.equals(fSystem.getRoot())
				&& (AssistantCommands.checkWritingRights(crtDir) != 0)) {
				return -5;
		}
		
		Repository target = AssistantCommands.checkForRepo(newFile);
		if (target != null) {
			return target.getContent() != null ? -1 : -7;
		}
		
		crtDir.add(AssistantCommands.createRepo(1, crtUser, newFile, crtDir));
		return 0;
	}
}
