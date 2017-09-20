package edu.ycp.cs340.jsonparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
	private BufferedReader r;
	private String line;
	private boolean atEOF;
	private Token next;
	
	private static class TokenPattern {
		final Pattern pattern;
		final Symbol symbol;
		TokenPattern(String regex, Symbol symbol) {
			this.pattern = Pattern.compile(regex);
			this.symbol =  symbol;
		}
	}
	
	private static final TokenPattern[] TOKEN_PATTERNS = {
		new TokenPattern("^[0-9]+", Symbol.INT_LITERAL),
		new TokenPattern("^\"([^\"\\\\]|\\\\|\\\\\")*\"", Symbol.STRING_LITERAL),
		new TokenPattern("^,", Symbol.COMMA),
		new TokenPattern("^:", Symbol.COLON),
		new TokenPattern("^\\{", Symbol.LBRACE),
		new TokenPattern("^\\}", Symbol.RBRACE),
		new TokenPattern("^\\[", Symbol.LBRACKET),
		new TokenPattern("^\\]", Symbol.RBRACKET),
	};
	
	public Lexer(Reader r) {
		this.r = new BufferedReader(r);
		this.line = null;
		this.atEOF = false;
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
	
	private void fill() {
		if (next != null) {
			// We have a cached token
			return;
		}
		if (!fillLine()) {
			// There isn't any more input left to read
			return;
		}
		for (TokenPattern tp : TOKEN_PATTERNS) {
			Matcher m = tp.pattern.matcher(line);
			if (m.find()) {
				// Matched a token!
				Symbol symbol = tp.symbol;
				String lexeme = m.group();
				Token token = new Token(symbol, lexeme);
				
				// Consume input characters from current line
				line = line.substring(lexeme.length()).trim();
				
				// Cache the token
				next = token;
				return;
			}
		}
		// Could not find a pattern to match the current input
		throw new LexerException("Invalid token: " + line);
	}

	private boolean fillLine() {
		if (atEOF) {
			// We've already reached the end of input
			return false;
		}
		while (line == null || line.trim().equals("")) {
			try {
				// Attempt to read a line
				String inputLine = r.readLine();
				if (inputLine == null) {
					// We've reached the end of input
					atEOF = true;
					return false;
				}
				inputLine = inputLine.trim();
				if (!inputLine.equals("")) {
					// Got an input line
					line = inputLine;
				}
			} catch (IOException e) {
				throw new LexerException("Error reading input", e);
			}
		}
		return true;
	}
}
