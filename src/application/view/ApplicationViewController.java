package application.view;

import java.awt.event.WindowStateListener;
import java.util.ArrayList;

import application.Calculation;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;

public class ApplicationViewController {

    private Main mainApp;
    private boolean calculated = false;

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
    @FXML
    private LineChart<Number, Number> lineChart;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    
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
    		calculated = true;
    	}
		
    }
    
    @FXML
    private void drawGraph() {
    	System.out.println("Draw Graph Pressed!");
    	if (calculated) {
    		xAxis = new NumberAxis();
    		yAxis = new NumberAxis();
    		lineChart = new LineChart<Number, Number>(xAxis, yAxis);
    		XYChart.Series seriesCow = new XYChart.Series();
    		XYChart.Series seriesHorse = new XYChart.Series();
    		
    		lineChart.setTitle("Population Prediction");
    		seriesCow.setName("Cows");
    		seriesHorse.setName("Horses");
    		xAxis.setLabel("Month number");
    		yAxis.setLabel("Population size");
    		
    		ArrayList<Double> cowData = Calculation.getCowPrediction();
    		ArrayList<Double> horseData = Calculation.getHorsePrediction();
    		int i = 1;
    		int j = 1;
    		
    		for (double item : cowData) {
    			seriesCow.getData().add(new XYChart.Data(i, item));
    			i++;
    		}
    		for (double item : horseData) {
    			seriesHorse.getData().add(new XYChart.Data(j, item));
    			j++;
    		}
    		
    		lineChart.getData().addAll(seriesCow, seriesHorse);
    	}
    }
	
    @FXML
    private void exportXML() {
    	if (calculated) {
    		System.out.println("Low Priority Feature");
		}	
    }
    
    // Source: CodeMakery - JavaFX Tutorial
    private boolean isInputValid() {
        String errorMessage = "";

        if (cowsField.getText() == null || cowsField.getText().length() == 0) {
            errorMessage += "No valid cow population!\n"; 
        } else {
        	try {
                Double.parseDouble(cowsField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Cow population must be a number!\n"; 
            }
        }
        if (horsesField.getText() == null || horsesField.getText().length() == 0) {
            errorMessage += "No valid horse population!\n"; 
        } else {
        	try {
        		Double.parseDouble(wolvesField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Horse population must be a number!\n"; 
            }
        }
        if (wolvesField.getText() == null || wolvesField.getText().length() == 0) {
            errorMessage += "No valid period!\n"; 
        } else {
            try {
                Integer.parseInt(wolvesField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Pack size must be an integer!\n"; 
            }
        }

        if (periodField.getText() == null || periodField.getText().length() == 0) {
            errorMessage += "No valid period!\n"; 
        } else {
            try {
                Integer.parseInt(periodField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Prediction period must be an integer!\n"; 
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
