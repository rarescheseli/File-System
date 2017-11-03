public abstract class Command {
	protected String toExecute;
	public Command(String toExecute) {
		this.toExecute = toExecute;
	}
	
	/**
	 * Tries to execute command received in constructor
	 * @return 0 if the command was executed with succes, an error code
	 * that specifies the encountered error otherwise
	 */
	public abstract int tryToExecute();
	
	/**
	 * Executes the command received in constructor is the cammand is valid,
	 * otherwise generates the proper error
	 */
	public void execute() {
		Directory original = FileSystem.getInstance().getCurrentDirectory();
		int error = tryToExecute();
		if (error != 0) {
				Errors.getInstance().generateError(error, toExecute);
		}
		
		if (!toExecute.startsWith("cd") && !toExecute.startsWith("chuser")) {
			FileSystem.getInstance().setCurrentDirectory(original);
		}
	}
}
