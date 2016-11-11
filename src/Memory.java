import java.util.Vector;

public class Memory {
	
	private final Vector<Character> memory;
	private int pointer;
	private BFInterpreter interpreter;
	
	public Memory(int size, BFInterpreter interpreter) {
		this.interpreter = interpreter;
		this.memory = new Vector<>();
		this.pointer = 0;
		this.addMemSpace(size);
	}
	
	private void addMemSpace(int n) {
		for(int i = 0; i < n; i++)
			this.memory.add((char)0);
	}
	
	public Character current() {
		Character value;
		try {
			value = this.memory.elementAt(this.pointer);
			return value;
		} catch(ArrayIndexOutOfBoundsException e) {
			this.enlarge();
			System.err.println("[!] Pointer (" + this.pointer + ") is out of memory.\nMemory has been enlarged to " + this.memory.size() + " blocks.");
			return this.current();
		}
		
	}
	
	public boolean set(char input) {
		if(input > 255)
			return false;
		
		this.memory.setElementAt(input, this.pointer);
		
		return true;
	}
	
	@SuppressWarnings("empty-statement")
	public String dump(int lines) {
		int last = this.memory.size()-1;
		int start = Math.max(this.pointer-this.pointer%10-31, 0);
		int end = start+10*lines-1;
		int n, length = 50;
		
		String out = "";

		for(int i = start; i <= end; i++) {
			n = (int)this.memory.elementAt(i);
			
			if((i-start%10)%10 == 0 && i != end)
				out += "\n| " + new String(new char[5-Integer.toString(i).length()]).replace("\0", " ")+i+"  |";
			
			if(i == this.pointer)
				out += (char)27+"[32m";
			
			out += new String(new char[4-Integer.toString(n).length()]).replace("\0", " ") + n;
			
			if(i == this.pointer)
				out += (char)27+"[39m";
			
			if((i-start%10+1)%10 == 0 || i == end)
				out += " |";
			
		}
		
		return out;
//		System.out.println(out);
//		this.interpreter.dump(length);
	}
	
	public String dump() {
		return this.dump(10);
	}
	
	public void enlarge() {
		this.addMemSpace(1000);
	}
	
	public boolean movePointerLeft() {
		this.pointer--;
		
		if(this.pointer < 0) {
			this.pointer = this.memory.size()-1;
		}
		
		return true;
	}
	
	public void movePointerRight() {
		this.pointer++;
	}
}
