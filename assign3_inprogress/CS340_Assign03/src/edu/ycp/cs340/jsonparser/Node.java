package edu.ycp.cs340.jsonparser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Node {
	private Symbol symbol;
	private Token token; // only for terminal symbols
	private List<Node> children; // for nonterminal nodes
	
	// Constructor for nonterminal symbols
	public Node(Symbol symbol) {
		this.symbol = symbol;
		this.token = null;
		children = new ArrayList<Node>();
	}
	
	// Constructor for terminal symbols
	public Node(Token token) {
		this.symbol = token.getSymbol();
		this.token = token;
		children = Collections.emptyList();
	}
	
	public Symbol getSymbol() {
		return symbol;
	}
	
	public Token getToken() {
		return token;
	}
	
	public List<Node> getChildren() {
		return children;
	}
	
	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append(symbol.toString());
		if (token != null) {
			buf.append("(\"");
			buf.append(token.getLexeme());
			buf.append("\")");
		}
		return buf.toString();
	}
}
