
import java.io.IOException;

public class Operator extends AbstractOperator {
	
	protected boolean loop = false;
	
	static public char CHR_INPUT = ',';
	static public char CHR_OUTPUT = '.';
	static public char CHR_PLUS = '+';
	static public char CHR_MINUS = '-';
	static public char CHR_RIGHT = '>';
	static public char CHR_LEFT = '<';
	static public char CHR_DUMP = '@';
	static public String OPS_CLASSIC = ""+CHR_INPUT+CHR_OUTPUT+CHR_PLUS+CHR_MINUS+CHR_RIGHT+CHR_LEFT;
	static public String OPS_NOL = OPS_CLASSIC+CHR_DUMP;
	static public String OPS_ALL = OPS_NOL+LoopOperator.OPS_LOOPS;
	
	public Operator() {
		this(Operator.CHR_NULL);
	}
	
	public Operator(char op) {
		this.op = op;
	}
	
	@Override
	public boolean run(Memory memory) {
		switch(this.op) {
		case '+':
			memory.set((char)((int)memory.current()+1));
			break;
		case '-':
			memory.set((char)((int)memory.current()-1));
			break;
		case '<':
			memory.movePointerLeft();
			break;
		case '>':
			try {
				memory.movePointerRight();
			} catch(ArrayIndexOutOfBoundsException e) {
				// Enlarge memory array when overflow
				System.out.println("OKAY C'EST BON !!!");
				memory.enlarge();
			}
			break;
		case '.':
			System.out.print(memory.current());
			break;
		case ',':
			try {
				memory.set((char)System.in.read());
			} catch(IOException e) {
				System.err.println("Error executing the source file : " + e.getMessage());
			}
			break;
		case '@':
			memory.dump();
			break;
		default:
			return false;
		}
		return true;
	}
}
