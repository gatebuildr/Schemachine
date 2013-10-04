package schema;

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
	
}
