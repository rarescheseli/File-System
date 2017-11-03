public class WriteToFile extends Command {

	public WriteToFile(String toExecute) {
		super(toExecute);
	}

	/**
	 * Writes some text to a file, if possible
	 */
	@Override
	public int tryToExecute() {
		String destination;
		int startIndex = toExecute.indexOf(' ') + 1;
		int endIndex = toExecute.indexOf(' ', startIndex);
		String path = toExecute.substring(startIndex, endIndex);
		
		FileSystem fSystem = FileSystem.getInstance();
		String text = toExecute.substring(toExecute.indexOf(' ') + 1);
		text = text.substring(text.indexOf(' ') + 1);
		
		if (path.lastIndexOf('/') > 0) {
			String parent = "cd " + path.substring(0, path.lastIndexOf('/'));
			Command tmp = CommandFactory.CreateCommand(parent);
			
			int error = tmp != null ? tmp.tryToExecute() : 0;
			if (error != 0) {
				return error;
			}
			destination = path.substring(path.lastIndexOf('/') + 1);
		} else {
			destination = path;
		}
		
		Repository target = AssistantCommands.checkForRepo(destination);			
		if (target != null) {
			if (target.getContent() != null) {
				return -1;
			}
			
			if (fSystem.getCurrentUser().equals(fSystem.getRoot())) {
				((SimpleFile)target).setText(text);
				return 0;			
			}
			if (AssistantCommands.checkWritingRights(target) != 0) {
				return -5;
			}		
		} else {
			return -11;
		}
		
		((SimpleFile)target).setText(text);
		return 0;
	}
}
