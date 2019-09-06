

/* *
 * Developed  for the class project in COP5556 Programming Language Principles 
 * at the University of Florida, Fall 2019.
 * 
 * This software is solely for the educational benefit of students 
 * enrolled in the course during the Fall 2019 semester.  
 * 
 * This software, and any software derived from it,  may not be shared with others or posted to public web sites or repositories,
 * either during the course or afterwards.
 * 
 *  @Beverly A. Sanders, 2019
 */
package cop5556sp19;


import static cop5556sp19.Token.Kind.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import cop5556sp19.Token.Kind;

public class Scanner {
	
	Reader r;
	String testString;
	int numPos=-1;
	int lineNo=0;
	Kind kind;

	@SuppressWarnings("serial")
	public static class LexicalException extends Exception {	
		public LexicalException(String arg0) {
			super(arg0);
		}
	}
	
	public Scanner(Reader r) throws IOException {
		this.r = r;
	}


	public Token getNext() throws Exception {
		    //replace this code.  Just for illustration
		char a ;String line="";
		BufferedReader reader = new BufferedReader(r,10);
		
		if(testString == null) {
			intializeInput(reader);
		}
		if(testString !=null) {
			numPos +=1;
		a = testString.charAt(numPos);
		
			System.out.print("character is "+a);
			//char a = '';
			//System.out.print("character is @ and value is "+(int)a);
			switch(a) {
			case '@': {
				throw new LexicalException("Error :- Invalid token found as "+a+" in line: "+ lineNo + " and position:"+numPos);	//@
				}
			case ',': {
				kind = Kind.EOF;
				return new Token(COMMA,",",0,0);
			}
			case ':': {
				if (Kind.EOF.equals(kind)) {
					kind = Kind.COLON;
					return (getNext());
				}
				else if(Kind.COLON.equals(kind)) {
					kind = Kind.EOF;
				return new Token(COLONCOLON,"::",0,0);
				}
				else {
					throw new LexicalException("Error :- Invalid colon token found as "+a+" in line: "+ lineNo + " and position:"+numPos);
				}
			}
			case '=': {
				if (Kind.EOF.equals(kind)) {
					kind = Kind.REL_LE;
					return (getNext());
				}
				else if(Kind.REL_LE.equals(kind)) {
				kind = Kind.REL_EQEQ;
				return new Token(REL_EQEQ,"==",0,0);
				}
				else {
					throw new LexicalException("Error :- Invalid equals token found as "+a+" in line: "+ lineNo + " and position:"+numPos);
				}
			}
			default:{
				throw new LexicalException("Error :- Invalid token found as "+a+" in line: "+ lineNo + " and position:"+numPos);	
			}
			}
		 }
		return new Token(EOF,"EOF",0,0);
}


	private void intializeInput(BufferedReader r) throws Exception {
		String line = r.readLine();
		testString = line;	
		kind = Kind.EOF;
	}
	}
