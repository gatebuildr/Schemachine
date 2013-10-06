package parser;

/*
 * The Earley parser in the schemachine.parser package is based on the code
 * published by George Luger in his book, AI Algorithms, Data Structures and
 * Idioms in Prolog, Lisp, and Java, chapter 30: "The Earley Parser: Dynamic
 * Programming in Java".  The text is freely available on his website, at
 * http://www.cs.unm.edu/~luger/
 */

import java.util.HashMap;
import java.util.Vector;

public class Grammar {
	HashMap<String, RHS[]> rules;
	Vector<String> POS;
	public Grammar(){
		rules = new HashMap<String, RHS[]>();
		POS = new Vector<String>();
	}
	
	public RHS[] getRHS(String lhs){
		RHS[] rhs = null;
		if(rules.containsKey(lhs))
			rhs = rules.get(lhs);
		return rhs;
	}
	
	public boolean isPartOfSpeech(String s){
		return POS.contains(s);
	}
}
