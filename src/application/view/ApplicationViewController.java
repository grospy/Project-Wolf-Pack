package application.view;

import java.util.ArrayList;

import application.Calculation;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;

public class ApplicationViewController {

    private boolean calculated = false;
    private Calculation model = new Calculation();

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

    
    public void setMainApp(Main mainApp) {
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
    	if (isInputValid()) {
    		results.setText("");
    		double cows = Double.parseDouble(cowsField.getText());
    		double horses = Double.parseDouble(horsesField.getText());
    		int wolves = Integer.parseInt(wolvesField.getText());
    		int years = Integer.parseInt(periodField.getText());
    		model.setValues(cows, horses, wolves, years);
    		model.calculate();
    		results.setText(model.print());
    		calculated = true;
    	}
		
    }
    
    @SuppressWarnings("unchecked")
	@FXML
    private void drawGraph() {
    	if (calculated) {
    		lineChart.getData().clear();
    		XYChart.Series<Number, Number> seriesCow = new XYChart.Series<Number, Number>();
    		XYChart.Series<Number, Number> seriesHorse = new XYChart.Series<Number, Number>();
    		
    		lineChart.setTitle("Population Prediction");
    		seriesCow.setName("Cows");
    		seriesHorse.setName("Horses");
    		
    		ArrayList<Double> cowData = Calculation.getCowPrediction();
    		ArrayList<Double> horseData = Calculation.getHorsePrediction();
    		int i = 1;
    		int j = 1;
    		
    		for (double item : cowData) {
    			seriesCow.getData().add(new XYChart.Data<Number, Number>(i, item));
    			i++;
    		}
    		for (double item : horseData) {
    			seriesHorse.getData().add(new XYChart.Data<Number, Number>(j, item));
    			j++;
    		}
    		
    		lineChart.getData().addAll(seriesCow, seriesHorse);
    		lineChart.setCreateSymbols(false);
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
