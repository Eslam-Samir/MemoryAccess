package allocator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class MemoryAllocator {
	private int NumberOfProcesses;
	private LinkedList<Process> InputProcesses;
	private LinkedList<Process> InitialHoles;
	public MemoryAllocator(int processesCount, LinkedList<Process> inputs, LinkedList<Process> holes)
	{
		NumberOfProcesses = processesCount;
		InputProcesses = inputs;
		InitialHoles = holes;
	}
	
	public ArrayList<LinkedList<Process>> firstFit()
	{
		ArrayList<LinkedList<Process>> output = new ArrayList<>();
		LinkedList<Process> initialState = new LinkedList<>();
		for(int i = 0; i < InitialHoles.size(); i++)
		{
			initialState.add(new Process(InitialHoles.get(i)));
		}
		Collections.sort(initialState, new Comparator<Process>() {
			@Override
			public int compare(Process p1, Process p2) {
				if(p1.getBaseAddress() < p2.getBaseAddress())
				{
					return -1;
				}
				else if(p1.getBaseAddress() > p2.getBaseAddress())
				{
					return 1;
				}
				else
				{
					return 0;
				}
			}
		});
		
		output.add(initialState); // initial state
		
		for(int i = 0; i < NumberOfProcesses; i++)
		{
			Process process = InputProcesses.get(i);
			LinkedList<Process> currentState = output.get(i);
			LinkedList<Process> nextState = new LinkedList<>();
			for(int j = 0; j < currentState.size(); j++)
			{
				nextState.add(new Process(currentState.get(j)));
			}
			for(int j = 0; j < currentState.size(); j++)
			{
				Process current = nextState.get(j);
				if(current.getType() == ProcessType.hole && current.getSize() >= process.getSize())
				{
					nextState.add(j, process);
					process.setBaseAddress(current.getBaseAddress());
					long newSize = current.getSize() - process.getSize();
					if(newSize == 0)
					{
						nextState.remove(current);
					}
					else
					{
						long newAddress = current.getBaseAddress() + process.getSize();
						current.setBaseAddress(newAddress);
						current.setSize(newSize);
					}
					break;
				}
			}
			output.add(nextState);
		}
		
		
		return output;
	}
	
	
}
