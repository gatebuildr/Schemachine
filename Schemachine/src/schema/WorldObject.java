package schema;
import java.util.HashSet;


public class WorldObject {

	private String name;
	private boolean isContainer;
	private boolean isSupporter;
	private HashSet<WorldObject> contents;
	private HashSet<WorldObject> burdens;
	private boolean frozen;
	
	public WorldObject(String name) {
		this.name = name;
		this.contents = new HashSet<WorldObject>();
		this.burdens = new HashSet<WorldObject>();
	}

	public boolean isContainer() {
		return isContainer;
	}

	public boolean isSupporter() {
		return isSupporter;
	}

	public String getName() {
		return name;
	}

	public void getContents() throws NotAContainerException {
		if(!isContainer()){
			throw new NotAContainerException();
		}
		
	}

	public void getEncumbrance() throws NotASupporterException {
		if(!isSupporter){
			throw new NotASupporterException();
		}
	}

	public void addBurden(WorldObject burden) throws NotASupporterException {
		if(!isSupporter)
			if(frozen)
				throw new NotASupporterException();
			isSupporter = true;
		burdens.add(burden);
		
	}

	public void addContents(WorldObject content) throws NotAContainerException {
		if(!isContainer){
			if(frozen){
				throw new NotAContainerException();
			}
			isContainer = true;
		}
		contents.add(content);
	}

	public boolean contains(WorldObject content) {
		if(!isContainer)
			return false;
		return contents.contains(content);
	}

	public void removeContents(WorldObject content) {
		contents.remove(content);
	}

	public boolean isEmpty() {
		return true;
	}

	public void freeze() {
		frozen = true;
	}

	public boolean supports(WorldObject burden) {
		return burdens.contains(burden);
	}

	public void removeBurden(WorldObject burden) {
		burdens.remove(burden);
	}

}
