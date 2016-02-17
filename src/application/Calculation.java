package application;

import java.util.ArrayList;

public class Calculation {
	
	// Default Values
	public final static String packSizeDef = "10";
	public final static String initialCowDef = "100.0";
	public final static String initialHorseDef = "200.0";
	public final static String predictionYearsDef = "20";
	
	// USER INPUT
	private int packSize;
	private Double initialCow;
	private Double initialHorse;
	private int predictionYears;
	
	// growtRateCowYear/12 => 0.18/12 = 0.015
	private final double growthRateCow = 0.015;
	// growtRateHorseYear/12 => 0.2/12 ~ 0.017
	private final double growthRateHorse = 0.017;
	// competition coefficients
	private final double compCoefCow = 0.64;
	private final double compCoefHorse = 1.56;
	// carrying capacities
	private final int carryCapCow = 2100;
	private final int carryCapHorse = 1400;
	// prediction storage
	private ArrayList<Double> cowPredictions = new ArrayList<Double>();
	private ArrayList<Double> horsePredictions = new ArrayList<Double>();
	private ArrayList<Double> predation = new ArrayList<Double>();
	
	public Calculation(double cows, double horses, int wolves, int years) {
		this.packSize = wolves;
		this.initialCow = cows;
		this.initialHorse = horses;
		this.predictionYears = years;
	}
	
	
	// Only affects the population of the Horses
	public double predationResult(double horses, double cows) {
		double oneHorse = 300;
		double period = 30.5;
		double meatPerDay = 7;
		double result;
//		if (horses < 150) {
			result = (meatPerDay * period * packSize / oneHorse) * (horses/(horses+cows));
//		}
		return result;
	}
	
	//Calculates the population size of the animal
	public double competitionCow(double population, double competitor) {
		double result = population + growthRateCow*population*((carryCapCow - population - compCoefHorse*competitor)/carryCapHorse);
		return result;
	}
	
	public double competitionHorse(double population, double competitor) {
		double result = population + growthRateHorse*population*((carryCapHorse - population - compCoefCow*competitor)/carryCapCow);
		return result;
	}

	public void calculate() {
		int period = predictionYears * 12;
		cowPredictions.add(initialCow);
		horsePredictions.add(initialHorse);
		predation.add(0.0);
		for (int i = 0; i < period; i++) {
			double currentMonthCows = competitionCow(cowPredictions.get(i), horsePredictions.get(i));
			double currentMonthHouse = competitionHorse(horsePredictions.get(i), cowPredictions.get(i)) - predationResult(horsePredictions.get(i), cowPredictions.get(i));
			cowPredictions.add(currentMonthCows);
			horsePredictions.add(currentMonthHouse);
			predation.add(predationResult(horsePredictions.get(i), cowPredictions.get(i)));
		}
	}
	
	public ArrayList<Double> getCowPrediction() {
		return cowPredictions;
	}
	
	public ArrayList<Double> getHorsePrediction() {
		return horsePredictions;
	}

	public String print() {
		String predictionResult = "";
		for (int i=0; i<predictionYears*12; i++) {
			int x = i+1;
			//System.out.println("Cows in month " + x + " : " +cowPredictions.get(i));
			//System.out.println("Horses in month " + x + " : " +horsePredictions.get(i));
			predictionResult = predictionResult + "Cows in month " + x + " : " +cowPredictions.get(i)+"\n";
			predictionResult = predictionResult + "Horses in month " + x + " : " +horsePredictions.get(i)+"\n";
			//System.out.println("Predation in month " + x + " : " + predation.get(i));
		}
		return predictionResult;
	}
}
