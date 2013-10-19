package parser;

import interpreter.TokenSet;
import static grammar.Symbols.*;

public class LexicalAnalyzer {
	private static final String SPLITREGEX = "([ \t]+)|(?=[.?])";
	
	private static String nameBuffer = "";

	private static String identify(String token){
		for(String s : SYMBOL_LIST)
			if(s.equals(token))
				return token;
		
		return NAME;
	}
	
	public static TokenSet analyze(String input){
		
		String[] stringTokens = input.toLowerCase().trim().split(SPLITREGEX);
		TokenSet tokenSet = new TokenSet();		
		nameBuffer = "";
		
		for(String sToken : stringTokens){
			if(sToken.equals(""))
				continue;
			
			String keyword = identify(sToken);
			if(keyword.equals(NAME))
				nameBuffer += " " + sToken;
			else{
				emptyBuffer(tokenSet);				
				tokenSet.tokens.add(keyword);
			}
		}
		
		emptyBuffer(tokenSet);	
		
		return tokenSet;
	}

	private static void emptyBuffer(TokenSet tokenSet) {
		if(nameBuffer.length() > 0){
			tokenSet.tokens.add(NAME);
			tokenSet.names.add(nameBuffer.trim());
			nameBuffer = "";
		}
	}
}
