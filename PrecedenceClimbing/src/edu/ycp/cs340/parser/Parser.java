package edu.ycp.cs340.parser;

public class Parser {
	public enum Associativity {
		LEFT,
		RIGHT,
	}
	
	private Lexer lexer;
	
	public Parser(Lexer lexer) {
		this.lexer = lexer;
	}
	
	public Node parse() {
		return parse1(parsePrimary(), 0);
	}
	
	private Node parse1(Node lhs, int minPrecedence) {
		// while the next token is a binary operator whose precedence is >= min_precedence
		while (true) {
			Token op = lexer.peek();
			if (op == null || !isOp(op) || prec(op) < minPrecedence) {
				break;
			}
			lexer.next(); // consume operator token

			Node rhs = parsePrimary();

			// while the next token is
			//   (1) a binary operator whose precedence is greater than op's, or
			//   (2) a right-associative operator whose precedence is equal to op's
			while (true) {
				Token lookahead = lexer.peek();
				if (lookahead == null ||
					!isOp(lookahead) ||
					!(prec(lookahead) > prec(op) ||
					  assoc(lookahead) == Associativity.RIGHT && prec(lookahead) == prec(op))) {
					break;
				} else {
					rhs = parse1(rhs, prec(lookahead));
				}
			}
			
			Node n = new Node(op.getSymbol());
			n.getChildren().add(lhs);
			n.getChildren().add(rhs);
			lhs = n;
		}
		
		return lhs;
	}

	// Is given token an operator?
	private boolean isOp(Token tok) {
		switch (tok.getSymbol()) {
		case PLUS:
		case MINUS:
		case TIMES:
		case DIVIDES:
		case EXP:
			return true;
		default:
			return false;
		}
	}

	// Get precedence of given operator token
	private int prec(Token op) {
		switch (op.getSymbol()) {
		case PLUS:
		case MINUS:
			return 0;
		case TIMES:
		case DIVIDES:
			return 1;
		case EXP:
			return 2;
		default:
			throw new IllegalArgumentException(op + " is not an operator");
		}
	}

	// Get associativity of given operator token
	private Associativity assoc(Token op) {
		switch (op.getSymbol()) {
		case PLUS:
		case MINUS:
		case TIMES:
		case DIVIDES:
			return Associativity.LEFT;
		case EXP:
			return Associativity.RIGHT;
		default:
			throw new IllegalArgumentException(op + " is not an operator");
		}
	}

	private Node parsePrimary() {
		Node f = new Node(Symbol.F);
		
		Token tok = lexer.next();
		
		if (tok.getSymbol() == Symbol.INT_LITERAL) {
			// 	F → 0 | 1 | 2 | ... | 9	
			f.getChildren().add(new Node(tok));
		} else if (tok.getSymbol() == Symbol.IDENTIFIER) {
			// 	F → a | b
			f.getChildren().add(new Node(tok));
		} else if (tok.getSymbol() == Symbol.LEFT_PAREN) {
			// 	F -> ( E )
			Node e = new Node(Symbol.E);
			e.getChildren().add(new Node(tok));
			e.getChildren().add(parse());
			tok = lexer.next();
			if(tok.getSymbol() == Symbol.RIGHT_PAREN) {
				e.getChildren().add(new Node(tok));
			} else {
				throw new ParserException("Expected a right parenthesis, instead got :" + tok);
			}
			
			return e;
			
		} else {
			throw new ParserException("Unexpected symbol looking for int literal or identifier: " + tok);
		}
		
		return f;
	}
}
