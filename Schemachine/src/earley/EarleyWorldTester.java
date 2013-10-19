package earley;

import grammar.Grammar;
import grammar.WorldGrammar;
import static grammar.Symbols.*;

public class EarleyWorldTester {

	public static void main(String[] args){
		String[] sentence1 = {NAME, PERIOD};
		Grammar grammar = new WorldGrammar();
		EarleyParser parser = new EarleyParser(grammar);
		test(sentence1, parser);
		System.out.println("\n");
	}

	public static void test(String[] sent, EarleyParser parser){
		StringBuffer out = new StringBuffer();
		for(int i=0; i<sent.length-1; i++)
			out.append(sent[i]+ " ");
		out.append(sent[sent.length-1]);
		String sentence = out.toString();
		System.out.println("\nSentence: \"" + sentence + "\"");
		boolean successful = parser.parseSentence(sent);
		System.out.println("Parse successful? " + successful);
		
		TreeNode.printByLevel(parser.buildTree());
	}
	
}
