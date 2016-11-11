public class LoopOperator extends Code {
	
	private int iterations = 0;
	
	public static char CHR_LSTART = '[';
	public static char CHR_LEND = ']';
	public static String OPS_LOOPS = ""+CHR_LSTART+CHR_LEND;
	
	public LoopOperator() {
		super();
		
		this.loop = true;
	}
	
	public LoopOperator(String code, int index) {
		super(code, index);
		this.loop = true;
	}
	
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
	
//	@Override
//	public int length() {
//		return super.length()+2;
//	}
	
	@Override
	public String getSymbol() {
		return '['+this.getTrace()+']';
	}
	
	@Override
	public String getSymbol(int targetIndex) {
		return '['+this.getTrace(this.length(), targetIndex)+']';
	}
	
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
