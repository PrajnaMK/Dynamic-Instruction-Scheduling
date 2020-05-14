
	import java.io.*;
	import java.text.DecimalFormat;
	import java.util.*;
public class sim {
	

		
		static int n=0, s=0;
		static int N=0,S=0;
		static int cycle =0;
		static String tracefile=null;
		static Stack <String> file = new Stack <String>();
		static Stack< String > file2= new Stack<String>();
		public static void main(String args[]) throws FileNotFoundException {
			
			String n1,s1;
			n1 = Integer.toString(n);
			s1= Integer.toString(s);
			s1=args[0];
			n1=args[1];
			tracefile = args[2];
			
			N= Integer.parseInt(n1);
			S= Integer.parseInt(s1);
			
			
			
			
			try {
				Scanner s = new Scanner(new File(tracefile));
				
				while(s.hasNextLine()) {
					String line = s.nextLine();
					
					file.push(line);
					
					
				}
			}
			catch(FileNotFoundException e) {
				System.out.println("file not found");
			}
			
				
			
			
			
		reverse();
		//PrintStream fileout = new PrintStream("myoutput5.txt");
		// System.setOut(fileout);
		
		
		int IPC = file2.size();
		Fetch f = new Fetch(file2,N,S);
		
		
	 do {
		  
		   f.execute(cycle);
		   f.issue(cycle);
		   f.Dispatch(cycle);
		   f.fetch(cycle);
		
			cycle ++;
			
			
		}
		while(f.advance_cycle());
	 
	 
	 
	 for(int j =0;j<f.ROB.size();j++) {
			Instruction t = f.ROB.get(j);
			System.out.print(j+" ");
			System.out.print("fu{"+t.type+"} ");
			System.out.print("src{"+t.R2+","+t.R3+"} ");
			System.out.print("dst{"+t.R1+"} ");
			System.out.print("IF{"+t.inst_cycle.get(0)+","+(t.inst_cycle.get(1)-t.inst_cycle.get(0))+"} ");
			System.out.print("ID{"+t.inst_cycle.get(1)+","+(t.inst_cycle.get(2)-t.inst_cycle.get(1))+"} ");
			System.out.print("IS{"+t.inst_cycle.get(2)+","+(t.inst_cycle.get(3)-t.inst_cycle.get(2))+"} ");
			System.out.print("EX{"+t.inst_cycle.get(3)+","+(t.inst_cycle.get(4)-t.inst_cycle.get(3))+"} ");
			System.out.print("WB{"+t.inst_cycle.get(4)+", 1} ");
			System.out.println(" ");
			
		}
	    System.out.println("number of instructions  ="+f.ROB.size());
	    System.out.println("number of cycles        ="+cycle);
	    double ipc= (double)IPC/cycle;
	    DecimalFormat df = new DecimalFormat("IPC                     ="+"#.#####");
	    System.out.println(df.format(ipc));

	   
		
		

	}
		static void reverse()
		{
			while(!file.isEmpty()) {
				 String a = file.pop();
				 file2.push(a);
			}
		}
		
		
	}


