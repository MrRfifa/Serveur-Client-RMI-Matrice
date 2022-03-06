package application;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import application.fabimpl;

public class scene0Controller 
{	
	@FXML 
	Button button0;
	@FXML
	Label label2;
	
	public void connect(ActionEvent event)
	{
		try {
			  LocateRegistry.createRegistry(1099);
			  label2.setOpacity(1);
			  Naming.rebind("fab", new fabimpl());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	} 
}
