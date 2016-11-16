/**
 * @brief LoopOperator class
 * 
 * Represents a loop.
 * 
 * @file LoopOperator.java
 * @author Droken
 * @author ZanyMonk
 * @since 1.0
 */

public class LoopOperator extends Program {
	
	private int iterations = 0;
	
	public static char CHR_LSTART = '[';
	public static char CHR_LEND = ']';
	public static String OPS_LOOPS = ""+CHR_LSTART+CHR_LEND;
	
	public LoopOperator() {
		super();
		
		this.loop = true;
	}
	
	/**
	 * Constructs a LoopOperator with given code and index.
	 * 
	 * A LoopOperator can be contained in a Program or in another LoopOperator
	 * 
	 * @param code		The program as a string
	 * @param index		Position of the loop in its parent operator
	 */
	public LoopOperator(String code, int index) {
		super(code, index);
		this.loop = true;
	}
	
	/**
	 * Executes each operator action
	 * 
	 * @param memory	The Memory on which to work
	 * @return {@code false} if execution failed. {@code true} if it succeeded
	 */
	private boolean loop(Memory memory) {
		AbstractOperator o;
		
		this.opIndex = 0;
		
		while(this.opIndex < this.operators.size()) {
			o = this.operatorAt(this.opIndex);
			if(o.run(memory) == false)
				return false;

			this.opIndex++;
		}
		
		return true;
	}
	
	/**
	 * @return A complete (with sides brackets) string version of the LoopOperator
	 */
	@Override
	public String getSymbol() {
		return '['+this.getTrace()+']';
	}
	
	/**
	 * @param	targetIndex Position of the operator to target
	 * @return A complete string version of the LoopOperator with targeted operator colored
	 */
	@Override
	public String getSymbol(int targetIndex) {
		return '['+this.getTrace(targetIndex, this.getSymbol().length())+']';
	}
	
	/**
	 * Starts looping
	 * @param memory	The Memory on which to work
	 * @return {@code false} if execution failed. {@code true} if it succeeded.
	 */
	@Override
	public boolean run(Memory memory) {
		while(memory.current() != 0) {
			if(this.loop(memory) == false)
				return false;
			this.iterations++;
		}
		this.iterations = 0;
		
		return true;
	}
	
}
