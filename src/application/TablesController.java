package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import allocator.Process;
import allocator.ProcessType;

public class TablesController implements Initializable{
	private ArrayList<Process> holes=new ArrayList<Process>();
	private ArrayList<Process>processes=new ArrayList<Process>();
	private String allocationType;
	private int numberOfProcesses;
	
	public String getAllocationType() {
		return allocationType;
	}
	
	public void setAllocationType(String allocationType) {
		this.allocationType = allocationType;
		System.out.println(allocationType);
	}
	
	public int getNumberOfProcesses() {
		return numberOfProcesses;
	}
	
	public void setNumberOfProcesses(int numberOfProcesses) {
		this.numberOfProcesses = numberOfProcesses;
		System.out.println(numberOfProcesses);
	}
	
	public void AddHole (int size,int Address) {
		holes.add(new Process("", size,Address,ProcessType.hole));
		System.out.println("Added Hole: "+String.valueOf(size)+ "\t" + String.valueOf(Address));
	}
	public void AddProcess (String name,int size) {
		processes.add(new Process(name, size,0,ProcessType.process));
		System.out.println("Added Process: "+name+ "\t" + String.valueOf(size));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}


}
