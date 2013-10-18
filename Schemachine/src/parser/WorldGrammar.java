package parser;

public class WorldGrammar extends Grammar {
	public WorldGrammar(){
		super();
		initialize();
	}
	
	private void initialize(){
		initRules();
		initPOS();
	}

	private void initPOS() {
		POS.add("N");
		POS.add(".");
	}

	private void initRules() {
		String[] s1 = {"N", "."};
		RHS[] sRHS = {new RHS(s1)};
		rules.put("S", sRHS);
		String[] n1 = {"NOUN"};
		RHS[] nRHS = {new RHS(n1)};
		rules.put("N", nRHS);
		String[] p1 = {"PERIOD"};
		RHS[] pRHS = {new RHS(p1)};
		rules.put(".", pRHS);
	}
}
