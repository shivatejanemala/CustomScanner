

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
package cop5556fa19;


import static cop5556fa19.Token.Kind.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import cop5556fa19.Token.Kind;

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
			case START:
				switch(a) {
				case '0': {
					t= new Token(INTLIT,"0",numPos,lineNo);
						
				}break;

				case '@': {
					throw new LexicalException("Error :- Invalid token found as "+a+" in line: "+ lineNo + " and position:"+numPos);	//@
					}
				
				case ',': {
					kind = Kind.START;
					t=new Token(COMMA,",",numPos,lineNo);
				}
				break;
				case ':': {
					kind = Kind.COLON;
						return (getNext());
					}
				case '.': {
					kind = Kind.DOT;
					return (getNext());
				}
				case '=': {
					kind = Kind.ASSIGN;
					return (getNext());
					}
				case '+':{
					t = new Token(OP_PLUS,"+",numPos,lineNo);
					break;
				}
				case '-':{
					t = new Token(OP_MINUS,"-",numPos,lineNo);
					break;
				}
				case '*':{
					t = new Token(OP_TIMES,"-",numPos,lineNo);
					break;
				}
				case '^':{
					t = new Token(OP_POW,"-",numPos,lineNo);
					break;
				}
				case '#':{
					t = new Token(OP_HASH,"-",numPos,lineNo);
					break;
				}
				case '%':{
					t = new Token(OP_MOD,"-",numPos,lineNo);
					break;
				}
				case '&':{
					t = new Token(BIT_AMP,"&",numPos,lineNo);
					break;
				}
				case '/':{
					kind = Kind.OP_DIV;
					return getNext();
				}
				case '<':{
					kind = Kind.REL_LT;
					return getNext();
				}
				case '>':{
					kind = Kind.REL_GT;
					return getNext();
				}
				
				case '~':{
					kind = Kind.BIT_XOR;
					return getNext();
				}
				case '|':{
					t = new Token(BIT_OR,"|",numPos,lineNo);
				}
				case '(':
					t = new Token(LPAREN,"(",numPos,lineNo);
					break;
				case ')':
					t = new Token(RPAREN,")",numPos,lineNo);
					break;	
				case '{':
					t = new Token(LCURLY,"{",numPos,lineNo);
					break;
				case '}':
					t = new Token(RCURLY,"}",numPos,lineNo);
					break;
				case '[':
					t = new Token(LSQUARE,"[",numPos,lineNo);
					break;
				case ']':
					t = new Token(RSQUARE,"]",numPos,lineNo);
					break;
				default:{
					if(Character.isJavaIdentifierStart(a)) {
						kind = Kind.STRINGLIT;
						sb.append((char)a);
						t = new Token(STRINGLIT,sb.toString(),numPos,lineNo);
					}
					else if(Character.isDigit(a)) {
						kind = Kind.INTLIT;
						sb.append((char)a);
						t = new Token(INTLIT,sb.toString(),numPos,lineNo);
						}
					else {
					throw new LexicalException("Error :- Invalid token found as "+a+" in line: "+ lineNo + " and position:"+numPos);	
					}
					if(numPos!=testString.length()-1) {
						return getNext();
					}
					
				}
				}break;
			case COLON :
				{
					if(Character.compare(':', a) ==0) {
					kind = Kind.START;
				t = new Token(COLONCOLON,"::",0,0);
					}
					else {
						kind = Kind.START;
						numPos-=1;
						t = new Token(COLON,":",numPos,lineNo);
					}
				}
				break;
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
				else if(Character.compare('-', a)==0) {
					kind = Kind.OP_MINUS;
				}
				else if(Character.compare('*', a)==0) {
					kind = Kind.OP_TIMES;
				}
				else if(Character.compare('/', a)==0) {
					kind = Kind.START;
				}
				else if(Character.compare('%', a)==0) {
					kind = Kind.OP_MOD;
				}
				else if(Character.compare('^', a)==0) {
					kind = Kind.OP_POW;
				}
				else if(Character.compare('#', a)==0) {
					kind = Kind.OP_HASH;
				}
				else if(Character.compare('&', a)==0) {
					kind = Kind.BIT_AMP;
				}
				else if(Character.compare('~', a)==0) {
					kind = Kind.START;
				}
				else if(Character.compare('|', a)==0) {
					kind = Kind.BIT_OR;
				}
				else if(Character.compare(':', a)==0) {
					kind = Kind.START;
				}
				else if(Character.compare('<', a)==0) {
					kind = Kind.START;
				}
				else if(Character.compare('>', a)==0) {
					kind = Kind.START;
				}
				else if(Character.compare('=', a)==0) {
					kind = Kind.START;
				}
				else if(Character.compare('(', a)==0) {
					kind = Kind.START;
				}else if(Character.compare(')', a)==0) {
					kind = Kind.START;
				}else if(Character.compare('{', a)==0) {
					kind = Kind.START;
				}else if(Character.compare('}', a)==0) {
					kind = Kind.START;
				}else if(Character.compare('[', a)==0) {
					kind = Kind.START;
				}else if(Character.compare(']', a)==0) {
					kind = Kind.START;
				}
				else if(Character.compare('.', a)==0) {
					kind = Kind.START;
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
				if(Character.compare('+', a)==0) {
					kind = Kind.OP_PLUS;
				}
				else if(Character.compare('-', a)==0) {
					kind = Kind.OP_MINUS;
				}
				else if(Character.compare('*', a)==0) {
					kind = Kind.OP_TIMES;
				}
				else if(Character.compare('/', a)==0) {
					kind = Kind.START;
				}
				else if(Character.compare('%', a)==0) {
					kind = Kind.OP_MOD;
				}
				else if(Character.compare('^', a)==0) {
					kind = Kind.OP_POW;
				}
				else if(Character.compare('#', a)==0) {
					kind = Kind.OP_HASH;
				}
				else if(Character.compare('&', a)==0) {
					kind = Kind.BIT_AMP;
				}
				else if(Character.compare('~', a)==0) {
					kind = Kind.BIT_XOR;
				}
				else if(Character.compare('|', a)==0) {
					kind = Kind.BIT_OR;
				}
				else if(Character.compare(':', a)==0) {
					kind = Kind.START;
				}
				else if(Character.compare('<', a)==0) {
					kind = Kind.START;
				}
				else if(Character.compare('>', a)==0) {
					kind = Kind.START;
				}
				else if(Character.compare('=', a)==0) {
					kind = Kind.START;
				}
				
				break;
			}
			case ASSIGN:{
				if(Character.compare('=', a)==0) {
					kind = Kind.START;
					t = new Token(REL_EQEQ,"==",numPos,lineNo);
				}
				else {
					numPos-=1;
					kind = Kind.START;
					t = new Token(ASSIGN,"=",numPos,lineNo);
				}
				break;
			}
			case OP_PLUS:{
				if(Character.compare(a, '+')==0) {
					kind = Kind.START;
					t = new Token(OP_PLUS,"+",numPos,lineNo);
					break;
				}
			}
			case OP_MINUS:{
				if(Character.compare(a, '-')==0) {
					kind = Kind.START;
					t = new Token(OP_MINUS,"-",numPos,lineNo);
					break;
				}
			}
			case OP_TIMES:{
				if(Character.compare(a, '*')==0) {
					kind = Kind.START;
					t = new Token(OP_TIMES,"*",numPos,lineNo);
					break;
				}
			}
			case OP_DIV:{
				if(Character.compare('/', a) ==0) {
				kind = Kind.START;
			t = new Token(Kind.OP_DIVDIV,"//",numPos,lineNo);
				}
				else {
					kind = Kind.START;
					numPos-=1;
					t = new Token(OP_DIV,"/",numPos,lineNo);
				}
				break;
			}
			case DOT :
			{
				if(Character.compare('.', a) ==0) {
				kind = Kind.START;
				t = new Token(Kind.DOTDOT,"..",numPos,lineNo);
				if(numPos!=testString.length()-1) {
					kind = Kind.DOTDOT;
					t = getNext();
				}
				}
				else {
					kind = Kind.START;
					numPos-=1;
					t = new Token(DOT,".",numPos,lineNo);
				}
				break;
			}
			case DOTDOT :
			{
				if(Character.compare('.', a) ==0) {
					kind = Kind.START;
					t = new Token(DOTDOTDOT,"...",numPos,lineNo);
			}
				else {
					numPos-=1;
					kind = Kind.START;
					t = new Token(DOTDOT,"..",numPos,lineNo);
			}
				break;
			}
			case OP_MOD:{
				if(Character.compare(a, '%')==0) {
					kind = Kind.START;
					t = new Token(OP_MOD,"%",numPos,lineNo);
					break;
				}
			}
			case OP_POW:{
				if(Character.compare(a, '^')==0) {
					kind = Kind.START;
					t = new Token(OP_POW,"^",numPos,lineNo);
					break;
				}
			}
			case OP_HASH:{
				if(Character.compare(a, '#')==0) {
					kind = Kind.START;
					t = new Token(OP_HASH,"#",numPos,lineNo);
					break;
				}
			}
			case BIT_AMP:{
				if(Character.compare(a, '&')==0) {
					kind = Kind.START;
					t = new Token(BIT_AMP,"&",numPos,lineNo);
					break;
				}
			}
			case BIT_XOR:{
				if(Character.compare(a, '=')==0) {
					kind = Kind.START;
					t = new Token(REL_NOTEQ,"~=",numPos,lineNo);
				}
				else {
					numPos-=1;
					kind = Kind.START;
					t = new Token(BIT_XOR,"~",numPos,lineNo);
				}
				break;
			}
			case BIT_OR:{
				if(Character.compare(a, '|')==0) {
					kind = Kind.START;
					t = new Token(BIT_OR,"|",numPos,lineNo);
					break;
				}
			}
			case REL_LT:{
				if(Character.compare(a, '<')==0) {
					kind = Kind.START;
					t = new Token(BIT_SHIFTL,"<<",numPos,lineNo);
					break;
				}
				else if(Character.compare('=', a)==0) {
					kind = Kind.START;
					t = new Token(REL_LE,"<=",numPos,lineNo);
					break;
				}
				else {
					numPos-=1;
					kind = Kind.START;
					t = new Token(REL_LT,"<",numPos,lineNo);
					break;
				}
			
			}
			case REL_GT:{
				if(Character.compare(a, '>')==0) {
					kind = Kind.START;
					t = new Token(BIT_SHIFTR,">>",numPos,lineNo);
					break;
				}
				else if(Character.compare('=', a)==0) {
					kind = Kind.START;
					t = new Token(REL_GE,">=",numPos,lineNo);
					break;
				}
				else {
					numPos-=1;
					kind = Kind.START;
					t = new Token(REL_GT,">",numPos,lineNo);
					break;
				}
			
			}
				
			}
		 }
		sb = new StringBuilder();
		checkForKeyWords(t);
		return t;
}


	private void checkForKeyWords(Token t) {
		// TODO Auto-generated method stub
		
	}


	private void intializeInput(BufferedReader r) throws Exception {
		String line = r.readLine();
		testString = line;	
		kind = Kind.START;
	}
	}
