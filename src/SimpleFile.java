import java.util.ArrayList;

public class SimpleFile extends Repository {
	private String text;
	public SimpleFile(String user, String name, Repository parent) {
		super(user, name, parent);
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String newText) {
		text = newText;
	}
	
	@Override
	public ArrayList<Repository> getContent() {
		return null;
	}
	
	/**
	 * Prints details about the files contained in this file
	 */
	@Override
	public void print() {
		System.out.println(this.toString());		
	}
	
	/**
	 * @return a String with details about this 
	 * Repository(name, type, rights, owner)
	 */
	public String toString() {
		String result = this.getName() + " " + "f";
		result += FileSystem.getInstance().getRights(this.getOwnerRights());
		result += FileSystem.getInstance().getRights(this.getOtherRights());
		result += " " + this.getOwner();
		return result;
	}
}
