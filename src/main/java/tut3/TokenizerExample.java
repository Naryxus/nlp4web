package tut3;

import java.text.BreakIterator;
import java.util.Locale;
import java.util.StringTokenizer;

public class TokenizerExample {

	public static void main(String[] args) {
		String s = "I saw a man.  \"I'm Sam,\" he said.";

		System.out.println("--- WhiteSpaceTokenize ---");
		WhitespaceTokenize(s);

		System.out.println("--- BreakIteratorTokenize ---");
		BreakIteratorTokenize(s);
	}

	// Problem 1: Examine and run the following tokenizer. What problems
	// do you see in the output?
	//For the answer see sol_3.txt
	private static void WhitespaceTokenize(String document) {
		StringTokenizer tok = new StringTokenizer(document);
		while(tok.hasMoreTokens()){
			System.out.println(tok.nextElement());
		}
	}

	// Problem 2: Implement a tokenizer using java.text.BreakIterator.
	// What are the improvements over the previous approach? What issues
	// still remain?
	//For the answer see sol_3.txt
	private static void BreakIteratorTokenize(String document) {
		BreakIterator tok = BreakIterator.getWordInstance();
		tok.setText(document);
		int firstBoundary = 0;
		int nextBoundary = tok.next();
		while(nextBoundary != BreakIterator.DONE) {
			System.out.println(document.substring(firstBoundary, nextBoundary));
			firstBoundary = nextBoundary;
			nextBoundary = tok.next();
		}

	}
}
