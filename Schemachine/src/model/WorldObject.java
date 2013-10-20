package model;

import static grammar.Symbols.*;

import java.util.HashSet;
public class WorldObject {

	private String name;
	private boolean isContainer;
	private boolean isSupporter;
	private HashSet<WorldObject> contents;
	private HashSet<WorldObject> burdens;

	public WorldObject(String name) {
		this.name = name;
		this.contents = new HashSet<WorldObject>();
		this.burdens = new HashSet<WorldObject>();
	}

	public boolean isContainer() {
		return isContainer;
	}

	public HashSet<WorldObject> getContents(){
		return contents;
	}

	public void addContents(WorldObject content){
		if(content == this || content.contains(this))
			throw new RuntimeException("Objects cannot contain themselves.");
		contents.add(content);
		isContainer = true;
	}

	public void removeContents(WorldObject content) {
		contents.remove(content);
	}

	public boolean isSupporter() {
		return isSupporter;
	}

	public String getName() {
		return name;
	}

	public HashSet<WorldObject> getBurdens(){
		return burdens;
	}

	public void addBurden(WorldObject burden){
		if(burden == this || burden.supports(this))
			throw new RuntimeException("Objects cannot support themselves.");
		isSupporter = true;
		burdens.add(burden);
	}

	public boolean contains(WorldObject content) {
		if(!isContainer)
			return false;
		if(contents.contains(content))
			return true;
		for(WorldObject recursiveContainer : contents){
			if(recursiveContainer.contains(content) || recursiveContainer.supports(content))
				return true;
		}
		return false;
	}

	public boolean isEmpty() {
		return true;
	}

	public boolean supports(WorldObject burden) {
		if(!isSupporter)
			return false;
		if(burdens.contains(burden))
			return true;
		for(WorldObject recursiveSupporter : burdens)
			if(recursiveSupporter.supports(burden) || recursiveSupporter.contains(burden))
				return true;
		return false;
	}

	public void removeBurden(WorldObject burden) {
		burdens.remove(burden);
	}

	public boolean checkQuality(Quality q) {
		switch(q.prep){
		case IN:
			return q.object.contains(this);
		case ON:
			return q.object.supports(this);
		default:
			throw new RuntimeException("Unknown preposition " + q.prep);
		}		
	}

	public void setQuality(Quality q) {

		switch(q.prep){
		case IN:
			q.object.addContents(this);
			break;
		case ON:
			q.object.addBurden(this);
			break;
		default:
			throw new RuntimeException("Unknown preposition " + q.prep);
		}		
	}

	public String toString(){
		return name;
	}

	public boolean hasQualities(Quality[] qList) {
		for(Quality q : qList){
			if(!checkQuality(q))
				return false;
		}
		return true;
	}
}
