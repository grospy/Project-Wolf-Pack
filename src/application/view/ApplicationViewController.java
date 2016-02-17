package application.view;

import application.Calculation;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;

public class ApplicationViewController {

    private Main mainApp;

    @FXML
    private TextField cowsField;
    @FXML
    private TextField horsesField;
    @FXML
    private TextField wolvesField;
    @FXML
    private TextField periodField;
    @FXML
    private TextArea results;
    
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    
    @FXML
    private void initialize() {
    	cowsField.setText(Calculation.initialCowDef);
    	horsesField.setText(Calculation.initialHorseDef);
    	wolvesField.setText(Calculation.packSizeDef);
    	periodField.setText(Calculation.predictionYearsDef);
    }
    
    @FXML
    private void calculate() {
    	System.out.println("Calculate Button Pressed!");
    	if (isInputValid()) {
    		double cows = Double.parseDouble(cowsField.getText());
    		double horses = Double.parseDouble(horsesField.getText());
    		int wolves = Integer.parseInt(wolvesField.getText());
    		int years = Integer.parseInt(periodField.getText());
    		Calculation model = new Calculation(cows, horses, wolves, years);
    		model.calculate();
    		results.setText(model.print());
    	}
		
    }
    
    @FXML
    private void drawGraph() {
    	System.out.println("Draw Graph Button Pressed!");
    }
	
    @FXML
    private void exportXML() {
    	System.out.println("Low Priority Feature");
    }
    
    private boolean isInputValid() {
    	// To be implemented
    	return true;
    }
}
