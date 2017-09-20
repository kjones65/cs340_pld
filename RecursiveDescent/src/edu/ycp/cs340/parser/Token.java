package edu.ycp.cs340.parser;

public class Token {
	private Symbol symbol;
	private String lexeme;
	
	public Token(int c) {
		this.symbol = Symbol.fromCharacter(c);
		this.lexeme = "" + ((char)c);
	}

	public String getLexeme() {
		return lexeme;
	}
	
	public Symbol getSymbol() {
		return symbol;
	}
	
	@Override
	public String toString() {
		return lexeme;
	}
}
