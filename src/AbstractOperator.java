public abstract class AbstractOperator {
	
	protected boolean loop = false;
	protected char op = CHR_NULL;
	
	static public char CHR_NULL = '#';
	
	public AbstractOperator() { }
	
	public boolean isLoop() { return this.loop; }
	
	public String getSymbol() { return ""+this.op; }
	
	public abstract boolean run(Memory memory);
	
}
