package parser;

import java.util.LinkedList;
import java.util.Queue;

public class TreeNode {

	public String data;
	private TreeNode[] children;
	public int numChildren;
	TreeNode parent;
	boolean complete;

	public TreeNode(TreeNode parent){
		this.parent = parent;
		this.numChildren = 0;
	}
	
	public boolean isComplete(){
		if(complete) return true;
//		complete = true;
		if(numChildren == 0)
			return false;
		for(TreeNode child : children)
			if(!child.isComplete()){
				complete = false;
				return false;
			}
		return true;
	}

	public static void printByLevel(TreeNode root){
		Queue<TreeNode> level  = new LinkedList<>();
		level.add(root);
		while(!level.isEmpty()){
			TreeNode node = level.poll();
			System.out.print(node.data + " ");
			if(node.children != null)
				for(TreeNode tn : node.children)
					if(tn !=  null)
						level.add(tn);
		}
	}

	public void initializeChildren(String[] rTerms) {
		numChildren = rTerms.length;
		children = new TreeNode[numChildren];
		for(int i=0; i<rTerms.length; i++){
			children[i] = new TreeNode(this);
			children[i].data = rTerms[i];
		}
	}

	public TreeNode getChild(int i) {
		return children[i];
	}
	
	public int getIndexOfChild(TreeNode child){
		for(int i=0; i<numChildren; i++){
			if(children[i] == child)
				return i;
		}
		return -1;
	}

	public TreeNode[] getChildren() {
		return children;
	}

}
