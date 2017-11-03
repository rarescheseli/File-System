public class Directory extends Repository{	
	public Directory(String user, String name, Repository parent) {
		super(user, name, parent);
	}
	
	/**
	 * Prints details about the files contained in this directory
	 */
	@Override
	public void print() {
		for (Repository repo : content) {
			System.out.println(repo.toString());
		}
	}
	
	/**
	 * @return a String with details about this 
	 * Repository(name, type, rights, owner)
	 */
	public String toString() {
		String result = this.getName() + " " + "d";
		result += FileSystem.getInstance().getRights(this.getOwnerRights());
		result += FileSystem.getInstance().getRights(this.getOtherRights());
		result += " " + this.getOwner();
		return result;
	}
}
