package allocator;

import java.util.ArrayList;
import java.util.LinkedList;

public class MemoryAllocator {
	private int NumberOfProcesses;
	private ArrayList<Process> InputProcesses;
	private ArrayList<Process> InitialHoles;
	MemoryAllocator(int processesCount, ArrayList<Process> inputs, ArrayList<Process> holes)
	{
		NumberOfProcesses = processesCount;
		InputProcesses = inputs;
		InitialHoles = holes;
	}
	
	ArrayList<LinkedList<Process>> firstFit()
	{
		ArrayList<LinkedList<Process>> output = new ArrayList<>();
		
		
		
		return output;
	}
	
	
}
