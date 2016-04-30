package extras;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Common {

	public static void createAlert(String warnings)
	{
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Wrong input");
		alert.setHeaderText(warnings);
		alert.show();
	}
	
	public static void createAlert(String warnings, String content)
	{
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Wrong input");
		alert.setHeaderText(warnings);
		alert.setContentText(content);
		alert.show();
	}

}
