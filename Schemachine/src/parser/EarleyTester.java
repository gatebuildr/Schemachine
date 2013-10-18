package parser;

public class EarleyTester {

	public static void main(String[] args){
		String[] sentence1 = {"John", "called", "Mary"};
		String[] sentence2 = {"John", "called", "Mary", "from", "Denver"};
		Grammar grammar = new SimpleGrammar();
		EarleyParser parser = new EarleyParser(grammar);
		test(sentence1, parser);
		System.out.println("\n");
		test(sentence2, parser);
	}

	public static void test(String[] sent, EarleyParser parser){
		StringBuffer out = new StringBuffer();
		for(int i=0; i<sent.length-1; i++)
			out.append(sent[i]+ " ");
		out.append(sent[sent.length-1]+".");
		String sentence = out.toString();
		System.out.println("\nSentence: \"" + sentence + "\"");
		boolean successful = parser.parseSentence(sent);
		System.out.println("Parse successful? " + successful);
		
		TreeNode.printByLevel(parser.buildTree());
	}
}

