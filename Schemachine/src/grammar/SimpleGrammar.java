package grammar;


/*
 * The Earley parser in the schemachine.parser package is based on the code
 * published by George Luger in his book, AI Algorithms, Data Structures and
 * Idioms in Prolog, Lisp, and Java, chapter 30: "The Earley Parser: Dynamic
 * Programming in Java".  The text is freely available on his website, at
 * http://www.cs.unm.edu/~luger/
 */

public class SimpleGrammar extends Grammar {
	public SimpleGrammar(){
		super();
		initialize();
	}
	
	private void initialize(){
		initRules();
		initPOS();
	}
	
	private void initRules() 
	 { 
	 String[] s1 = {"NP", "VP"}; 
	 RHS[] sRHS = {new RHS(s1)}; 
	 rules.put ("S", sRHS); 
	 String[] np1 = {"NP","PP"}; 
	 String[] np2 = {"Noun"}; 
	 RHS[] npRHS = {new RHS(np1), new RHS(np2)};
	 rules.put ("NP", npRHS); 
	 String[] vp1 = {"Verb","NP"}; 
	 String[] vp2 = {"VP", "PP"}; 
	 RHS[] vpRHS = {new RHS(vp1)};
	 rules.put ("VP", vpRHS); 
	 String[] pp1 = {"Prep","NP"}; 
	 RHS[] ppRHS = {new RHS(pp1)}; 
	 rules.put ("PP", ppRHS); 
	 String[] noun1 = {"John"}; 
	 String[] noun2 = {"Mary"}; 
	 String[] noun3 = {"Denver"}; 
	 RHS[] nounRHS = {new RHS(noun1), 
	 new RHS(noun2), 
	 new RHS(noun3)}; 
	 rules.put ("Noun", nounRHS); 
	 String[] verb = {"called"}; 
	 RHS[] verbRHS = {new RHS(verb)}; 
	 rules.put ("Verb", verbRHS); 
	 String[] prep = {"from"}; 
	 RHS[] prepRHS = {new RHS(prep)}; 
	 rules.put ("Prep", prepRHS); 
	 } 
	
	 private void initPOS() 
	 { 
	 POS.add ("Noun"); 
	 POS.add ("Verb"); 
	 POS.add ("Prep"); 
	 } 	
}
