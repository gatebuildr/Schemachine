package model;

import java.util.ArrayList;
import java.util.HashSet;


public class World {
	
	private int numObjects;
	private HashSet<WorldObject> objects;
	
	public World(){
		objects = new HashSet<WorldObject>();
	}

	public boolean equals(Object other){
		return true;
	}

	public int numObjects() {
		return numObjects;
	}

	public void addObject(WorldObject object) {
		numObjects++;
		objects.add(object);
	}

	public boolean exists(WorldObject object) {
		return objects.contains(object);
	}

	public WorldObject findObject(String name) {
		for(WorldObject object : objects)
			if(object.getName().equals(name))
				return object;
		return null;
	}

	public WorldObject[] findMatchingObjects(Quality[] qList) {
		ArrayList<WorldObject> matches = new ArrayList<WorldObject>();
		for(WorldObject object : objects){
			if(object.hasQualities(qList))
				matches.add(object);
		}
		return matches.toArray(new WorldObject[0]);
	}
}
