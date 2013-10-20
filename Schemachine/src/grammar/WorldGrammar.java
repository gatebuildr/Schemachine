package grammar;

import static grammar.Symbols.*;

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
		POS.add(OBJECT);
		POS.add(PERIOD);
		POS.add(QMARK);
		POS.add(IS);
		POS.add(PREPOSITION);
		POS.add(WHAT);
	}

	private void initRules() {
		String[] s1 = {DECLARATION};
		String[] s2 = {QUESTION};
		RHS[] startRHS = {new RHS(s1), new RHS(s2)};
		rules.put("S", startRHS);
		
		String[] declaration0 = {DECLARATION, PERIOD};
		String[] declaration1 = {OBJECT};
		String[] declaration2 = {OBJECT, IS, PREP_PHRASE};
		RHS[] statementRHS = {new RHS(declaration0), new RHS(declaration1), new RHS(declaration2)};
		rules.put(DECLARATION, statementRHS);
		
		String[] question1 = {OBJECT, QMARK};
		String[] question2 = {IS, OBJECT, PREP_PHRASE, QMARK};
		String[] question3 = {WHAT, IS, PREP_PHRASE, QMARK};
		RHS[] questionRHS = {new RHS(question1), new RHS(question2), new RHS(question3)};
		rules.put(QUESTION, questionRHS);
		
		String[] n1 = {NAME};
		RHS[] nRHS = {new RHS(n1)};
		rules.put(OBJECT, nRHS);
		
		String[] p1 = {PERIOD};
		RHS[] pRHS = {new RHS(p1)};
		rules.put(PERIOD, pRHS);
		
		String[] q1 = {QMARK};
		RHS[] qRHS = {new RHS(q1)};
		rules.put(QMARK, qRHS);
		
		String[] what = {WHAT};
		RHS[] whatRHS = {new RHS(what)};
		rules.put(WHAT, whatRHS);
		
		String[] i1 = {IS};
		RHS[] identityRHS = {new RHS(i1)};
		rules.put(IS, identityRHS);
		
		String[] pp1 = {PREPOSITION, OBJECT};
		RHS[] prepPhraseRHS = {new RHS(pp1)};
		rules.put(PREP_PHRASE, prepPhraseRHS);
		
		String[] prepIn = {IN};
		String[] prepOn = {ON};
		RHS[] prepRHS = {new RHS(prepIn), new RHS(prepOn)};
		rules.put(PREPOSITION, prepRHS);
	}
}
