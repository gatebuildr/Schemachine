package interpreter;

import schema.WorldObject;

public class Quality {
	public Keyword prep;
	public WorldObject object;
	public Quality(Keyword prep, WorldObject object) {
		this.prep = prep;
		this.object = object;
	}
	
	public String toString(){
		return prep.toString().toLowerCase() + " " + object;
	}
	
}
