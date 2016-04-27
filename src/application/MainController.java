package application;
import java.awt.TextField;
import java.net.URL;
import java.util.ResourceBundle;

import allocator.MemoryAllocator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

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
	private ObservableList<String> list = FXCollections.observableArrayList(
			Contstant.FF,
			Contstant.BF,
			Contstant.WF
            );
	public void pressAddHole(ActionEvent event) {
		holeSize=Integer.valueOf(size.getText());
		holeAdress=Integer.valueOf(startingAddress.getText());
		
	}
public void pressAddProcess(ActionEvent event) {
		NameOfProcess=ProcessName.toString();
		sizeOfProcess=Integer.valueOf(ProcessSize.getText());
		
	}
	public void pressNext(ActionEvent event) {
		
	}
	public void typeChoise(ActionEvent event) {
		
	}
	@Override
	public void initialize(URL location, ResourceBundle application) {
		combobox.setItems(list);
	}
	
}
