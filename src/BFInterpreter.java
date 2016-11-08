import java.util.Vector;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class BFInterpreter{

	private Memory memory;
	private Code code;

	private void Init() {
		this.memory = new Memory();
	}

	public static void main(String[] args) {
		if(args.length < 1) {
			System.err.println("Add a file or an inline program to interpret as argument");
			return;
		}

		BFInterpreter bi;
		File file = new File(args[0]);

		if(file.exists() && !file.isDirectory()) {
			bi = new BFInterpreter(file);
		} else {
			bi = new BFInterpreter(args[0]);
		}

		System.out.println();	// End the program by printing a new line
	}

	public BFInterpreter(File f) {
		this(BFInterpreter.fromFile(f));
	}

	public BFInterpreter(String code)
	{
		Init();
		this.code = new Code(code);
		this.code.run(this.memory);
	}

	static public String fromFile(File f) {
		String s = new String();
		try {
			s = (new Scanner(f)).useDelimiter("\\Z").next();
		} catch(IOException e) {
			System.err.println("Error loading the source file : " + e.getMessage());
		}
		
		return s;
	}
	
}
