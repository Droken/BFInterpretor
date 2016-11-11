public class LoopOperator extends Code {
	
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
		AbstractOperator o;
		while(memory.current() != 0) {
			o = this.operatorAt(this.pointer);
			o.run(memory);
			this.pointer++;
		}
		
		return true;
	}
	
}
