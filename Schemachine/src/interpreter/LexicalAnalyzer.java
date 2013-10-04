package interpreter;

public class LexicalAnalyzer {
	private static final String SPLITREGEX = "([ \t]+)|(?=[.?])";
	
	private static String nameBuffer = "";

	private static Keyword identify(String token){
		for(Keyword k : Keyword.values()){
			if(k.name().equalsIgnoreCase(token))
				return k;
		}
		
		// punctuation can't be matched to keywords in the same way
		if(token.equals("."))
			return Keyword.PERIOD;
		if(token.equals("?"))
			return Keyword.QUERY;
		
		return Keyword.NAME;
	}
	
	public static TokenSet analyze(String input){
		
		String[] stringTokens = input.toLowerCase().trim().split(SPLITREGEX);
		TokenSet tokenSet = new TokenSet();		
		nameBuffer = "";
		
		for(String sToken : stringTokens){
			if(sToken.equals(""))
				continue;
			
			Keyword keyword = identify(sToken);
			if(keyword == Keyword.NAME)
				nameBuffer += " " + sToken;
			else{
				emptyBuffer(tokenSet);				
				tokenSet.keywords.add(keyword);
			}
		}
		
		emptyBuffer(tokenSet);	
		
		return tokenSet;
	}

	private static void emptyBuffer(TokenSet tokenSet) {
		if(nameBuffer.length() > 0){
			tokenSet.keywords.add(Keyword.NAME);
			tokenSet.names.add(nameBuffer.trim());
			nameBuffer = "";
		}
	}
}
