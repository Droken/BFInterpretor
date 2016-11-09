import java.util.Vector;

public class Memory {
	
	private final Vector<Character> memory;
	private int pointer;
	
	public Memory() {
		this.memory = new Vector<>();
		this.pointer = 0;
		this.addThousandMemSpace();
	}
	
	private void addThousandMemSpace() {
		for(int i = 0; i < 1000; i++)
			this.memory.add((char)0);
	}
	
	public Character current() {
		Character value;
		try {
			value = this.memory.elementAt(this.pointer);
			return value;
		} catch(ArrayIndexOutOfBoundsException e) {
			this.enlarge();
			System.err.println("[!] Pointer (" + this.pointer + ") is out of memory.\nMemory has been enlarged to " + this.memory.capacity() + " blocks.");
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
	public void dump(int deb,int fin) {
		for(int i = deb; (i++) < fin; System.out.print((int)this.memory.elementAt(i) + " "));
		System.out.println();
		for(int i = this.pointer-deb-1; i > 0; i--)
			System.out.print("  ");
		System.out.println('^');
	}
	
	public void dump() {
		int i = this.pointer;
		this.dump(Math.max(i-5, -1), Math.min(i+5, this.memory.capacity()-1));
	}
	
	public void enlarge() {
		this.addThousandMemSpace();
	}
	
	public boolean movePointerLeft() {
		if(this.pointer == 0)
			return false;
		
		this.pointer--;
		
		return true;
	}
	
	public void movePointerRight() {
		this.pointer++;
	}
}
