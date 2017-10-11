package edu.ycp.cs340.jsonparser;

public class JSONParser {
	private Lexer lexer;
	
	public JSONParser(Lexer lexer) {
		this.lexer = lexer;
	}
	
	public Node parseValue() {
		Node value = new Node(Symbol.VALUE);
		
		Token tok = lexer.peek();
		
		if (tok == null) {
			throw new ParserException("Unexpected end of input reading value");
		}
		
		if (tok.getSymbol() == Symbol.STRING_LITERAL) {
			// Value --> StringLiteral
			value.getChildren().add(expect(Symbol.STRING_LITERAL));
		} else if (tok.getSymbol() == Symbol.INT_LITERAL) {
			// Value --> IntLiteral
			value.getChildren().add(expect(Symbol.INT_LITERAL));
		} else if (tok.getSymbol() == Symbol.LBRACE) {
			// Value --> Object
			value.getChildren().add(parseObject());
		} else if (tok.getSymbol() == Symbol.LBRACKET) {
			// Value --> Array
			value.getChildren().add(parseArray());
		} else {
			throw new ParserException("Unexpected token looking for value: " + tok);
		}
		
		return value;
	}

	private Node parseObject() {
		Node object = new Node(Symbol.OBJECT);
		
		// Object --> "{" OptFieldList "}"
		object.getChildren().add(expect(Symbol.LBRACE));
		object.getChildren().add(parseOptFieldList());
		object.getChildren().add(expect(Symbol.RBRACE));
		
		return object;
	}

	private Node parseOptFieldList() {
		Node optFieldList = new Node(Symbol.OPT_FIELD_LIST);
		Token tok = lexer.peek();
		
//		OptFieldList --> FieldList | epsilon
		if(tok.getSymbol() == Symbol.RBRACE) {
			//epsilon, do nothing
		} else {
			optFieldList.getChildren().add(parseFieldList());
		}
		
		return optFieldList;
	}
	
	private Node parseFieldList() {
		Node fieldList = new Node(Symbol.FIELD_LIST);
		
		//FieldList --> Field | Field “,” FieldList
		fieldList.getChildren().add(parseField());
		Token tok = lexer.peek();
		
		if(tok.getSymbol()==Symbol.COMMA) {
			fieldList.getChildren().add(new Node(lexer.next())); //add comma to tree
			fieldList.getChildren().add(parseFieldList());
		}
		
		return fieldList;
	}
	
	private Node parseField() {
		Node field = new Node(Symbol.FIELD);
		
		// Field --> StringLiteral “:” Value
		field.getChildren().add(expect(Symbol.STRING_LITERAL));
		field.getChildren().add(expect(Symbol.COLON));
		field.getChildren().add(parseValue());
		
		return field;
		
	}
	
	private Node parseArray() {
		Node array = new Node(Symbol.ARRAY);
		
		// Array --> “[” OptValueList “]”
		array.getChildren().add(expect(Symbol.LBRACKET));
		array.getChildren().add(parseOptValueList());
		array.getChildren().add(expect(Symbol.RBRACKET));
		
		return array;
	}
	
	private Node parseOptValueList() {
		Node optValueList = new Node(Symbol.OPT_VALUE_LIST);
		Token tok = lexer.peek();
//		OptValueList --> ValueList | epsilon
		if(tok.getSymbol() == Symbol.RBRACKET) {
			//epsilon, do nothing
		} else {
			optValueList.getChildren().add(parseValueList());
		}
		
		return optValueList;
	}
	
	private Node parseValueList() {
		Node valueList = new Node(Symbol.VALUE_LIST);
		
//		ValueList --> Value | Value “,” ValueList
		valueList.getChildren().add(parseValue());
		Token tok = lexer.peek();
		
		if(tok.getSymbol()==Symbol.COMMA) {
			valueList.getChildren().add(new Node(lexer.next())); // add comma to tree
			valueList.getChildren().add(parseValueList());
		}
		
		return valueList;
	}

	private Node expect(Symbol symbol) {
		Token tok = lexer.next();
		if (tok.getSymbol() != symbol) {
			throw new ParserException("Unexpected token " + tok + " (was expecting " + symbol + ")");
		}
		return new Node(tok);
	}
}
