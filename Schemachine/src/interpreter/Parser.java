package interpreter;

import java.util.ArrayList;

import parser.EarleyParser;
import parser.TreeNode;
import parser.WorldGrammar;
import schema.World;
import schema.WorldObject;

public class Parser {
	private static final String SORRY = "Sorry, I didn't understand that statement.";
	private World world;
	private EarleyParser earley;
	private String[] nameList;
	private int nameIndex;
	public static boolean DEBUG = true;

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

		return parseSentence(root);
	}

	private String parseSentence(TreeNode root) {
		if(DEBUG){
			TreeNode.printByLevel(root);
			System.out.println("\n");
		}

		if(root.data.equals("DECLARATION"))
			return parseDeclaration(root.getChildren());

		if(root.data.equals("QUESTION"))
			return parseQuestion(root.getChildren());

		return SORRY;
	}

	private String parseQuestion(TreeNode[] children) {
		if(children.length == 2 && children[0].data.equals("OBJECT") && children[1].data.equals("?")){
			String name = nameList[nameIndex];
			WorldObject object = findObject(children[0]);
			if(object == null)
				return name + " does not exist.";
			return name + " exists.";
		}
		if(children.length == 4 && children[0].data.equals("IDENTITY") && children[1].data.equals("OBJECT") && children[2].data.equals("PREP_PHRASE")){
			String name = nameList[nameIndex];
			WorldObject object = findObject(children[1]);
			if(object == null)
				return name + " does not exist.";
			Quality[] qualities = getQualities(children[2]);
			for(Quality q : qualities){
				if(!object.checkQuality(q)){
					return "No, " + object.getName() + " is not " + q + ".";
				}
			}
			return "Yes, " + object.getName() + " is " + qualityListToString(qualities) + ".";
		}
		return SORRY;
	}

	private WorldObject findObject(TreeNode objectNode) {
		String name = nextName();
		return world.findObject(name);
	}

	private String parseDeclaration(TreeNode[] children) {

		if(children.length == 2 && children[0].data.equals("DECLARATION"))
			return parseDeclaration(children[0].getChildren());

		if(children.length == 1 && children[0].data.equals("OBJECT")){
			WorldObject object = findOrCreateObject(children[0]);
			return object.getName();
		}
		if(children.length == 3 && children[0].data.equals("OBJECT") && children[1].data.equals("IDENTITY") && children[2].data.equals("PREP_PHRASE")){
			WorldObject object = findOrCreateObject(children[0]);
			Quality[] qualityList = getQualities(children[2]);
			String out = object.getName() + " is ";
			
			for(Quality q: qualityList)
				object.setQuality(q);
			
			String qString = qualityListToString(qualityList);
			
			out += qString + ".";
			return out;
		}
		return SORRY;
	}

	private String qualityListToString(Quality[] qualityList) {
		String qString = qualityList[0].toString();
		for(int i=1; i< qualityList.length-1; i++){
			qString += qualityList[i] + ", ";
		}
		if(qualityList.length > 1){
			qString += "and " + qualityList[qualityList.length-1].toString();
		}
		return qString;
	}

	private Quality[] getQualities(TreeNode prepPhraseRoot) {
		return new Quality[]{new Quality(getPreposition(prepPhraseRoot.getChild(0)), findOrCreateObject(prepPhraseRoot.getChild(1)))};
	}

	private Keyword getPreposition(TreeNode prepRoot) {
		return Keyword.valueOf(prepRoot.getChild(0).data);
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
