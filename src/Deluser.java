import java.util.LinkedList;

public class Deluser extends Command{
	public Deluser(String toExecute) {
		super(toExecute);
	}

	/**
	 * Removes a user from the system if the current user is the root
	 */
	@Override
	public int tryToExecute() {
		FileSystem fsInstance = FileSystem.getInstance();
		String crtUser = fsInstance.getCurrentUser();
		if (crtUser.equals(fsInstance.getRoot())) {
			String userToBeDeleted =
					toExecute.substring(toExecute.indexOf(' ') + 1);
			if (!fsInstance.exists(userToBeDeleted)) {
				return -8;
			} else { 			
				String newOwner;
				if (fsInstance.getUser(1).equals(userToBeDeleted)) {
					newOwner = fsInstance.getUser(1);					
				} else { 		
					newOwner = fsInstance.getUser(1);
				}

				/*
				  Change the owner of the files owned by the user that must
				  be deleted by doing a breath-first search in the entire
				  file system
				 */
				LinkedList<Repository> queue = new LinkedList<Repository>();
				for (Repository repo : fsInstance.getRootDirectory().getContent()) { 
					if (repo.getName().equals(userToBeDeleted)) {
						queue.add(repo);
					}
				}
				
				fsInstance.deleteUser(userToBeDeleted);
				while (!queue.isEmpty()) {
					Repository crtRepo = queue.poll();
					crtRepo.setOwner(newOwner);
					if (crtRepo.getContent() != null) {	
						for (Repository repo : crtRepo.getContent()) {
							if (repo.getName().equals(userToBeDeleted)) {
								repo.setOwner(newOwner);
								queue.add(repo);
							}
						}
					}
				}
			}
		} else {
			return -10;
		}
		return 0;
	}
}
