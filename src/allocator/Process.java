package allocator;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Process {
	private SimpleStringProperty name;
	private SimpleLongProperty size;
	private SimpleLongProperty baseAddress;
	
	
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
	
	
	
}
