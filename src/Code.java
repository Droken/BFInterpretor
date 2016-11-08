import java.util.Vector;

public class Code extends AbstractOperator {
	
	private Vector<AbstractOperator> operators;
	
	protected boolean loop = false;
	
	public Code() {
		this.operators = new Vector<>();
	}
	
	public Code(String code) {
		code = this.clean(code);
		this.operators = new Vector<>(code.length());
		
		for(int i = 0; i < code.length(); i++) {
			char op = code.charAt(i);
			if(Operator.OPS_NOL.indexOf(op) != -1) {
				this.operators.add(new Operator(op));
			} else if(LoopOperator.OPS_LOOPS.indexOf(op) != -1) {
				this.operators.add(new LoopOperator(code.substring(i).split(""+LoopOperator.CHR_LEND)[0]));
			}
		}
	}
	
	private String clean(String code) {
		String s = new String();
		
		for(int i = 0; i < code.length(); i++)
			if(Operator.OPS_ALL.indexOf(code.charAt(i)) != -1)
				s += code.charAt(i);

		return s;
	}
	
	@Override
	public boolean run(Memory memory) {
		for(AbstractOperator op : this.operators) {
			op.run(memory);
		}
		
		return true;
	}
	
}
