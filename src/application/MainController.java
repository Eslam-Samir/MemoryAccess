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
	private ComboBox<String> type;
	
	@FXML
	private TextField ProcessName;
	
	@FXML
	private TextField ProcessSize;
	
	@FXML
	private TextField NumberOfProcesses;
	
	@FXML
	public ComboBox<String> combobox;
	
	private int holeSize;
	private int holeAdress;
	private String NameOfProcess;
	private int sizeOfProcess;
	private int numberOfProcess;
	private String allocationType;
	private ArrayList<Process> holes=new ArrayList<Process>();
	private ArrayList<Process> processes=new ArrayList<Process>();
	private TablesController tablecontroller;
	private Parent root;
	private ObservableList<String> list = FXCollections.observableArrayList(
			Contstant.FF,
			Contstant.BF,
			Contstant.WF
            );
	public void pressAddHole(ActionEvent event) {
		holeSize=Integer.valueOf(size.getText());
		holeAdress=Integer.valueOf(startingAddress.getText());
		tablecontroller.AddHole(holeSize, holeAdress);
	}
public void pressAddProcess(ActionEvent event) {
		NameOfProcess=ProcessName.toString();
		sizeOfProcess=Integer.valueOf(ProcessSize.getText());
		tablecontroller.AddProcess(NameOfProcess, sizeOfProcess);
	}
	public void typeChoise(ActionEvent event) {
		allocationType=combobox.getValue();	
	}
	public void pressNext(ActionEvent event) throws IOException {
		Stage stage=(Stage)size.getScene().getWindow();

        tablecontroller.setAllocationType(allocationType);
        Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle application) {
		numberOfProcess=Integer.valueOf(NumberOfProcesses.getText());
		System.out.print(allocationType);
		Stage stage=(Stage)size.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Tables.fxml"));
        try {
			root = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        tablecontroller=loader.getController();
        
		combobox.setItems(list);
	}
	
}
