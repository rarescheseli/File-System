import java.util.ArrayList;

public class Cd extends Command {
	
	public Cd(String toExecute) {
		super(toExecute);
	}

	/**
	 * Changes the current directory to the specified path, if possible
	 */
	@Override
	public int tryToExecute() {
		FileSystem fSystem = FileSystem.getInstance();
		if (toExecute.endsWith("cd /")) {
			fSystem.setCurrentDirectory(fSystem.getRootDirectory());
			return 0;
		}
	
		String[] path;
		Repository crtDirectory = fSystem.getCurrentDirectory();	
		Repository startDir = crtDirectory;
		if (toExecute.startsWith("cd /")) { 
			crtDirectory = fSystem.getRootDirectory();
		} 
		if (toExecute.indexOf('/') < 0) {
			path = toExecute.substring(toExecute.indexOf(' ') + 1).split("/");
		} else {
			String[] aux = toExecute.split(" ");
			if (aux[1].startsWith("/")) {
				path = aux[1].substring(1).split("/");
			} else {
				path = aux[1].split("/");
			}
		}
		
		boolean found;
		String crtUser = fSystem.getCurrentUser();
        for (String aPath : path) {
            found = false;
            if (aPath == null || aPath.equals(".")) {
                continue;
            }

            if (aPath.equals("..")) {
                crtDirectory = crtDirectory.getParent();
                continue;
            }

            ArrayList<Repository> tmpContent = crtDirectory.getContent();
            if (!tmpContent.isEmpty()) {
                for (Repository repo : tmpContent) {
                    if (repo.getName().equals(aPath) && repo.getContent() == null) {
                        fSystem.setCurrentDirectory(startDir);
                        return -3;
                    }

                    if (repo.getName().equals(aPath)) {
                        if (crtUser.equals(fSystem.getRoot())) {
                            crtDirectory = repo;
                            found = true;
                            break;
                        }
                        if (crtUser.equals(repo.getOwner())
                                && repo.getOwnerRights() % 2 == 0) {
                            fSystem.setCurrentDirectory(startDir);
                            return -6;
                        }
                        if (!crtUser.equals(repo.getOwner())
                                && repo.getOtherRights() % 2 == 0) {
                            fSystem.setCurrentDirectory(startDir);
                            return -6;
                        }

                        crtDirectory = repo;
                        found = true;
                        break;
                    }
                }
            }

            if (!found) {
                fSystem.setCurrentDirectory(startDir);
                return -2;
            }
        }
		fSystem.setCurrentDirectory(crtDirectory);
		return 0;
	}
}
