package edu.ycp.cs340.parser;

import java.io.IOException;
import java.io.Reader;

public class Lexer {
	private Reader r;
	private Token next;
	
	public Lexer(Reader r) {
		this.r = r;
		this.next = null;
	}
	
	public Token peek() {
		fill();
		return next;
	}
	
	public Token next() {
		fill();
		if (next == null) {
			throw new LexerException("Unexpected end of input");
		}
		Token result = next;
		next = null;
		return result;
	}
	
	private static final String LEGAL = "ab0123456789+-*/()";

	private void fill() {
		try {
			doFill();
		} catch (IOException e) {
			throw new LexerException("IOException looking for token", e);
		}
	}

	/**
	 * @throws IOException
	 */
	public void doFill() throws IOException {
		while (next == null) {
			int c = r.read();
			if (c < 0) {
				// Reached end of input
				return;
			}
			if (!Character.isWhitespace(c)) {
				if (LEGAL.indexOf(c) < 0) {
					throw new IOException("Illegal token: " + ((char)c));
				}
				next = new Token((char)c);
			}
		}
	}
}
