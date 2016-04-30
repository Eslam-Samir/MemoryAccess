package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import extras.Common;
import extras.Contstant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
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
	private Button previous;
	@FXML
	private Button next;
	
	@FXML
	private TableColumn<Process, String> nameColumn;
	
	@FXML 
	private TableColumn<Process, Long> sizeColumn;
	
	@FXML
	private TableColumn<Process, Long>baseAddressColumn; 
	
	@FXML
	private GridPane deallocationGrid;
	
	@FXML
	private ComboBox<String> processSelection;
	
	private ObservableList<String> names = FXCollections.observableArrayList();
	
	private MemoryAllocator m1;
	private ArrayList<Process> holes=new ArrayList<Process>();
	private ArrayList<Process>processes=new ArrayList<Process>();
	private String allocationType;
	private int numberOfProcesses;
	private ArrayList<ArrayList<Process>> output;
	private int index;

	
	private ObservableList<Process> list=FXCollections.observableArrayList();
	

	public String getAllocationType() 
	{
		return allocationType;
	}
	
	public void setAllocationType(String allocationType) 
	{
		this.allocationType = allocationType;
		System.out.println(allocationType);
		
		processSelection.setItems(names);
		InitializeAllocator();
		setStageTable();
		previous.setDisable(true);	
	}
	
	public int getNumberOfProcesses() {
		return numberOfProcesses;
	}
	
	public void setNumberOfProcesses(int numberOfProcesses) 
	{
		this.numberOfProcesses = numberOfProcesses;
	}
	
	public void AddHole(long size,long Address) 
	{
		holes.add(new Process("Empty", size,Address,ProcessType.hole));
		System.out.println("Added Hole: "+String.valueOf(size)+ "\t" + String.valueOf(Address));
	}
	public void AddProcess (String name,long size) 
	{
		names.add(name);
		processes.add(new Process(name, size,0,ProcessType.process));
		System.out.println("Added Process: "+name+ "\t" + String.valueOf(size));
	}
	public void pressNext(ActionEvent event) 
	{
		if(index < output.size()-1)
			index++;
		if(index > 0)
			previous.setDisable(false);
		
		setStageTable();
		if(index == output.size()-1)
		{
			next.setDisable(true);
			deallocationGrid.setVisible(true);
		}
	}
	public void pressPrevious(ActionEvent event) 
	{	
		if(index > 0)
			index--;
		if(index < output.size()-1)
			next.setDisable(false);
		
		deallocationGrid.setVisible(false);
		setStageTable();
		
		if(index == 0)
			previous.setDisable(true);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		nameColumn.setCellValueFactory(new PropertyValueFactory<Process, String>("name"));
		sizeColumn.setCellValueFactory(new PropertyValueFactory<Process, Long>("size"));
		baseAddressColumn.setCellValueFactory(new PropertyValueFactory<Process, Long>("baseAddress"));
		
		deallocationGrid.setVisible(false);
	}
	
	private void InitializeAllocator() 
	{
		if (allocationType.equals(Contstant.FF))
		{
			m1=new FirstFitAllocator(numberOfProcesses, processes, holes);
		}
		else if(allocationType.equals(Contstant.BF))
		{
			m1=new BestFitAllocator(numberOfProcesses, processes, holes);
		}
		else if(allocationType.equals(Contstant.WF))
		{
			m1=new WorstFitAllocator(numberOfProcesses, processes, holes);
		}
		m1.RunAllocator();
		output=m1.getOutput();
		index=0;
	}
	
	public void setStageTable() 
	{
		list.clear();
		for(int i=0;i<output.get(index).size();i++)
		{
			list.add(output.get(index).get(i));	
		}
		StageNumber.setText(m1.getStages().get(index));
		table.setItems(list);
	}
	
	public void Deallocate(ActionEvent action)
	{
		if(processSelection.getSelectionModel().getSelectedIndex() < 0)
		{
			Common.createAlert("Select a Process to Deallocate", "Use the drop down list to select a process");
		}
		else
		{
			String deallocatedProcess = processSelection.getValue();
			if(names.remove(deallocatedProcess))
			{
				if(m1.DeallocateProcess(deallocatedProcess))
				{
					index++;
					setStageTable();
				}
				else
				{
					Common.createAlert("Process "+deallocatedProcess+" can't be deallocated",
							"The memory allocator couldn't allocate "+ deallocatedProcess+
							", So it is not in the memory");
				}
			}
		}
	}
	
	public void Back(ActionEvent action) throws IOException
	{
		Stage stage=(Stage) table.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();		
	}
}
