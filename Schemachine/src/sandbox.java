
public class sandbox {

	public static void main(String[] args) {
		String testString = "   		 Does this SENTEnce end with a question mark?.	  	 ";
		String[] tokens = testString.toLowerCase().trim().split("([ \t]+)|(?=[.?])");
		for(String s : tokens){
			System.out.println(s);
		}
	}

}
