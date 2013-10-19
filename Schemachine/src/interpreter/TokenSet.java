package interpreter;

import java.util.ArrayList;

import parser.Keyword;

public class TokenSet {

	public ArrayList<Keyword> keywords;
	public ArrayList<String> names;
	
	public TokenSet(){
		keywords = new ArrayList<Keyword>();
		names = new ArrayList<String>();
	}
	
	public TokenSet(Keyword[] keywords, String[] names){
		this();
		for(Keyword k : keywords)
			this.keywords.add(k);
		for(String n : names)
			this.names.add(n);
	}
	
	public boolean equals(Object other){
		if(! (other instanceof TokenSet))
			return false;
		TokenSet ts = (TokenSet)other;
		return this.keywords.equals(ts.keywords) && this.names.equals(ts.names);
	}
}
