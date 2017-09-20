package edu.ycp.cs340.jsonparser;

public class Token {
	private Symbol symbol;
	private String lexeme;
	
	public Token(Symbol symbol, String lexeme) {
		this.symbol = symbol;
		this.lexeme = lexeme;
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
