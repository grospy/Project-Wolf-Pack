package application;

import java.util.ArrayList;

public class Calculation {

	// USER INPUT
	private int packSize = 10;
	private Double initialCow = 30.0;
	private Double initialHorse = 20.0;
	private int predictionYears = 20;
	
	// growtRateCowYear/12 => 0.18/12 = 0.015
	private static final double growthRateCow = 0.18;
	// growtRateHorseYear/12 => 0.2/12 ~ 0.017
	private static final double growthRateHorse = 0.2;
	// competition coefficients
	private static final double compCoefCow = 0.64;
	private static final double compCoefHorse = 1.56;
	// carrying capacities
	private static final int carryCapCow = 2100;
	private static final int carryCapHorse = 1400;
	// prediction storage
	private ArrayList<Double> cowPredictions = new ArrayList<Double>();
	private ArrayList<Double> horsePredictions = new ArrayList<Double>();
	
	public Calculation() {
		
	}
	
	
	// Only affects the population of the Horses
	public double predationResult(double horses, double cows) {
		double oneHorse = 300;
		double period = 30.5;
		double meatPerDay = 7;
		double result;
		if (horses < 150) {
			result = (meatPerDay * period * packSize / oneHorse) * (horses/(horses+cows));
		} else {
			result = (meatPerDay * period * packSize / oneHorse);
		}
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
		for (int i = 0; i < period; i++) {
			double currentMonthCows = competitionCow(cowPredictions.get(i), horsePredictions.get(i));
			double currentMonthHouse = competitionHorse(horsePredictions.get(i), cowPredictions.get(i)) - predationResult(horsePredictions.get(i), cowPredictions.get(i));
			cowPredictions.add(currentMonthCows);
			horsePredictions.add(currentMonthHouse);
		}
	}
	
	public ArrayList<Double> getCowPrediction() {
		return cowPredictions;
	}
	
	public ArrayList<Double> getHorsePrediction() {
		return horsePredictions;
	}

	public void print() {
		for (int i=0; i<predictionYears*12; i++) {
			int x = i+1;
			System.out.println("Cows in month " + x + " : " +cowPredictions.get(i));
			System.out.println("Horses in month " + x + " : " +horsePredictions.get(i));
		}
	}
}
