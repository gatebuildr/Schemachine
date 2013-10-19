package model;

public class Quality {
	public String prep;
	public WorldObject object;
	public Quality(String prep, WorldObject object) {
		this.prep = prep;
		this.object = object;
	}
	
	public String toString(){
		return prep.toString().toLowerCase() + " " + object;
	}
}
