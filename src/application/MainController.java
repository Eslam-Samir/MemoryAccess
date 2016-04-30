package application;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

import extras.Common;
import extras.Contstant;
import allocator.Process;
import allocator.ProcessType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class MainController implements Initializable {
	@FXML
	private TextField size;
	
	@FXML
	private TextField startingAddress;
	
	@FXML
	private TextField ProcessName;
	
	@FXML
	private TextField ProcessSize;
	
	@FXML
	private TextField NumberOfProcesses;
	
	@FXML
	public ComboBox<String> combobox;
	
	@FXML
	private TableView<Process> holesTable;
	
	@FXML
	private TableView<Process> processesTable;
	
	@FXML 
	private TableColumn<Process, Long> HoleAddressColumn;
	
	@FXML
	private TableColumn<Process, Long>HoleSizeColumn;
	
	@FXML
	private TableColumn<Process, String> ProcessNameColumn;
	
	@FXML
	private TableColumn<Process, Long> ProcessSizeColumn;
	
	private ObservableList<String> list = FXCollections.observableArrayList(
			Contstant.FF,
			Contstant.BF,
			Contstant.WF
            );
	
	private ObservableList<Process> processes = FXCollections.observableArrayList();
	private ObservableList<Process> holes = FXCollections.observableArrayList();
	
	public void pressAddHole(ActionEvent event) 
	{
		if(size.getText().isEmpty() || startingAddress.getText().isEmpty())
		{
			Common.createAlert("Please Enter Hole Size and Base Address");
			return;
		}
		long holesize = Long.valueOf(size.getText());
		long holeaddress = Long.valueOf(startingAddress.getText());
		
		holes.add(new Process("", holesize, holeaddress, ProcessType.hole));
		
		Collections.sort(holes, new Comparator<Process>() {
			@Override
			public int compare(Process p1, Process p2) {
				if(p1.getBaseAddress() < p2.getBaseAddress())
					return -1;
				else if(p1.getBaseAddress() > p2.getBaseAddress())
					return 1;
				else
					return 0;
			}
		});
		
		holesTable.setItems(holes);
		
		size.clear();
		startingAddress.clear();
	}
	public void pressAddProcess(ActionEvent event) 
	{
		if(ProcessSize.getText().isEmpty() || ProcessName.getText().isEmpty())
		{
			Common.createAlert("Please Enter Process Size and Name");
			return;
		}
		long processsize = Long.valueOf(ProcessSize.getText());
		String processname = ProcessName.getText();
		
		processes.add(new Process(processname, processsize, 0, ProcessType.process));
		processesTable.setItems(processes);
		
		ProcessSize.clear();
		ProcessName.clear();
	}
	
	public void pressNext(ActionEvent event) throws IOException 
	{
		if(processes.isEmpty())
		{
			Common.createAlert("Please Enter at Least One Process");
		}
		else if(holes.isEmpty())
		{
			Common.createAlert("Please Enter at Least One Hole");
		}
		else if(combobox.getSelectionModel().getSelectedIndex() < 0)
		{
			Common.createAlert("Select Memory Allocation Type");
		}
		else if(NumberOfProcesses.getText().isEmpty())
		{
			Common.createAlert("Please Enter Number of Processes");
		}
		else if(Integer.valueOf(NumberOfProcesses.getText()) != processes.size())
		{
			Common.createAlert("You Entered Wrong Number of Processes"
					, "Processes count and the entered number of processes didn't match");
		}
		else
		{
			if(checkAllocation())
				goToNextStage();
		}
	}
	
	private boolean checkAllocation()
	{
		for(int i = 0, size = holes.size(); i < size-1; i++) // check if holes are overlapping
		{
			if(holes.get(i).getBaseAddress() + holes.get(i).getSize() >= holes.get(i+1).getBaseAddress())
			{
				Common.createAlert("There are overlapping holes"
						, "If two holes are overlapping please combine them into one hole or separate them");
				return false;
			}
		}
		
		int sumSizes = 0, sumHoles = 0;
		for(int i = 0, size = holes.size(); i < size; i++)
		{
			sumHoles += holes.get(i).getSize();
		}
		for(int i = 0, size = processes.size(); i < size; i++)
		{
			sumSizes += processes.get(i).getSize();
		}
		if(sumSizes > sumHoles)
		{
			Common.createAlert("The entered processes require a bigger size than the holes size");
			return false;
		}
		return true;
	}
	
	private void goToNextStage() throws IOException
	{
		Stage stage = (Stage) combobox.getScene().getWindow();
		
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Tables.fxml"));
        Parent root = loader.load();
        TablesController tablecontroller = loader.getController();
		String type = combobox.getValue();
		
		for(int i = 0; i < processes.size(); i++)
		{
			tablecontroller.AddProcess(processes.get(i).getName(), processes.get(i).getSize());
		}
		for(int i = 0; i < holes.size(); i++)
		{
			tablecontroller.AddHole(holes.get(i).getSize(), holes.get(i).getBaseAddress());
		}
		
		tablecontroller.setNumberOfProcesses(Integer.valueOf(NumberOfProcesses.getText()));
		tablecontroller.setAllocationType(type);
		
        Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void deleteProcess(KeyEvent event)
	{
		if (event.getCode().equals( KeyCode.DELETE ) )
	    {
			int index = processesTable.getSelectionModel().getSelectedIndex();
			processes.remove(index);
			processesTable.setItems(processes);
	    }
	}
	
	public void deleteHole(KeyEvent event)
	{
		if (event.getCode().equals( KeyCode.DELETE ) )
	    {
			int index = holesTable.getSelectionModel().getSelectedIndex();
			holes.remove(index);
			holesTable.setItems(holes);
	    }
	}
	
	@Override
	public void initialize(URL location, ResourceBundle application) 
	{
		combobox.setItems(list);
		HoleAddressColumn.setCellValueFactory(new PropertyValueFactory<Process, Long>("baseAddress"));
		HoleSizeColumn.setCellValueFactory(new PropertyValueFactory<Process, Long>("size"));
		
		ProcessNameColumn.setCellValueFactory(new PropertyValueFactory<Process, String>("name"));
		ProcessSizeColumn.setCellValueFactory(new PropertyValueFactory<Process, Long>("size"));
	}
	
}
