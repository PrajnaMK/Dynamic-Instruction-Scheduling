

import java.util.*;

import java.util.Arrays;

public class Fetch {
	
	HashMap < Integer, Instruction> ROB = new HashMap<Integer, Instruction>();
	String trace;
	int tag =0,N=0,S=0,new_size=0,dispatch_count=0;
	public  String state=null;
	Stack<String> stack = new Stack<String>();
	int cycle_count=0;
	int count =0,latency=0;
	int instrcution_count=0, issue_count=0, execute_count=0;
	ArrayList<Integer> dispatch_list =new ArrayList<Integer>(2*this.N) ;
	ArrayList<Integer> issue_list = new ArrayList<Integer>(this.S);
	String S1_depend,S2_depend;
	int S1_tag, S2_tag,D;
	int S1_index, S2_index,D_index;
	ArrayList<String> rename_list2 = new ArrayList<String>(128);
	ArrayList<Integer> rename_list1 = new ArrayList<Integer>(128);
	ArrayList<Integer> execute_list = new ArrayList<Integer>(this.N+1);
	
	//Stack <String> stack1 = new Stack<String>();
	public Fetch(Stack s, int N, int S) {
		this.stack = s;
		this.N=N;
		this.S=S;
		
		
		for(int j=0;j<128;j++) {
			rename_list1.add(-1);
			rename_list2.add("R");
			
		}
	}
	
	
	
	
	public void fetch(int c) {
		
	
		int s1 = 0;
		
		
	
		while (!this.stack.isEmpty()) {
			
			
			int width = (2*N)- dispatch_count;
			
			
			  int loop_size=0;
			   if(stack.size()>=this.N) {
				 s1= this.N;
			   }
			  else if(stack.size()<this.N) {
				s1= stack.size();
			  }
				
				
			  if(width>=s1) 
				{
					loop_size=s1;

				}
			 else if(width<s1)
				{
					loop_size=width;
				}
			 else if(width == 0) {
					loop_size=0;
				}
			
			
			
			 for(int j=0;j<loop_size;j++)
					{
						String line= this.stack.pop();
						Instruction inst1 = new Instruction(line);
						ROB.put(tag,inst1);
						inst1.state= "IF";
						inst1.instrcution_count(c);
						dispatch_list.add(tag);
			
						dispatch_count++;
						tag=tag+1;
						
						
					}
			 break;
			 
				}
			
		
		
			
		}
		
		
		
	public void Dispatch(int c) {
		
		int issue_width = S - issue_count; 
		int t,loop_size = 0;

	if (!dispatch_list.isEmpty()) {
		
		if(  dispatch_list.size()>= issue_width) {
			 
			loop_size = issue_width;
			
		}
		else if(dispatch_list.size()< issue_width) {
			loop_size = dispatch_list.size();
		}
			
		for(int i =0;i<loop_size;i++)   
			{
				t = dispatch_list.get(0);
		      	Instruction line = ROB.get(t);
		      	String state = line.state;
		      	S1_index = line.R2;
				S2_index = line.R3;
				D = line.R1;
				
				if(line.state.equals("ID"))
				{
					issue_list.add(dispatch_list.get(0));
					dispatch_list.remove(0);		
					
					line.state="IS";
					issue_count++;
					dispatch_count--;
					
					line.instrcution_count(c);
					
				}
				
						
			}
			for(int j =0;j<dispatch_list.size();j++) {
				
				t = dispatch_list.get(j);
				Instruction line = ROB.get(t);
				S1_index = line.R2;
				S2_index = line.R3;
				D_index = line.R1;
				String state = line.state;
				int S1_tag = line.S1;
				int S2_tag = line.S2;
				if(line.state.equals("IF")) {
					
					line.state = "ID";
					line.instrcution_count(c);
				
					
					if(S1_index == -1) {
						 line.S1_depend = "R";
						 
					}
					else if(rename_list2.get(S1_index).equals("R"))
					{
						line.S1_depend ="R";
						
					}
					
					
					else if (rename_list2.get(S1_index).equals("NR")) {
						line.S1_depend = "NR";
				        line.S1 = rename_list1.get(S1_index);
				       
					}
					if(S2_index == -1) {
						 line.S2_depend = "R";
						 //System.out.println(" second loop dispatch S2 changed "+line.R3);
					}
					else if(rename_list2.get(S2_index).equals("R"))
					{
						line.S2_depend ="R";
						//System.out.println(" second loop dispatch S2 changed "+line.R3);
					}
					else if (rename_list2.get(S2_index).equals("NR")) {
						line.S2_depend = "NR";
						line.S2 = rename_list1.get(S2_index);
						//System.out.println(" second loop dispatch S2 changed "+line.R3);
				       // System.out.println(" second loop dispatch S2 tag changed "+line.S2);
					}
					
					/*System.out.print("inside dispath destination ");
					System.out.print(t+" ");
					System.out.print(line.state+" ");
					System.out.print(line.R2+" ");
					System.out.print(line.R3+" ");
					System.out.print(line.S1_depend+" ");
					System.out.print(line.S2_depend+" ");
					System.out.print(line.S1+" ");
					System.out.print(line.S2+" ");
					System.out.println(" ");*/
					if(D_index!= -1) {
						
						rename_list1.set(D_index, t);
						rename_list2.set(D_index, "NR");
						//System.out.println(" second loop dispatch D changed "+D);
				        
						
						
					}
					
				}
					
			}
		}
				
		
				
	}			
				
			
		
					
			
	
		
	
	
	
	@SuppressWarnings("deprecation")
	int temp;
	public void issue(int c) {
		
		//System.out.println("inside issue");
		ArrayList<Integer> temp_issue = new ArrayList<Integer>();
		int exec_loop = 0,temp1=0;
		//System.out.println("new disp"+dispatch_list);
		if( issue_list.size()!=0) {
			for(int j = 0; j< issue_list.size();j++) {
				
				 int t = issue_list.get(j);
				Instruction line = ROB.get(t);
				int S1_state, S2_state;
				S1_state = line.R2;
				S2_state = line.R3;
				String S1= line.S1_depend;
				String S2= line.S2_depend;
				String state = line.state;
				if(N+1==temp1) {
					break;
				}
				else {
					if(S1.equals("R") && (S2.equals("R") && (line.state.equals("IS")))) {
						
						temp_issue.add(t);
						temp1++;
						
						
					}
				}
				
				/*System.out.println("inside issue");
				System.out.print(t+" ");
				System.out.print(line.state+" ");
				System.out.print(S1+" ");
				System.out.print(S2+" ");
				System.out.println(" ");*/
			}
			
			for(int j1= 0;j1<temp_issue.size();j1++) {
				issue_list.remove(new Integer(temp_issue.get(j1)));
				
			}
		
		int temp_size = temp_issue.size();
		int issue_width = (N+1);
		
		if(issue_width< temp_size)
		{
			exec_loop= issue_width;
		}
		else if (issue_width>= temp_size)
		{
			 exec_loop= temp_size;
		}
		
		
		for(int i =0; i<exec_loop;i++) {
				
			int t = temp_issue.get(0);
			Instruction line = ROB.get(t);
			int S1_state, S2_state;
			S1_state = line.R2;
			S2_state = line.R3;
			String S1,S2;
			
			execute_list.add(t);
			execute_count++;
			line.state="EX";
			line.instrcution_count(c);
			temp_issue.remove(0);
			issue_count--;
				
			if(line.type == 0) {
				line.temp = 1+c;
			}
			else if(line.type == 1) {
				line.temp = 2+c;
			}
			else if (line.type == 2 ) {
				line.temp = 5+c;
			}
				
		}
				
			
		
				
				
		}
		

	
		
 }
	
			
		@SuppressWarnings("deprecation")
		public void execute(int c)
		{
			ArrayList< Integer> temp_list = new ArrayList<Integer>();
			//System.out.println("inside execute");
			for (int i =0;i<execute_list.size();i++) {
				
			//System.out.println("temp"+temp_list);
			
			int t = execute_list.get(i);
			temp_list.add(t);
			}
			for(int i1=0;i1<execute_list.size();i1++) {
				int tag = execute_list.get(i1);
				
			    Instruction line = ROB.get(tag);
			    int D_index = line.R1;
			    if( line.temp == c) {
			    	line.state = "WB";
					line.instrcution_count(c);
					
					/*System.out.println("execute ");
					System.out.print(tag+" ");
					System.out.print(line.state+" ");
					System.out.print(line.R2+" ");
					System.out.print(line.R3+" ");
					System.out.print(line.S1_depend+" ");
					System.out.print(line.S2_depend+" ");
					System.out.print(line.S1+" ");
					System.out.print(line.S2+" ");
					System.out.println(" ");*/
					temp_list.remove(new Integer(execute_list.get(i1)));
					if((D_index!=-1) && (tag == rename_list1.get(D_index)))
					{
						rename_list2.set(D_index, "R");
						//System.out.println(" execute loop D changed "+D_index);
				        
						
					}
					for(int i2=0;i2<issue_list.size();i2++) {
						
						int tag2=issue_list.get(i2);
						Instruction issue_int = ROB.get(tag2);
						
					
						if(issue_int.S1 == tag) {
							issue_int.S1_depend = "R";
							//System.out.println(" execute loop S1 changed "+issue_int.S1_depend);
					       
						}
						if(issue_int.S2 == tag) {
							issue_int.S2_depend = "R";
							//System.out.println(" execute loop S2 changed "+issue_int.S2_depend);
						}
					}
					for(int i3=0;i3<dispatch_list.size();i3++) {
						
						int tag3=dispatch_list.get(i3);
						Instruction disp_int = ROB.get(tag3);
						/*System.out.println("execute - dispatch ");
						System.out.print(tag3+" ");
						System.out.print(line.state+" ");
						System.out.print(line.R2+" ");
						System.out.print(line.R3+" ");
						System.out.print(line.S1_depend+" ");
						System.out.print(line.S2_depend+" ");
						System.out.print(line.S1+" ");
						System.out.print(line.S2+" ");
						System.out.println(" ");*/
						if(disp_int.state.equals("ID")) {
							if(disp_int.S1 == tag) {
								disp_int.S1_depend = "R";
								//System.out.println(" execute loop S1 changed "+disp_int.S1_depend);
							}
							if(disp_int.S2 == tag) {
								disp_int.S2_depend = "R";
								//System.out.println(" execute loop S1 changed "+disp_int.S2_depend);
							}
						}
						
					}
				
			    }
			
			
			
		
		
			//System.out.println("final execute list "f.execute_list);
			
		//	System.out.println("execute list"+execute_list);
			
			
		}
			
			execute_list.clear();
			for( int x =0;x<temp_list.size();x++) {
				execute_list.add(temp_list.get(x));
			}
		}
		
		public  boolean advance_cycle() {
			
			if(stack.isEmpty() && dispatch_list.isEmpty() && issue_list.isEmpty() && execute_list.isEmpty()) {
				
				return false;	
			}
			else {
				return true;
			}
			
			
			
		}
		
	
	
}
	


	
	



