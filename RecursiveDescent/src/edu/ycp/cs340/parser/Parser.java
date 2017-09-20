package edu.ycp.cs340.parser;

public class Parser {
	private Lexer lexer;
	
	public Parser(Lexer lexer) {
		this.lexer = lexer;
	}
	
	/*
	S → E
	E → T E'
	E' → + T E'
	E' → - T E'
	E' → epsilon
	T → F T'
	T' → * F T'
	T' → / F T'
	T' → epsilon
	F → a | b | 0 | 1 | 2 | ... | 9	
	F → ( E )
	*/
	
	public Node parseS() {
		Node s = new Node(Symbol.S);
		
		// S -> E
		s.getChildren().add(parseE());
		
		return s;
	}

	private Node parseE() {
		Node e = new Node(Symbol.E);
		
		// E -> T E'
		e.getChildren().add(parseT());
		e.getChildren().add(parseEPrime());
		
		return e;
	}

	private Node parseEPrime() {
		Node ePrime = new Node(Symbol.E_PRIME);
		Token tok = lexer.peek();
		if (tok == null) {
			// E' -> epsilon
			// epsilon production, do nothing
		} else {
			if (tok.getSymbol() != Symbol.PLUS && tok.getSymbol() != Symbol.MINUS) {
				// E' -> epsilon
				// epsilon production, do nothing
			} else {
				// E' -> + T E'
				// E' -> - T E'
				ePrime.getChildren().add(new Node(lexer.next())); // consume token
				ePrime.getChildren().add(parseT());
				ePrime.getChildren().add(parseEPrime());
			}
		}
		return ePrime;
	}

	private Node parseT() {
		Node t = new Node(Symbol.T);
		
		// T -> F T'
		t.getChildren().add(parseF());
		t.getChildren().add(parseTPrime());
		
		return t;
	}

	private Node parseTPrime() {
		Node tPrime = new Node(Symbol.T_PRIME);
		
		Token tok = lexer.peek();
		
		if (tok == null) {
			// T' → epsilon
			// epsilon production, do nothing
		} else {
			if (tok.getSymbol() != Symbol.TIMES && tok.getSymbol() != Symbol.DIVIDES) {
				// T' → epsilon
				// epsilon production, do nothing
			} else {
				// T' → * F T'
				// T' → / F T'
				tPrime.getChildren().add(new Node(lexer.next()));
				tPrime.getChildren().add(parseF());
				tPrime.getChildren().add(parseTPrime());
			}
		}
		
		return tPrime;
	}

	private Node parseF() {
		Node f = new Node(Symbol.F);
		
		Token tok = lexer.next();
		
		if (tok.getSymbol() == Symbol.INT_LITERAL) {
			// 	F → 0 | 1 | 2 | ... | 9	
			f.getChildren().add(new Node(tok));
		} else if (tok.getSymbol() == Symbol.IDENTIFIER) {
			// 	F → a | b
			f.getChildren().add(new Node(tok));
		} else if (tok.getSymbol() == Symbol.LEFT_PAREN ) {
			// 	F → ( E )
			f.getChildren().add(new Node(tok));
			f.getChildren().add(parseE());
			tok = lexer.next();
			if (tok.getSymbol() == Symbol.RIGHT_PAREN ){
				f.getChildren().add(new Node(tok));
			} else {
				throw new ParserException("Expected right parenthesis, but instead got " + tok);
			}	
		} else {
			throw new ParserException("Unexpected symbol looking for int literal or identifier: " + tok);
		}
		
		return f;
	}
}
