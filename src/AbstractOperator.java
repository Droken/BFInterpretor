public abstract class AbstractOperator {
	
	protected boolean loop = false;
	protected char op = CHR_NULL;
	protected int index = -2; // -2 = error; -1 = main code; >0 = op index
	
	public static char CHR_NULL = '#';
	
	public AbstractOperator() { }
	
	public boolean isLoop() { return this.loop; }
	public int length() { return 1; }
	public int getIndex() { return this.index; }
	public String getSymbol() { return ""+this.op; };
	public String getSymbol(int targetIndex) { return this.getSymbol(); };
	public String getTrace() { return this.getSymbol(); };
	
	public abstract boolean run(Memory memory);
	
}
