

import java.util.ArrayList;

public class Instruction {
	
	String S1_depend = null, S2_depend= null;
	String instruction,state; 
	String [] array = null;
	int type, R1,R2,R3,temp,S1,S2;

	ArrayList<Integer> inst_cycle = new ArrayList<Integer>();
	public Instruction(String t) {
		String type, r1,r2,r3;
		this.state= state;
		this.instruction=t;
		array= t.split(" ");
		type=array[1];
		r1= array[2];
		r2= array[3];
		r3= array[4];
		
		S1=-1;
		S2=-1;
		S1_depend="R";
		S2_depend="R";
		this.type= Integer.parseInt(type);
		this.R1 = Integer.parseInt(r1);
		this.R2 = Integer.parseInt(r2);
		this.R3 = Integer.parseInt(r3);
		
		//System.out.println("wassup");
		
		
		
	}
	public  void instrcution_count(int count) {
		inst_cycle.add(count);
		
	}
	
	

}

