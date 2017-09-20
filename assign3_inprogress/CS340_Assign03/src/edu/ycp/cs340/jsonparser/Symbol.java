package edu.ycp.cs340.jsonparser;

public enum Symbol {
	// Terminal symbols
	STRING_LITERAL,
	INT_LITERAL,
	COMMA,
	COLON,
	LBRACE,
	RBRACE,
	LBRACKET,
	RBRACKET,
	
	// Nonterminal symbols
	VALUE,
	OBJECT,
	OPT_FIELD_LIST,
	FIELD_LIST,
	FIELD,
	ARRAY,
	OPT_VALUE_LIST,
	VALUE_LIST,
	;
	
}
