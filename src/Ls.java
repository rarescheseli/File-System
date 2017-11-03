public class Ls extends Command {

	public Ls(String toExecute) {
		super(toExecute);
	}

	/**
	 * Prints the content of the directory recived as parameter, if possible
	 */
	@Override
	public int tryToExecute() {	
		String original = toExecute;
		FileSystem fSystem = FileSystem.getInstance();
		if (toExecute.equals("ls /")) {
			fSystem.getRootDirectory().print();
			return 0;
		}
		
		if (toExecute.endsWith("/")) {
			toExecute = toExecute.substring(0, toExecute.length() - 1);
		}		
		if (toExecute.equals("ls ..")) {
			fSystem.getCurrentDirectory().getParent().print();
			return 0;
		} 
		if (toExecute.equals("ls .")) {
			fSystem.getCurrentDirectory().print();
			return 0;
		}
		
		String toPrint = AssistantCommands.process(toExecute);
		if (toPrint.startsWith("-")) {
			toExecute = original;
			return Integer.parseInt(toPrint);
		}
		if (toPrint.equals(".")) {
			fSystem.getCurrentDirectory().print();
			return 0;
		}
		if (toPrint.equals("..")) {
			fSystem.getCurrentDirectory().getParent().print();
			return 0;
		}
			
		Repository target = AssistantCommands.checkForRepo(toPrint);
		if (target != null) {
			if (fSystem.getCurrentUser().equals(fSystem.getRoot())) {	
				target.print();
				return 0;			
			}
			
			if (AssistantCommands.checkExecutingRights(target) != 0) {
				return -6;
			}
			if (AssistantCommands.checkReadingRights(target) != 0) {
				return -4;
			}
			
			target.print();
		} else {
			toExecute = original;
			return -12;
		}
		return 0;
	}
}
