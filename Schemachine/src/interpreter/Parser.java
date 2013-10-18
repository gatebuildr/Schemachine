package interpreter;

import java.util.ArrayList;

import parser.EarleyParser;
import parser.TreeNode;
import parser.WorldGrammar;
import schema.World;
import schema.WorldObject;

public class Parser {
	private static final String SORRY = "Sorry, I didn't understand that statement.";
	private static ArrayList<Keyword> simpleDeclaration;
	private static ArrayList<Keyword> simpleQuery;

	private World world;
	private EarleyParser earley;
	private String[] nameList;
	private int nameIndex;

	public Parser(World world){
		this.world = world;
		this.earley = new EarleyParser(new WorldGrammar());
		this.nameList = new String[1];
	}

	public String parse(TokenSet tokens) {
		nameIndex = 0;

		String[] keywords = new String[tokens.keywords.size()];
		for(int i=0; i<keywords.length; i++)
			keywords[i] = tokens.keywords.get(i).toString();
		nameList = tokens.names.toArray(nameList);

		if(!earley.parseSentence(keywords))
			return SORRY;
		TreeNode root = earley.buildTree();
		root = root.getChild(0).getChild(0);

		if(root.data.equals("STATEMENT"))
			return parseStatement(root.getChildren());
		
		if(root.data.equals("QUESTION"))
			return parseQuestion(root.getChildren());

		return SORRY;
	}

	private String parseQuestion(TreeNode[] children) {
		String name = nameList[nameIndex];
		WorldObject object = findObject(children[0]);
		if(object == null)
			return name + " does not exist.";
		return name + " exists.";
	}

	private WorldObject findObject(TreeNode objectNode) {
		String name = nextName();
		return world.findObject(name);
	}

	private String parseStatement(TreeNode[] children) {
		if(children.length == 2 && children[0].data.equals("OBJECT") && children[1].data.equals(".")){
			WorldObject object = findOrCreateObject(children[0]);
			return object.getName();
		}
		return SORRY;
	}

	private WorldObject findOrCreateObject(TreeNode objectRoot) {
		String name = nameList[nameIndex];
		WorldObject object = findObject(objectRoot);
		if(object != null)
			return object;
		object = new WorldObject(name);
		world.addObject(object);
		return object;
	}

	private String nextName() {
		return nameList[nameIndex++];
	}
}
