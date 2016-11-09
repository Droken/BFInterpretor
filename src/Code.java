import java.util.Vector;

public class Code extends AbstractOperator {
	
	private Vector<AbstractOperator> operators;
	
	protected boolean loop = false;
	
	public Code() {
		this.operators = new Vector<>();
	}
	
	public int countOperators(String code) {
		int count = 0;
		for(int i = 0; i < code.length(); i++) {
			count++;
		}
		
		return count;
	}
	
	public Code(String code) {
		code = this.clean(code);
		this.operators = new Vector<>(code.length());
		
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
				this.operators.add(new Operator(c));
			} else if(LoopOperator.OPS_LOOPS.indexOf(c) != -1) {
				String loopCode = this.extractLoop(code, i);
//				System.out.println("CLEAN : "+loopCode);
				this.operators.add(new LoopOperator(loopCode));
				i += loopCode.length()+1;
			}
		}
	}
	
	@Override
	public String getSymbol() {
		String symbol = "";
			
		for(AbstractOperator o : this.operators) {
			symbol += o.getSymbol();
		}
		
		return symbol;
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
	
	@Override
	public boolean run(Memory memory) {
			for(AbstractOperator o : this.operators) {
			try {
				o.run(memory);
			} catch(StackOverflowError e ) {
				System.err.println("Your program likely presents an infinite loop.\nExecution aborted.");
				Runtime.getRuntime().exit(1);
			}
		}
		
		return true;
	}
	
}
