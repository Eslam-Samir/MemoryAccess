package application;

import java.io.Console;
import java.lang.invoke.ConstantCallSite;
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
	private boolean flag=false;

	
	private ObservableList<Process> list=FXCollections.observableArrayList();
	

	public String getAllocationType() {
		return allocationType;
	}
	
	public void setAllocationType(String allocationType) {
		this.allocationType = allocationType;
		System.out.println(allocationType);
		InitializeAllocationType();
		setList();
			
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
	public void pressNext(ActionEvent event) {
		flag =false;
		StageNumber.setText(m1.getStages().get(index));
		setList();	
		table.setItems(list);
		nameColumn.setCellValueFactory(new PropertyValueFactory<Process, String>("name"));
		sizeColumn.setCellValueFactory(new PropertyValueFactory<Process, Long>("size"));
		baseAddressColumn.setCellValueFactory(new PropertyValueFactory<Process, Long>("baseAddress"));	
	}
	public void pressPrevious(ActionEvent event) {	
		
		if (flag)
			index--;
		else
			index=index-2;
		StageNumber.setText(m1.getStages().get(index));
		setList();
		table.setItems(list);
		nameColumn.setCellValueFactory(new PropertyValueFactory<Process, String>("name"));
		sizeColumn.setCellValueFactory(new PropertyValueFactory<Process, Long>("size"));
		baseAddressColumn.setCellValueFactory(new PropertyValueFactory<Process, Long>("baseAddress"));	
		flag=true;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nameColumn.setCellValueFactory(new PropertyValueFactory<Process, String>("name"));
		sizeColumn.setCellValueFactory(new PropertyValueFactory<Process, Long>("size"));
		baseAddressColumn.setCellValueFactory(new PropertyValueFactory<Process, Long>("baseAddress"));	
	}
	
	private void InitializeAllocationType() {
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
	
	public void setList() {
		list.clear();
		for(int i=0;i<output.get(index).size();i++){
			list.add(output.get(index).get(i));	
		}
		table.setItems(list);
		if(index < output.size()-1)
			index++;
	}


}
