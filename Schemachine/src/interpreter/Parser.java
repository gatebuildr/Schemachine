package interpreter;

import java.util.ArrayList;

import schema.World;
import schema.WorldObject;

public class Parser {
	private static ArrayList<Keyword> simpleDeclaration;
	private static ArrayList<Keyword> simpleQuery;
	
	private World world;

	public Parser(World world){
		this.world = world;
	}

	//TODO: This really needs to go. Making an LR parser is high priority.
	static {
		simpleDeclaration = new ArrayList<Keyword>();
		simpleDeclaration.add(Keyword.NAME);
		simpleDeclaration.add(Keyword.PERIOD);
		simpleQuery = new ArrayList<Keyword>();
		simpleQuery.add(Keyword.NAME);
		simpleQuery.add(Keyword.QUERY);
	}

	public String parse(TokenSet tokens) {
		
		if(tokens.keywords.equals(simpleDeclaration)){
			String name = tokens.names.get(0);
			world.addObject(new WorldObject(name));
			return "Added " + name;
		}
		
		if(tokens.keywords.equals(simpleQuery)){
			String name = tokens.names.get(0);
			if(world.findObject(name) != null)
				return name + " exists.";
			else
				return name + " does not exist.";
		}

		return "Sorry, I didn't understand that statement.";
	}
}
