package parser;

/*
 * The Earley parser in the schemachine.parser package is based on the code
 * published by George Luger in his book, AI Algorithms, Data Structures and
 * Idioms in Prolog, Lisp, and Java, chapter 30: "The Earley Parser: Dynamic
 * Programming in Java".  The text is freely available on his website, at
 * http://www.cs.unm.edu/~luger/
 */

public class EarleyParser {
	private Grammar grammar;
	private String[] sentence;
	private Chart[] charts;
	public static boolean DEBUG = false;

	public EarleyParser(Grammar g){
		grammar = g;
	}

	public Grammar getGrammar(){
		return grammar;
	}

	public Chart[] getCharts(){
		return charts;
	}

	public boolean parseSentence(String[] s)
	{
		sentence = s;
		charts = new Chart[sentence.length+1];
		for(int i=0; i<charts.length; i++)
			charts[i] = new Chart();
		String[] start1 = {"@", "S"};
		RHS startRHS = new RHS(start1);
		State start = new State("$", startRHS, 0, 0, null);
		charts[0].addState(start);

		for(int i=0; i<charts.length; i++)
		{
			//			System.out.println("examining chart " + i);
			for(int j=0; j<charts[i].size(); j++)
			{
				State st = charts[i].getState(j);
				String next_term = st.getAfterDot();
				if(st.isDotLast())
					complete(st);
				else if(grammar.isPartOfSpeech(next_term))
					scan(st);
				else
					predict(st);
			}
		}

		String[] fin = {"S", "@"};
		RHS finRHS = new RHS(fin);
		State finish = new State("$", finRHS, 0, sentence.length, null);
		State last = charts[sentence.length].getState(charts[sentence.length].size()-1);
		if(DEBUG){
			System.out.println("traceback:");
			System.out.println(last.history());
		}
		return finish.equals(last);
	}

	private void predict(State s){
		String lhs = s.getAfterDot();
		RHS[] rhs = grammar.getRHS(lhs);
		int j = s.getJ();
		for(int i=0; i<rhs.length; i++){
			State ns = new State(lhs, rhs[i].addDot(), j, j, s);
			charts[j].addState(ns);
		}
	}

	private void scan(State s){
		String lhs = s.getAfterDot();
		RHS[] rhs = grammar.getRHS(lhs);
		int i = s.getI();
		int j = s.getJ();
		for(int a=0; a<rhs.length; a++){
			String[] terms = rhs[a].getTerms();
			if(terms.length == 1 && j < sentence.length &&
					terms[0].compareToIgnoreCase(sentence[j]) ==0){
				State ns = new State(lhs, rhs[a].addDotLast(), j, j+1, s);
				charts[j+1].addState(ns);
			}
		}
	}

	private void complete(State s){
		String lhs = s.getLHS();
		for(int a=0; a<charts[s.getI()].size(); a++){
			State st = charts[s.getI()].getState(a);
			String after = st.getAfterDot();
			if(after != null && lhs.compareTo(after)==0){
				State ns = new State(st.getLHS(), st.getRHS().moveDot(), st.getI(), s.getJ(), s);
				charts[s.getJ ()].addState (ns);
			}
		}
	}

	public TreeNode buildTree(){
		State last = charts[sentence.length].getState(charts[sentence.length].size()-1);
		State current = last;
		String[] start1 = {"@", "S"};
		RHS startRHS = new RHS(start1);
		State rootState = new State("$", startRHS, 0, 0, null);
		TreeNode root = new TreeNode(null);
		root.data = current.getLHS();
		TreeNode currentNode = root;
		int i = 0;
		while(!current.equals(rootState)){
			if(DEBUG)
				System.out.println("\nsitting at " + currentNode.data + ": " + current);

			if(current.isDotFirst()){
				currentNode.complete = true;
			}

			//if we're not at the right spot, back up until we get there
			if(!current.getLHS().equals(currentNode.data)){
				if(DEBUG)
					System.out.println("LHS " + current.getLHS() + " doesn't match node " + currentNode.data + ", backing up");
				int childIndex = 1;
				while(currentNode.isComplete() && currentNode.parent != null){
					childIndex = currentNode.parent.getIndexOfChild(currentNode);
					currentNode = currentNode.parent;
					if(DEBUG)
						System.out.println("backed up to " + currentNode.data);
				}
				if(DEBUG){
					System.out.println("last recorded childIndex: " + childIndex);
					if(childIndex > 0)
						System.out.println("child at childIndex-1:" + currentNode.getChild(childIndex-1).data);
				}
				if(childIndex > 0 && currentNode.getChild(childIndex-1).data.equals(current.getLHS())){
					currentNode = currentNode.getChild(childIndex-1);
					if(DEBUG)
						System.out.println("moved down to child " + (childIndex-1) + ": " + currentNode.data);
				}
			}

			if(DEBUG && !current.isDotFirst())
				System.out.println("LHS " + current.getLHS() + " matches node " + currentNode.data);

			if(current.isDotLast()){ //initialize children
				currentNode.initializeChildren(current.getRTerms());
				if(DEBUG)
					System.out.println("initialized " + currentNode.data);
				i = currentNode.numChildren-1;
				currentNode = currentNode.getChild(i);
				if(grammar.isPartOfSpeech(currentNode.parent.data)){
					currentNode.complete = true;
					currentNode.parent.complete = true;
				}
				if(DEBUG)
					System.out.println("moved down to " + currentNode.data + " at index " + i);
			}
			else if(!current.isDotFirst()){
				i = current.getDotIndex()-1;
				currentNode = currentNode.getChild(i);
				if(DEBUG)
					System.out.println("moved down to " + currentNode.data + " at index " + i);
			}
			current = current.getPrevious();
		}
		return root;
	}

}
