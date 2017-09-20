package edu.ycp.cs340.jsonparser;

import java.io.IOException;

/**
 * Exception indicating an error performing lexical analysis.
 */
public class LexerException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public LexerException(String msg) {
		super(msg);
	}

	public LexerException(String msg, IOException e) {
		super(msg, e);
	}
}
