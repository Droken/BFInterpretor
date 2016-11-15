/**
 * @file BFInterpreter.java
 * @author Droken
 * @author ZanyMonk
 * @brief A Brainfuck interpreter
 * 
 * Takes a string or a Brainfuck script file path as argument.
 */

import java.util.Vector;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class BFInterpreter{

	private Memory memory;
	private Program code;
	
	public static BFInterpreter instance;

	private void Init() {
		this.memory = new Memory(30000);
	}

	public static void main(String[] args) {
		if(args.length < 1) {
			System.err.println("Add a file or an inline program to interpret as argument");
			return;
		}

		File file = new File(args[0]);

		if(file.exists() && !file.isDirectory()) {
			instance = new BFInterpreter(file);
		} else {
			instance = new BFInterpreter(args[0]);
		}
		
		instance.run();

		System.out.println();	// End the program by printing a new line
	}

	public BFInterpreter(File f) {
		this(BFInterpreter.fromFile(f));
	}

	public BFInterpreter(String code)
	{
		Init();
		this.code = new Program(code);
	}
	
	public int getCodePointerPosition() {
		return this.code.getPointerPosition();
	}
	
	public void dump(int targetIndex) {
		int length = 50;
		String out = "";
		
		out += ' ' + new String(new char[length]).replace('\0', '-');
		
		out += this.memory.dump();
		
		out += "\n " + new String(new char[length]).replace('\0', '-') + '\n';
		
		String trace = this.code.getTrace(targetIndex, length);
		int colorControlChars = 14;
		int traceLength = trace.length()-colorControlChars; // Remove color control chars
		
		trace = trace.substring(0, Math.min(length, traceLength+colorControlChars));
		trace = trace + new String(new char[Math.max(0, length-traceLength)]).replace('\0', ' ');
		
		out += "| " + trace + " |";
		
		out += "\n " + new String(new char[length]).replace('\0', '-') + '\n';
		
		System.out.println(out);
	}
	
	public boolean run() {
		return this.code.run(this.memory);
	}

	static public String fromFile(File f) {
		String s = new String();
		try {
			s = (new Scanner(f)).useDelimiter("\\Z").next();
		} catch(IOException e) {
			System.err.println("Error loading the source file : " + e.getMessage());
		}
		
		return s;
	}
	
}
