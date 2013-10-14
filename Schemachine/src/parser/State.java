package parser;


/*
 * The Earley parser in the schemachine.parser package is based on the code
 * published by George Luger in his book, AI Algorithms, Data Structures and
 * Idioms in Prolog, Lisp, and Java, chapter 30: "The Earley Parser: Dynamic
 * Programming in Java".  The text is freely available on his website, at
 * http://www.cs.unm.edu/~luger/
 */


public class State {
	private String lhs;
	private RHS rhs;
	private int i,j;
	
	public State(String lhs, RHS rhs, int i, int j){
		this.lhs = lhs;
		this.rhs = rhs;
		this.i = i;
		this.j = j;
	}
	public String getLHS(){
		return lhs;
	}
	public RHS getRHS(){
		return rhs;
	}
	
	public int getI(){
		return i;
	}
	
	public int getJ(){
		return j;
	}
	
	public String getPriorToDot(){
		return rhs.getPriorToDot();
	}
	
	public String getAfterDot(){
		return rhs.getAfterDot();
	}
	
	public boolean isDotLast(){
		return rhs.isDotLast();
	}
	
	public boolean equals(Object o){
		if(!(o instanceof State))
			return false;
		State other = (State)o;
		return lhs.equals(other.lhs) && rhs.equals(other.rhs)
				&& i==other.i && j==other.j;  
	}
	
	public String toString(){
		String out = lhs;
		for(int i = 0; i < rhs.getTerms().length; i++)
			out = out + " " + rhs.getTerms()[i];
		return out;
	}
}
