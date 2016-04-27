package application;

import java.util.ArrayList;

import allocator.Process;
import allocator.ProcessType;

public class TablesController {
	private ArrayList<Process> holes=new ArrayList<Process>();
	private ArrayList<Process>processes=new ArrayList<Process>();
	private String allocationType;
	
	public String getAllocationType() {
		return allocationType;
	}
	public void setAllocationType(String allocationType) {
		this.allocationType = allocationType;
	}
	
	public void AddHole (int size,int Address) {
		holes.add(new Process("", size,Address,ProcessType.hole));
		
	}
	public void AddProcess (String name,int size) {
		processes.add(new Process(name, size,0,ProcessType.process));
		
	}


}
