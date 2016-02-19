package application;

import java.util.ArrayList;

public class Calculation {
	
	// Default Values
	public final static String packSizeDef = "10";
	public final static String initialCowDef = "100";
	public final static String initialHorseDef = "120";
	public final static String predictionYearsDef = "3";
	
	// USER INPUT
	private int packSize;
	private Double initialCow;
	private Double initialHorse;
	private int predictionYears;
	
	// growtRateCowYear/12 => 0.19/12 = 0.0158
	private final double growthRateCow = 0.0158;
	// growtRateHorseYear/12 => 0.21/12 = 0.0175
	private final double growthRateHorse = 0.0175;
	// competition coefficients
	private final double compCoefCow = 1.56;
	private final double compCoefHorse = 0.64;
	// carrying capacities
	private final int carryCapCow = 2100;
	private final int carryCapHorse = 1400;
	// prediction storage
	private static ArrayList<Double> cowPredictions = new ArrayList<Double>();
	private static ArrayList<Double> horsePredictions = new ArrayList<Double>();
	
	public Calculation() {}
	
	public void setValues(double cows, double horses, int wolves, int years) {
		this.packSize = wolves;
		this.initialCow = cows;
		this.initialHorse = horses;
		this.predictionYears = years;
	}
	
	
	// Only affects the population of the Horses
	public double predationResult(double horses) {
		double oneHorse = 300;
		double period = 30.5;
		double meatPerDay = 5;
		double result = (meatPerDay * period * packSize / oneHorse) * (horses/56);
		return result;
	}
	
	//Calculates the population size of the animal
	public double competitionCow(double population, double competitor) {
		double result = population + growthRateCow*population*((carryCapCow - population - compCoefCow*competitor)/carryCapHorse);
		return result;
	}
	
	public double competitionHorse(double population, double competitor) {
		double result = population + growthRateHorse*population*((carryCapHorse - population - compCoefHorse*competitor)/carryCapCow);
		return result;
	}

	public void calculate() {
		int period = predictionYears * 12;
		cowPredictions.clear();
		horsePredictions.clear();
		cowPredictions.add(initialCow);
		horsePredictions.add(initialHorse);
		for (int i = 0; i < period; i++) {
			double currentMonthCows = competitionCow(cowPredictions.get(i), horsePredictions.get(i));
			double currentMonthHouse = competitionHorse(horsePredictions.get(i), cowPredictions.get(i)) - predationResult(horsePredictions.get(i));
			cowPredictions.add(currentMonthCows);
			horsePredictions.add(currentMonthHouse);
		}
	}
	
	public static ArrayList<Double> getCowPrediction() {
		return cowPredictions;
	}
	
	public static ArrayList<Double> getHorsePrediction() {
		return horsePredictions;
	}

	public String print() {
		String predictionResult = "";
		for (int i=0; i<predictionYears*12; i++) {
			int x = i+1;
			predictionResult = predictionResult + "Cows in month " + x + " : " +cowPredictions.get(i)+"\n";
			predictionResult = predictionResult + "Horses in month " + x + " : " +horsePredictions.get(i)+"\n";
		}
		return predictionResult;
	}
}
