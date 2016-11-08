import java.util.*;
import java.io.*;

public class BrainfuckInterpreter
{

  private Vector<Character> Buff;
  private int Pointer;

  private String Code;

  private void Init()
  {
      Code = new String();
      Pointer=0;
      Buff = new Vector<Character>(1000);
      for (int i=0;i<1000;i++) Buff.add((char)0);
  }

  public static void main(String[] args)
  {
    if(args.length<1){System.out.println("Add a file to interpret as argument"); return;}

  	File file = new File(args[0]);
  	BrainfuckInterpreter bi;
  	if(file.exists() && !file.isDirectory()) {
  		bi = new BrainfuckInterpreter(file);
  	} else {
  		bi = new BrainfuckInterpreter(args[0]);
  	}

    bi.runCode();

    //End the program by printing a new line feed
    System.out.println();
  }

  public BrainfuckInterpreter(File f)
  {
    loadFile(f);
  }

  public BrainfuckInterpreter(String _code)
  {
    Init();
    Code = _code;
  }

  public BrainfuckInterpreter()
  {
    Init();
  }

  public void loadFile(File f)
  {
    Init();
    try {
      Code = (new Scanner(f)).useDelimiter("\\Z").next();
    }
    catch(IOException e) { System.err.println("Error loading the source file : " + e.getMessage());}
  }

  private void executeLoop(String loop)
  {
    while((int)Buff.elementAt(Pointer)!=0)
    {
      runCode(loop);
    }

  }

  private void displayArray(int deb,int fin)
  {
    for(int i=deb;(i++)<fin;System.out.print((int)Buff.elementAt(i)+" "));
  }

  private void scanLoop(String loop)
  {
    @SuppressWarnings("unchecked")
    Vector<Character> b = (Vector<Character>)Buff.clone();
    int p2 = Pointer;
    Pointer = 0;
    for (int i=0;i<1000;i++) Buff.add((char)0);
    System.out.println(loop);
    runCode(loop);
    displayArray(0,10);System.out.println();
    for(int i=1;i<Pointer;i++)System.out.print(" ");
    System.out.println("^");
    Buff=b;
    Pointer=p2;
  }

  private void executeOp(char op)
  {
    if(".,<>+-".indexOf(op)==-1);
    else
    {
      switch(op)
      {
        case '+':
            Buff.set(Pointer, (char)((int)Buff.elementAt(Pointer)+1));
          break;
        case '-':
            Buff.set(Pointer, (char)((int)Buff.elementAt(Pointer)-1));
          break;
        case '<':
            Pointer--;
          break;
        case '>':
            Pointer++;
          break;
        case '.':
            System.out.print(Buff.elementAt(Pointer));
          break;
        case ',':
          try
          {
            Buff.set(Pointer,(char)System.in.read());
          }
          catch(IOException e) { System.err.println("Error executing the source file : " + e.getMessage()); }
          break;
      }//switch

    }//else

  }

  private void runCode(String s)
  {
    for(int i=0;i<s.length();++i)
    {
      char op=s.charAt(i);
      if(".,<>+-".indexOf(op)!=-1) executeOp(op);
      else if(op=='[')
      {
        String loop = getLoop(s.substring(i));
        while((int)Buff.elementAt(Pointer)!=0)
          runCode(loop);
        i+=loop.length();
      }//else if op==[
    }//for

  }//runCode

  private String getLoop(String base)
  {
    String loop = new String("");
    for(int j=0,b = 0;;j++)
    {
      char op=base.charAt(j);
      if((b<=1&&op==']'))break;
      if(op=='[')      b++;
      else if(op==']') b--;
      if(j>0)loop+=op;
    }//for
    return trimCode(loop);
  }

  private String trimCode(String code)
  {
    String s = new String();
    for(int i=0;i<code.length();i++)if(".,<>+-[]".indexOf(code.charAt(i))!=-1)s+=code.charAt(i);
    return s;
  }

  public void runCode()
  {
    try {
      runCode(trimCode(Code));
    }
    catch(java.lang.ArrayIndexOutOfBoundsException e) { System.out.println();System.out.println();displayArray(0,10);System.out.println("  |  Erreur dans la case de l'array numero "+Pointer+" ("+Buff.elementAt(Pointer)+" or "+(int)Buff.elementAt(Pointer)+") : ");}
  }
}
