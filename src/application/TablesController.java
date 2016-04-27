package application;

import java.util.ArrayList;

import allocator.Process;

public class TablesController {
	private ArrayList<Process> holes;
	private ArrayList<Process>processes;
	private String allocationType;
	public String getAllocationType() {
		return allocationType;
	}
	public void setAllocationType(String allocationType) {
		this.allocationType = allocationType;
	}
	
	public ArrayList<Process> getHoles() {
		return holes;
	}
	public void setHoles(ArrayList<Process> holes) {
		this.holes = holes;
	}
	public ArrayList<Process> getProcesses() {
		return processes;
	}
	public void setProcesses(ArrayList<Process> processes) {
		this.processes = processes;
	}
	

}
