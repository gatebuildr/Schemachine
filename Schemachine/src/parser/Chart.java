package parser;

import java.util.Vector;


/*
 * The Earley parser in the schemachine.parser package is based on the code
 * published by George Luger in his book, AI Algorithms, Data Structures and
 * Idioms in Prolog, Lisp, and Java, chapter 30: "The Earley Parser: Dynamic
 * Programming in Java".  The text is freely available on his website, at
 * http://www.cs.unm.edu/~luger/
 */

public class Chart {
	Vector<State> chart;
	
	public Chart(){
		chart = new Vector<State>();
	}
	
	public void addState(State s){
		if(!chart.contains(s)){
			chart.add(s);
		}
	}
	
	public State getState(int i){
		if(i<0 || i>= chart.size())
			return null;
		return chart.get(i);
	}
	
	public int size(){
		return chart.size();
	}
	
	public String toString(){
		return chart.toString();
	}
}
