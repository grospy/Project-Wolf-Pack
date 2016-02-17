package application.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class RootLayoutController {
    
    @FXML
    private void handleExit() {
        System.exit(0);
    }
    
    @FXML
    private void handleAbout() {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Information");
    	alert.setHeaderText("About");
    	alert.setContentText("Application created by:\n"
    						+ "Andrei Frunze (551107) & "
    						+ "Shamil Karimli (523001)\n\n"
    						+ "Project Wolf Pack");
    	alert.showAndWait();
    }

	
}
