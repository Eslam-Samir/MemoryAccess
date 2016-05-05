package allocator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class WorstFitAllocator extends MemoryAllocator {
	public WorstFitAllocator(int processesCount, ArrayList<Process> inputs, ArrayList<Process> holes) 
	{
		NumberOfProcesses = processesCount;
		InputProcesses = inputs;
		InitialHoles = holes;
	}
	public void RunAllocator()
	{
		ArrayList<Process> initialState = new ArrayList<>();
		for(int i = 0; i < InitialHoles.size(); i++)
		{
			initialState.add(new Process(InitialHoles.get(i)));
		}
		
		output.add(initialState); // initial state
		stages.add("Initial State");
		for(int i = 0; i < NumberOfProcesses; i++)
		{
			Process process = InputProcesses.get(i);
			ArrayList<Process> currentState = output.get(i);
			int lastIndex = output.size()-1;
			Collections.sort(output.get(lastIndex), new Comparator<Process>() {
				@Override
				public int compare(Process p1, Process p2) {
					if(p1.getSize() > p2.getSize())
					{
						return -1;
					}
					else if(p1.getBaseAddress() < p2.getBaseAddress())
					{
						return 1;
					}
					else
					{
						return 0;
					}
				}
			});
			FindSuitableHole(process, currentState);
		}
	}
}
