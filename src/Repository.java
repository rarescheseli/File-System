import java.util.ArrayList;

public abstract class Repository {
	private String name;
	private String owner;
	private int ownerRights;
	private int otherRights;
	private Repository parent;
	protected ArrayList<Repository> content;
	
	public Repository() {}
	
	/**
	 * Creates a new Repository with owner, name and parent given in constructor
	 * and sets the default rights rwx---
	 * @param owner the owner of the Repository
	 * @param name the name of the Repository
	 * @param parent the Repository that contains the current Repository
	 */
	public Repository(String owner, String name, Repository parent) {
		this.name = name;
		this.owner = owner;
		this.parent = parent;
		
		ownerRights = 7;
		otherRights = 0;
		
		content = new ArrayList<Repository>();
	}
	
	public String getName() {
		return name;
	}

	public String getOwner() {
		return owner;
	}
	
	public void setOwner(String newOwner) {
		owner = newOwner;
	}
	
	public Repository getParent() {
		return parent;
	}
	
	public void setParent(Repository other) {
		parent = other;
	}

	public int getOwnerRights() {
		return ownerRights;
	}

	public void setOwnerRights(int ownerRights) {
		this.ownerRights = ownerRights;
	}

	public int getOtherRights() {
		return otherRights;
	}

	public void setOtherRights(int otherRights) {
		this.otherRights = otherRights;
	}
	
	public ArrayList<Repository> getContent() {
		return content;
	}
	
	public void add(Repository repo) {
		content.add(repo);
	}
	
	/**
	 * Prints the content of the Repository
	 */
	public abstract void print();
}
