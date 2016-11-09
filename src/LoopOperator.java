public class LoopOperator extends Code {
	
	protected boolean loop = true;
	
	static public char CHR_LSTART = '[';
	static public char CHR_LEND = ']';
	static public String OPS_LOOPS = ""+CHR_LSTART+CHR_LEND;
	
	public LoopOperator() {
		super();
	}
	
	public LoopOperator(String code) {
		super(code);
	}
	
	@Override
	public String getSymbol() {
		return '['+super.getSymbol()+']';
	}
	
	@Override
	public boolean run(Memory memory) {
		super.run(memory);
		if(memory.current() != 0)
			this.run(memory);
		
		return true;
	}
	
}
