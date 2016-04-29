package application;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import allocator.BestFitAllocator;
import allocator.FirstFitAllocator;
import allocator.MemoryAllocator;
import allocator.Process;
import allocator.ProcessType;
import allocator.WorstFitAllocator;

public class TablesController implements Initializable{
	@FXML
	Label StageNumber;
	
	@FXML
	TableView<Process>table;
	
	@FXML
	TableColumn<Process, String> nameColumn;
	
	@FXML 
	TableColumn<Process, Integer> sizeColumn;
	
	@FXML
	TableColumn<Process, Integer>baseAddressColumn; 
	private MemoryAllocator m1;
	private ArrayList<Process> holes=new ArrayList<Process>();
	private ArrayList<Process>processes=new ArrayList<Process>();
	private String allocationType;
	private int numberOfProcesses;
	private ArrayList<ArrayList<Process>> output;
	private ArrayList<Process> arrayListOfThisStage;
	private int index;

	
public ObservableList<Process> list=FXCollections.observableArrayList();
	

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
		InitializeAllocationType();
		nameColumn.setCellValueFactory(new PropertyValueFactory<Process, String>("name"));
		sizeColumn.setCellValueFactory(new PropertyValueFactory<Process, Integer>("size"));
		baseAddressColumn.setCellValueFactory(new PropertyValueFactory<Process, Integer>("baseAdress"));	
	}
	private void InitializeAllocationType() {
		if (allocationType=="First Fit")
			{
				m1=new FirstFitAllocator(numberOfProcesses, processes, holes);
			}
		else if(allocationType=="Best Fit")
			{
				m1=new BestFitAllocator(numberOfProcesses, processes, holes);
			}
		else if(allocationType=="Worst Fit")
			{
				m1=new WorstFitAllocator(numberOfProcesses, processes, holes);
			}
		m1.RunAllocator();
		output=m1.getOutput();
		index=0;
		arrayListOfThisStage=output.get(index);
		
	}
	public void setList() {
		for(int i=0;i<arrayListOfThisStage.size();i++){
			list.add(arrayListOfThisStage.get(i));
		}
		index++;
		
	}


}
