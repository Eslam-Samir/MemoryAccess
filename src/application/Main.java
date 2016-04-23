package application;
	
import java.util.ArrayList;

import allocator.*;
import allocator.Process;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader =new FXMLLoader(getClass().getResource("Main.fxml"));
			Parent root =loader.load();
			Scene scene=new Scene(root);
			primaryStage.setTitle("Memory Assignment");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			/*
			Process p0 = new Process("P0",  200, 0, ProcessType.process);
			Process p1 = new Process("P1",  300, 0, ProcessType.process);
			Process p2 = new Process("P2",  800, 0, ProcessType.process);
			ArrayList<Process> input = new ArrayList<>();
			input.add(p0); 
			input.add(p1);
			input.add(p2);
			
			Process h0 = new Process("h0",  800, 0, ProcessType.hole);
			Process h1 = new Process("h1",  600, 900, ProcessType.hole);
			Process h2 = new Process("h2",  2000, 1800, ProcessType.hole);
			ArrayList<Process> holes = new ArrayList<>();
			holes.add(h0); 
			holes.add(h1);
			holes.add(h2);
			
			MemoryAllocator mem = new FirstFitAllocator(3, input, holes);
			mem.RunAllocator();
			//mem.getOutput();
			mem.DeallocateProcess("P0");
			mem.DeallocateProcess("P1");
			mem.DeallocateProcess("P2");
			mem.getOutput();*/
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
