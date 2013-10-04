package interpreter;

import java.util.Scanner;

import schema.World;

public class Interpreter {

	private static final String WELCOME = "Welcome to the Schemachine interpreter! Type 'exit' to quit.";

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		World world = new World();
		Parser parser = new Parser(world);
		
		System.out.println(WELCOME);
		while(true){
			System.out.print(">");
			String input = scanner.nextLine();
			if(input.equalsIgnoreCase("exit"))
				break;
			
			TokenSet tokens = LexicalAnalyzer.analyze(input);
			
			System.out.println("Tokens: " + tokens.keywords.toString());
			System.out.println("Names: " + tokens.names.toString());
			
			String output = parser.parse(tokens);
			System.out.println(output);
		}
		scanner.close();
		System.out.println("Bye!");
	}
}
