public class LoopOperator extends Code {
	
	private String code;
	
	protected boolean loop = true;
	
	static public char CHR_LSTART = '[';
	static public char CHR_LEND = ']';
	static public String OPS_LOOPS = ""+CHR_LSTART+CHR_LEND;
	
	public LoopOperator() {
		super();
	}
	
	public LoopOperator(String code) {
		this.code = code;
	}
	
}
