package edu.ycp.cs340.jsonparser;

import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) {
		System.out.println("Enter a JSON value.");
		
		InputStreamReader in = new InputStreamReader(System.in);
		Lexer lexer = new Lexer(in);
		JSONParser parser = new JSONParser(lexer);
		Node parseTree = parser.parseValue();
		TreePrinter tp = new TreePrinter();
		tp.print(parseTree);
	}
}
