/**
 * @file Operator.java
 * @author Droken
 * @author ZanyMonk
 * @brief Operator class
 * 
 * Represents a single op code.
 */

import java.io.IOException;

public class Operator extends AbstractOperator {
	
	
	public static char CHR_INPUT = ',';
	public static char CHR_OUTPUT = '.';
	public static char CHR_PLUS = '+';
	public static char CHR_MINUS = '-';
	public static char CHR_RIGHT = '>';
	public static char CHR_LEFT = '<';
	public static char CHR_DUMP = '@';
	public static String OPS_CLASSIC = ""+CHR_INPUT+CHR_OUTPUT+CHR_PLUS+CHR_MINUS+CHR_RIGHT+CHR_LEFT;
	public static String OPS_NOL = OPS_CLASSIC+CHR_DUMP;
	public static String OPS_ALL = OPS_NOL+LoopOperator.OPS_LOOPS;
	
	public Operator() {
		this(Operator.CHR_NULL, -2);
	}
	
	public Operator(char op, int index) {
		this.op = op;
		this.index = index;
	}
	
	@Override
	public boolean run(Memory memory) {
		switch(this.op) {
		case '+':
			memory.set((char)((int)memory.current()+1));
			break;
		case '-':
			int n = (int)memory.current()-1;
			if(n < 0) {
				n = 255;
			}
			memory.set((char)n);
			break;
		case '<':
			memory.movePointerLeft();
			break;
		case '>':
			memory.movePointerRight();
			break;
		case '.':
			System.out.print(memory.current());
			break;
		case ',':
			try {
				char in = (char)System.in.read();
				
				memory.set(in == '\n' ? 0 : in);
				if(System.in.available() > 0)
					System.in.skip(1); // Skip newline
			} catch(IOException e) {
				System.err.println("Invalid input.");
			}
			break;
		case '@':
			BFInterpreter.instance.dump(this.index);
			break;
		default:
			return false;
		}
		return true;
	}
}
