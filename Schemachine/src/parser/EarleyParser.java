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
			System.out.println("examining chart " + i);
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
		System.out.println("traceback:");
		System.out.println(last.history());
		return finish.equals(last);
	}
	
	private void predict(State s){
		String lhs = s.getAfterDot();
		RHS[] rhs = grammar.getRHS(lhs);
		int j = s.getJ();
		for(int i=0; i<rhs.length; i++){
			State ns = new State(lhs, rhs[i].addDot(), j, j, s);
			charts[j].addState(ns);
//			System.out.println(ns + " added from " + s);
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
			System.out.println("sitting at " + currentNode.data + ": " + current);
			if(current.isDotFirst()){
				currentNode.complete = true;
				System.out.println("backing out from " + currentNode.data);
				while(currentNode.isComplete() && currentNode.parent != null)
					currentNode = currentNode.parent;
			}
			else if(current.isDotLast()){ //initialize children
				currentNode.initializeChildren(current.getRTerms());
				i = currentNode.numChildren-1;
				currentNode = currentNode.getChild(i);
			}
			else{
				i = current.getDotIndex();
				currentNode = currentNode.getChild(i-1);
			}
			current = current.getPrevious();
		}
		return root;
	}
	
}
