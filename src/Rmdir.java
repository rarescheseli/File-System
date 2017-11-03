public class Rmdir extends Command {

	public Rmdir(String toExecute) {
		super(toExecute);
	}

	/**
	 * Removes an empty directory, if possible
	 */
	@Override
	public int tryToExecute() {
		if (toExecute.equals("rmdir .") 
				|| toExecute.equals("rmdir ..")
				|| toExecute.startsWith("rmdir /..")) {
			return -13;
		}
		
		FileSystem fSystem = FileSystem.getInstance();
		String toDelete = AssistantCommands.process(toExecute);
		if (toDelete.startsWith("-")) {
			return Integer.parseInt(toDelete);
		}
		if (toDelete.equals(".") || toDelete.equals("..")) {
			return -13;
		}
		if (fSystem.getCurrentDirectory().getContent().isEmpty()) {
			return -2;
		}
		
		Repository target = AssistantCommands.checkForRepo(toDelete);
		if (target != null) {
			if (target.getContent() == null) {	
				return -3;
			} else {
				if (!target.getContent().isEmpty()) {
					return -14;
				}
			}
			
			Repository parent = target.getParent();
			if (fSystem.getCurrentUser().equals(fSystem.getRoot())) {
				parent.getContent().remove(target);
				return 0;			
			}
			if (AssistantCommands.checkWritingRights(target.getParent()) != 0) {
				return -5;
			}
			
			parent.getContent().remove(target);
		} else {
			return -2;
		}
		return 0;
	}
}
