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

    BrainfuckInterpreter bi = new BrainfuckInterpreter(new File(args[0]));
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

  private void executeLoop(String loop) //Boucles infinies quand imbriquées
  {
    //System.out.print("[");
    //int i=0;
    //int min=100000;
    //displayArray(0,4);System.out.println(" | Before loop we're in : "+Pointer +" | "+ (int)Buff.elementAt(Pointer)+" | Test = "+((Pointer<0)?"true":"false"));
    //System.out.println("At the "+Pointer+"th case of the array we have "+(int)Buff.elementAt(Pointer)+" so (Buff[pointer]!=0)="+((Buff.elementAt(Pointer)!=0)?"true":"false"));
    while((int)Buff.elementAt(Pointer)!=0)
    {
      //if(Buff.elementAt(Pointer)<min)min = Buff.elementAt(Pointer);
      //System.out.println("Tour #"+(i++)+"\tPointer on "+(int)Buff.elementAt(Pointer)+" : ["+Pointer+"]");
      runCode(loop);
      //System.out.print(" | tour "+i+"+>"+(int)Buff.elementAt(Pointer)+"| ");
      //i++;
      //if(i>=100)System.out.print("Erreur 100+ tours");
      //System.out.println(" Pointer on "+Pointer);
    }
    //System.out.print("]");

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
      //System.out.print(op);
      switch(op)
      {
        case '+':
            Buff.set(Pointer, (char)((int)Buff.elementAt(Pointer)+1));
          break;
        case '-':
            Buff.set(Pointer, (char)((int)Buff.elementAt(Pointer)-1));
          break;
        case '<':
            //System.out.print(Pointer+" to ");
            Pointer--;
            //System.out.println(Pointer);
          break;
        case '>':
            //System.out.print(Pointer+" to ");
            Pointer++;
            //System.out.println(Pointer);
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
      //displayArray(0,4);System.out.println(" after "+op);

  }

  private void runCode(String s)
  {
    //System.out.println("Running "+s);
    for(int i=0;i<s.length();++i)
    {
      //System.out.println("Instruction #"+i);
      char op=s.charAt(i);
      if(".,<>+-".indexOf(op)!=-1) executeOp(op);
      else if(op=='[')
      {
        String loop = getLoop(s.substring(i));
        //scanLoop(loop);
        //displayArray(0,5);
        while((int)Buff.elementAt(Pointer)!=0)
          runCode(loop);
        i+=loop.length()-1;
      }//else if op==[
    }//for
    //System.out.println("Executed : "+(int)Buff.elementAt(Pointer));

  }//runCode

  private String getLoop(String base)
  {
    //System.out.println(base);
    String loop = new String("");
    for(int j=0,b = 0;;j++)
    {
      char op=base.charAt(j);
      //System.out.println(op + " "+ b +"\t"+j+"/"+base.length()+"\tP="+Pointer+"/"+Buff.capacity()+"=>\t"+(int)Buff.elementAt(Pointer));
      if((b<=1&&op==']'))break;
      if(op=='[')      b++;
      else if(op==']') b--;
      if(j>0)loop+=op;
      //System.out.println(loop);
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
