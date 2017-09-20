package edu.ycp.cs340.parser;

import java.io.StringReader;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Enter expressions (type 'quit' when finished):");
		
		boolean done = false;
		while (!done) {
			if (!keyboard.hasNextLine()) {
				done = true;
			} else {
				String line = keyboard.nextLine();
				if (line == null) {
					done = true;
				} else {
					line = line.trim();
					if (line.toLowerCase().equals("quit")) {
						done = true;
					} else {
						StringReader sr = new StringReader(line);
						Lexer lexer = new Lexer(sr);
						Parser parser = new Parser(lexer);
						Node parseTree = parser.parse();
						TreePrinter tp = new TreePrinter();
						tp.print(parseTree);
					}
				}
			}
		}
	}
}
