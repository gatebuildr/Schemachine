package model;

import static grammar.Symbols.*;

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
		if(contents.contains(content))
			return true;
		for(WorldObject recursiveContainer : contents){
			if(recursiveContainer.contains(content))
				return true;
		}
		return false;
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
		if(!isSupporter)
			return false;
		if(burdens.contains(burden))
			return true;
		for(WorldObject recursiveSupporter : burdens)
			if(recursiveSupporter.supports(burden))
				return true;
		return false;
	}

	public void removeBurden(WorldObject burden) {
		burdens.remove(burden);
	}

	public void setQuality(Quality q) {

		switch(q.prep){
		case IN:
			try {
				q.object.addContents(this);
			} catch (NotAContainerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} break;
		case ON:
			try {
				q.object.addBurden(this);
			} catch (NotASupporterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} break;
		default:
			throw new RuntimeException("Unknown preposition " + q.prep);
		}		
	}

	public String toString(){
		return name;
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
}
