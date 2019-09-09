

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
	StringBuilder sb;
	
	@SuppressWarnings("serial")
	public static class LexicalException extends Exception {	
		public LexicalException(String arg0) {
			super(arg0);
		}
	}
	
	public Scanner(Reader r) throws IOException {
		this.r = r;
		sb = new StringBuilder();
	}


	public Token getNext() throws Exception {
		    //replace this code.  Just for illustration
		char a ;String line="";
		BufferedReader reader = new BufferedReader(r,10);
		Token t = new Token(EOF,"EOF",0,0);
		if(testString == null) {
			intializeInput(reader);
		}
		if(testString !=null) {
			numPos +=1;
		a = testString.charAt(numPos);
	
			System.out.println("character is "+a);
			switch(kind) {
			case NAME:
				switch(a) {
				case '0': {
					t= new Token(INTLIT,"0",numPos,lineNo);
						
				}break;

				case '@': {
					throw new LexicalException("Error :- Invalid token found as "+a+" in line: "+ lineNo + " and position:"+numPos);	//@
					}
				case ',': {
					kind = Kind.NAME;
					t=new Token(COMMA,",",0,0);
				}
				break;
				case ':': {
					kind = Kind.COLON;
						return (getNext());
					}
				case '=': {
					kind = Kind.REL_LE;
					return (getNext());
					}
				case '+':{
					t = new Token(OP_PLUS,"+",numPos,lineNo);
				}
				case '-':{
					t = new Token(OP_MINUS,"-",numPos,lineNo);
				}
				case '*':{
					t = new Token(OP_TIMES,"-",numPos,lineNo);
				}
				case '/':{
					kind = Kind.OP_DIV;
					return getNext();
				}
				default:{
					if(Character.isJavaIdentifierStart(a)) {
						kind = Kind.STRINGLIT;
						sb.append((char)a);
						return getNext();
					}
					else if(Character.isDigit(a)) {
						kind = Kind.INTLIT;
						sb.append((char)a);
						return getNext();
					}
					throw new LexicalException("Error :- Invalid token found as "+a+" in line: "+ lineNo + " and position:"+numPos);	
				}
				}break;
			case COLON :
				{
					if(Character.compare(':', a) ==0) {
					kind = Kind.NAME;
				t = new Token(COLONCOLON,"::",0,0);
					}
				}
				break;
			case REL_LE:{
				kind = Kind.REL_EQEQ;
				t = new Token(REL_EQEQ,"==",0,0);
				break;}
			case OP_DIV:{
				if(Character.compare('/', a) !=0) {
					numPos -=1;
					t = new Token(OP_DIV,"/",numPos,lineNo);
				}
				else {
					t = new Token(OP_DIVDIV,"//",numPos,lineNo);
				}
				break;
			}
			case STRINGLIT:{
				if(Character.isJavaIdentifierPart(a)) {
				sb.append((char)a);
				if(numPos != testString.length()-1){
					return getNext();
				}
				else {
					t = new Token(STRINGLIT,sb.toString(),numPos,lineNo);
				}
				
				}
				else {
					numPos-=1;
					t = new Token(STRINGLIT,sb.toString(),numPos,lineNo);
				}
				if(Character.compare('+', a)==0) {
					kind = Kind.OP_PLUS;
				}
				break;
			}
			case INTLIT:{
				if(Character.isDigit(a)) {
				sb.append((char)a);
				if(numPos != testString.length()-1){
					return getNext();
				}
				else {
					t = new Token(INTLIT,sb.toString(),numPos,lineNo);
				}
				
				}
				else {
					numPos-=1;
					t = new Token(INTLIT,sb.toString(),numPos,lineNo);
				}
				break;
			}
			case OP_PLUS:{
				kind = Kind.NAME;
				t = new Token(OP_PLUS,"+",numPos,lineNo);
				break;
			}
				
				
			}
		 }
		checkForKeyWords(t);
		return t;
}


	private void checkForKeyWords(Token t) {
		// TODO Auto-generated method stub
		
	}


	private void intializeInput(BufferedReader r) throws Exception {
		String line = r.readLine();
		testString = line;	
		kind = Kind.NAME;
	}
	}
