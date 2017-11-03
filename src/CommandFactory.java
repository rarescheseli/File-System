
public class CommandFactory {
	public static Command CreateCommand(String command) {
		String prefix = command.substring(0, command.indexOf(' '));
		
		switch (prefix) {
			case "writetofile": return new WriteToFile(command);
			case "adduser": return new AddUser(command);
			case "deluser": return new Deluser(command);
			case "chuser": return new Chuser(command);
			case "chmod": return new Chmod(command);
			case "mkdir": return new Mkdir(command);
			case "rmdir": return new Rmdir(command);
			case "touch": return new Touch(command);
			case "cat": return new Cat(command);
			case "cd" : return new Cd(command);
			case "ls" : return new Ls(command);
			case "rm" : return new Rm(command);
		}
		return null;
	}
}
