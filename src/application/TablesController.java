package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
	private Label StageNumber;
	
	@FXML
	private TableView<Process> table;
	
	@FXML
	private TableColumn<Process, String> nameColumn;
	
	@FXML 
	private TableColumn<Process, Long> sizeColumn;
	
	@FXML
	private TableColumn<Process, Long>baseAddressColumn; 
	
	private MemoryAllocator m1;
	private ArrayList<Process> holes=new ArrayList<Process>();
	private ArrayList<Process>processes=new ArrayList<Process>();
	private String allocationType;
	private int numberOfProcesses;
	private ArrayList<ArrayList<Process>> output;
	private int index;

	
	private ObservableList<Process> list=FXCollections.observableArrayList();
	

	public String getAllocationType() {
		return allocationType;
	}
	
	public void setAllocationType(String allocationType) {
		this.allocationType = allocationType;
		System.out.println(allocationType);
		InitializeAllocator();
		setTable();
			
	}
	
	public int getNumberOfProcesses() {
		return numberOfProcesses;
	}
	
	public void setNumberOfProcesses(int numberOfProcesses) {
		this.numberOfProcesses = numberOfProcesses;
		System.out.println(numberOfProcesses);
	}
	
	public void AddHole (long size,long Address) 
	{
		holes.add(new Process("", size,Address,ProcessType.hole));
		System.out.println("Added Hole: "+String.valueOf(size)+ "\t" + String.valueOf(Address));
	}
	public void AddProcess (String name,long size) 
	{
		processes.add(new Process(name, size,0,ProcessType.process));
		System.out.println("Added Process: "+name+ "\t" + String.valueOf(size));
	}
	public void pressNext(ActionEvent event) 
	{
		if(index < output.size()-1)
			index++;
		StageNumber.setText(m1.getStages().get(index));
		setTable();	
		nameColumn.setCellValueFactory(new PropertyValueFactory<Process, String>("name"));
		sizeColumn.setCellValueFactory(new PropertyValueFactory<Process, Long>("size"));
		baseAddressColumn.setCellValueFactory(new PropertyValueFactory<Process, Long>("baseAddress"));	
	}
	public void pressPrevious(ActionEvent event) 
	{	
		if(index > 0)
			index--;
		StageNumber.setText(m1.getStages().get(index));
		setTable();
		nameColumn.setCellValueFactory(new PropertyValueFactory<Process, String>("name"));
		sizeColumn.setCellValueFactory(new PropertyValueFactory<Process, Long>("size"));
		baseAddressColumn.setCellValueFactory(new PropertyValueFactory<Process, Long>("baseAddress"));
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		nameColumn.setCellValueFactory(new PropertyValueFactory<Process, String>("name"));
		sizeColumn.setCellValueFactory(new PropertyValueFactory<Process, Long>("size"));
		baseAddressColumn.setCellValueFactory(new PropertyValueFactory<Process, Long>("baseAddress"));	
	}
	
	private void InitializeAllocator() 
	{
		if (allocationType==Contstant.FF)
		{
			m1=new FirstFitAllocator(numberOfProcesses, processes, holes);
		}
		else if(allocationType==Contstant.BF)
		{
			m1=new BestFitAllocator(numberOfProcesses, processes, holes);
		}
		else if(allocationType==Contstant.WF)
		{
			m1=new WorstFitAllocator(numberOfProcesses, processes, holes);
		}
		m1.RunAllocator();
		output=m1.getOutput();
		index=0;
	}
	
	public void setTable() 
	{
		list.clear();
		for(int i=0;i<output.get(index).size();i++)
		{
			list.add(output.get(index).get(i));	
		}
		table.setItems(list);
	}


}
