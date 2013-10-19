package interpreter;

import java.util.ArrayList;


public class TokenSet {

	public ArrayList<String> tokens;
	public ArrayList<String> names;
	
	public TokenSet(){
		tokens = new ArrayList<String>();
		names = new ArrayList<String>();
	}
	
	public TokenSet(String[] tokens, String[] names){
		this();
		for(String s : tokens)
			this.tokens.add(s);
		for(String n : names)
			this.names.add(n);
	}
	
	public boolean equals(Object other){
		if(! (other instanceof TokenSet))
			return false;
		TokenSet ts = (TokenSet)other;
		return this.tokens.equals(ts.tokens) && this.names.equals(ts.names);
	}
}
