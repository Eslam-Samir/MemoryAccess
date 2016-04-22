package allocator;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Process {
	private SimpleStringProperty name;
	private SimpleLongProperty size;
	private SimpleLongProperty baseAddress;
	private ProcessType type;
	
	public Process(String name, long size, long baseAddress, ProcessType type)
	{
		this.name = new SimpleStringProperty(name);
		this.size = new SimpleLongProperty(size);
		this.baseAddress = new SimpleLongProperty(baseAddress);
		this.type = type;
	}
	
	public Process(Process process) {
		this.name = new SimpleStringProperty(process.getName());
		this.size = new SimpleLongProperty(process.getSize());
		this.baseAddress = new SimpleLongProperty(process.getBaseAddress());
		this.type = process.getType();
	}
	
	public String getName() {
		return name.get();
	}
	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}
	public long getSize() {
		return size.get();
	}
	public void setSize(long size) {
		this.size = new SimpleLongProperty(size);
	}
	public long getBaseAddress() {
		return baseAddress.get();
	}
	public void setBaseAddress(long baseAddress) {
		this.baseAddress = new SimpleLongProperty(baseAddress);
	}

	public ProcessType getType() {
		return type;
	}

	public void setType(ProcessType type) {
		this.type = type;
	}
	
}
