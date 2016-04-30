package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
	
	
	private ArrayList<Long> ProcessesSizes = new ArrayList<Long>();
	private ArrayList<String> ProcessesNames = new ArrayList<String>();
	
	private ArrayList<Long> HolesSizes = new ArrayList<Long>();
	private ArrayList<Long> HolesAddresses = new ArrayList<Long>();
	
	private ObservableList<String> list = FXCollections.observableArrayList(
			Contstant.FF,
			Contstant.BF,
			Contstant.WF
            );
	
	private ObservableList<Process> processes = FXCollections.observableArrayList();
	private ObservableList<Process> holes = FXCollections.observableArrayList();
	
	public void pressAddHole(ActionEvent event) 
	{
		long holesize = Long.valueOf(size.getText());
		long holeaddress = Long.valueOf(startingAddress.getText());
		
		HolesSizes.add(holesize);
		HolesAddresses.add(holeaddress);
		holes.add(new Process("", holesize, holeaddress, ProcessType.hole));
		holesTable.setItems(holes);
		
		size.clear();
		startingAddress.clear();
	}
	public void pressAddProcess(ActionEvent event) 
	{
		long processsize = Long.valueOf(ProcessSize.getText());
		String processname = ProcessName.getText();
		
		ProcessesSizes.add(processsize);
		ProcessesNames.add(processname);
		processes.add(new Process(processname, processsize, 0, ProcessType.process));
		processesTable.setItems(processes);
		
		ProcessSize.clear();
		ProcessName.clear();
	}
	
	public void pressNext(ActionEvent event) throws IOException 
	{
		Stage stage = (Stage) combobox.getScene().getWindow();
		
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Tables.fxml"));
        Parent root = loader.load();
        TablesController tablecontroller = loader.getController();
		String type = combobox.getValue();
		
		for(int i = 0; i < ProcessesSizes.size(); i++)
		{
			tablecontroller.AddProcess(ProcessesNames.get(i), ProcessesSizes.get(i));
		}
		for(int i = 0; i < HolesSizes.size(); i++)
		{
			tablecontroller.AddHole(HolesSizes.get(i), HolesAddresses.get(i));
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
			ProcessesNames.remove(index);
			ProcessesSizes.remove(index);
	    }
	}
	
	public void deleteHole(KeyEvent event)
	{
		if (event.getCode().equals( KeyCode.DELETE ) )
	    {
			int index = holesTable.getSelectionModel().getSelectedIndex();
			holes.remove(index);
			holesTable.setItems(holes);
			HolesAddresses.remove(index);
			HolesSizes.remove(index);
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
