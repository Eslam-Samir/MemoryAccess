package allocator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public abstract class MemoryAllocator {
	protected int NumberOfProcesses;
	protected ArrayList<Process> InputProcesses;
	protected ArrayList<Process> InitialHoles;
	protected ArrayList<ArrayList<Process>> output;
	protected ArrayList<String> stages;
	
	protected MemoryAllocator()
	{
		output = new ArrayList<>();
		stages = new ArrayList<>();
	}
	
	protected void FindSuitableHole(Process process, ArrayList<Process> currentState)
	{
		ArrayList<Process> nextState = new ArrayList<>();
		boolean success = false;
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
				success = true;
				break;
			}
		}
		output.add(nextState);
		if(success)
			stages.add("After " + process.getName() + " Allocation");
		else
			stages.add("Couldn't Allocate " + process.getName());
	}
	
	public abstract void RunAllocator();	
	
	public ArrayList<String> getStages()
	{
		return stages;
	}
	
	public ArrayList<ArrayList<Process>> getOutput()
	{
		if(output.isEmpty())
			return null;
		for(int i = 0, count = output.size(); i < count; i++)
		{
			Collections.sort(output.get(i), new Comparator<Process>() {
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
			
			System.out.println(stages.get(i) + ": \n");
			System.out.println("Name\tBase Address\tSize");
			Process p;
			for(int j = 0, size = output.get(i).size(); j < size; j++)
			{
				p = output.get(i).get(j);
				System.out.println(p.getName()+"\t"+p.getBaseAddress()+"\t\t"+p.getSize());
			}
			System.out.println();
		}
		return output;
	}
	
	// return false if not found
	public boolean DeallocateProcess(String process)
	{
		if(output.isEmpty())
			return false;
		
		ArrayList<Process> lastState = new ArrayList<>();
		for(int j = 0, size = output.get(output.size()-1).size(); j < size; j++)
		{
			lastState.add(new Process(output.get(output.size()-1).get(j)));
		}
		Collections.sort(lastState, new Comparator<Process>() {
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

		Process p;
		for(int i = 0, size = lastState.size(); i < size; i++)
		{
			p = lastState.get(i);
			if(p.getName().equals(process) && p.getType() != ProcessType.hole)
			{
				Process hole = new Process("Empty", p.getSize(), p.getBaseAddress(), ProcessType.hole);
				lastState.remove(i);
				lastState.add(i, hole);
				if(i == 0 && size > 1 && lastState.get(i+1).getType() == ProcessType.hole
						&& lastState.get(i+1).getBaseAddress() == hole.getBaseAddress() + hole.getSize())
				{
					hole.setSize(hole.getSize() + lastState.get(i+1).getSize());
					lastState.remove(i+1);
				}
				else if(i == size-1 && size > 1 && lastState.get(i-1).getType() == ProcessType.hole
						&& lastState.get(i-1).getBaseAddress() + lastState.get(i-1).getSize() == hole.getBaseAddress())
				{
					hole.setBaseAddress(lastState.get(i-1).getBaseAddress());
					hole.setSize(hole.getSize() + lastState.get(i-1).getSize());
					lastState.remove(i-1);
				}
				else if(size > 1 && i > 0 && i < size-1)
				{
					if(lastState.get(i-1).getType() == ProcessType.hole
						&& lastState.get(i-1).getBaseAddress() + lastState.get(i-1).getSize() == hole.getBaseAddress()
						&& lastState.get(i+1).getType() == ProcessType.hole
						&& lastState.get(i+1).getBaseAddress() == hole.getBaseAddress() + hole.getSize())
					{
						hole.setBaseAddress(lastState.get(i-1).getBaseAddress());
						hole.setSize(hole.getSize() + lastState.get(i-1).getSize() + lastState.get(i+1).getSize());
						lastState.remove(i+1);
						lastState.remove(i-1);
					}
					else if(lastState.get(i+1).getType() == ProcessType.hole
							&& lastState.get(i+1).getBaseAddress() == hole.getBaseAddress() + hole.getSize())
					{
						hole.setSize(hole.getSize() + lastState.get(i+1).getSize());
						lastState.remove(i+1);
					}
					else if(lastState.get(i-1).getType() == ProcessType.hole
							&& lastState.get(i-1).getBaseAddress() + lastState.get(i-1).getSize() == hole.getBaseAddress())
					{
						hole.setBaseAddress(lastState.get(i-1).getBaseAddress());
						hole.setSize(hole.getSize() + lastState.get(i-1).getSize());
						lastState.remove(i-1);	
					}
				}
				output.add(lastState);
				stages.add("After " + process + " Deallocation");
				return true;
			}
		}
		return false;
	}
	
}
