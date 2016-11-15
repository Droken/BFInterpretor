/**
 * @file Program.java
 * @author Droken
 * @author ZanyMonk
 * @brief Program class
 * 
 * Represent an entire program.
 * Can be instanciated from a code string
 */

import java.util.Vector;

public class Program extends AbstractOperator {
	
	private int pointer;
	
	protected Vector<AbstractOperator> operators;
	protected int opIndex;
	
	public Program() {
		this(-1);
	}
	
	public Program(int index) {
		this.loop = false;
		this.operators = new Vector<>();
		this.pointer = 0;
		this.index = index;
	}
	
	public Program(String code) {
		this(code, -1);
	}
	
	public Program(String code, int index) {
		this(index);
		code = this.clean(code);
		
		if(		code.length() == 1
			&&	code.charAt(0) == LoopOperator.CHR_LEND)
			code = "";
		else if(		code.charAt(0) == LoopOperator.CHR_LSTART
			&&	code.charAt(code.length()-1) == LoopOperator.CHR_LEND) {	// Trim loop wards if the whole code is in a loop
			code = code.substring(1, code.length()-1);
		}
		
		for(int i = 0; i < code.length(); i++) {
			char c = code.charAt(i);
			if(Operator.OPS_NOL.indexOf(c) != -1) {
				this.operators.add(new Operator(c, i+Math.max(0, this.index)));
			} else if(LoopOperator.OPS_LOOPS.indexOf(c) != -1) {
				String loopCode = this.extractLoop(code, i);
				this.operators.add(new LoopOperator(loopCode, i+Math.max(0, this.index)));
				i += loopCode.length()+1;
			}
		}
	}
	
	private String extractLoop(String code, int start) {
		String r = "";
		boolean found = false;
		char cur;
		int i = 0;
		int indent = 0;
		
		code = code.substring(start);
		
		while(!found) {
			cur = code.charAt(i);
			if(cur == '[') {
				indent++;
			} else if(cur == ']') {
				indent--;
			}
			if(indent > 0) {
				r += cur;
			} else {
				found = true;
			}
			i++;
		}
		
		return r.substring(1);
	}
	
	private String clean(String code) {
		String s = new String();
		
		char c;
		for(int i = 0; i < code.length(); i++) {
			c = code.charAt(i);
			if(Operator.OPS_ALL.indexOf(c) != -1)
				s += c;
		}

		return s;
	}
	
	public int getPointerPosition() {
		return this.pointer;
	}
	
	public AbstractOperator operatorAt(int index) {
//		System.err.println(this.opIndex+" in "+this.index);
//		if(index > this.length()-1) {
//			System.err.println("Execution pointer ("+index+") out of stack.");
//			Runtime.getRuntime().exit(1);
//		}
		
		return this.operators.elementAt(index);
	}
	
	@Override
	public String getTrace() {
		return this.getTrace(this.length(), -1);
	}
	
	public String getTrace(int targetIndex, int length) {
		String trace = "", s;
		AbstractOperator o;
		
		int start;
		int end;
		
		if(targetIndex > -1) {
			start = 0;
			end = this.operators.size()-1;
		} else {
			start = Math.max(0, targetIndex-Math.max(10, length/2));
			end = Math.min(Math.max(10, targetIndex+length/2), this.operators.size()-1);
		}
		int opI = start;
		
		System.out.println("target: "+targetIndex);
		System.out.println("ptr: "+this.pointer);
		System.out.println("from "+start+" to "+end+'\n');
		
		for(int i = start; i <= end; opI++) {
			o = this.operators.elementAt(opI);
			
			if(o.isLoop()) { // Find it in child loop
				trace += o.getSymbol(targetIndex);
			} else if(
					this.index == -1				// We are at root
				&&	!o.isLoop()						// Op is not a loop
				&&	targetIndex == o.getIndex()	// Op index matches
			) {										// Then add pointer annotation
					trace += (char)27+"[41;30m" + o.getSymbol() + (char)27+"[49;39m";
					System.out.println("FOUND : "+o.getSymbol()+" at "+opI);
			} else
				trace += o.getSymbol();
			
			i += o.length();
			
			if(!this.isLoop())
				i -= o.length()-1;
		}
		
		return trace;
	}
	
	@Override
	public String getSymbol() {
		String symbol = "";
		
		symbol = this.operators.stream().map((o) -> o.getSymbol()).reduce(symbol, String::concat);
		
		return symbol;
	}
	
	@Override
	public boolean run(Memory memory) {
		AbstractOperator o;
		while(this.pointer < this.operators.size()) {
			o = this.operatorAt(this.pointer);
			try {
				o.run(memory);
			} catch(StackOverflowError e) {
				e.printStackTrace();
				System.err.println("Your program likely presents an infinite loop.\nExecution aborted. ");
				Runtime.getRuntime().exit(1);
			}
			this.opIndex += o.length();
			this.pointer++;
		}
		
		return true;
	}
	
	@Override
	public int length() {
		int length = 0;
		
		length = this.operators.stream().map((o) -> o.length()).reduce(length, Integer::sum);
		
		return length;
	}
	
}
