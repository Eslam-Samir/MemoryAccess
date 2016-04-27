package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


import allocator.ProcessType;
import allocator.Process;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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
	
	private ArrayList<Integer> ProcessesSizes = new ArrayList<Integer>();
	private ArrayList<String> ProcessesNames = new ArrayList<String>();
	
	private ArrayList<Integer> HolesSizes = new ArrayList<Integer>();
	private ArrayList<Integer> HolesAddresses = new ArrayList<Integer>();
	
	private ObservableList<String> list = FXCollections.observableArrayList(
			Contstant.FF,
			Contstant.BF,
			Contstant.WF
            );
	
	public void pressAddHole(ActionEvent event) 
	{
		HolesSizes.add(Integer.valueOf(size.getText()));
		HolesAddresses.add(Integer.valueOf(startingAddress.getText()));
		size.clear();
		startingAddress.clear();
	}
	public void pressAddProcess(ActionEvent event) 
	{
		ProcessesSizes.add(Integer.valueOf(ProcessSize.getText()));
		ProcessesNames.add(ProcessName.getText());
		ProcessSize.clear();
		ProcessName.clear();
	}
	
	public void pressNext(ActionEvent event) throws IOException 
	{
		Stage stage = (Stage) combobox.getScene().getWindow();
		
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Tables.fxml"));
        
        TablesController tablecontroller = new TablesController();
		String type = combobox.getValue();
		
		for(int i = 0; i < ProcessesSizes.size(); i++)
		{
			tablecontroller.AddProcess(ProcessesNames.get(i), ProcessesSizes.get(i));
		}
		for(int i = 0; i < HolesSizes.size(); i++)
		{
			tablecontroller.AddHole(HolesSizes.get(i), HolesAddresses.get(i));
		}
		
		tablecontroller.setAllocationType(type);
		
		loader.setController(tablecontroller);
		Parent root = loader.load();
        Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle application) 
	{
		combobox.setItems(list);
	}
	
}
