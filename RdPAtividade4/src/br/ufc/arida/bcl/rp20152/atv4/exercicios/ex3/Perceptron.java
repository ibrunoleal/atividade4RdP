package br.ufc.arida.bcl.rp20152.atv4.exercicios.ex3;

/**
 *  The Perceptron Algorithm
 *  By Dr Noureddin Sadawi
 *  Please watch my youtube videos on perceptron for things to make sense!
 *  Copyright (C) 2014 
 *  @author Dr Noureddin Sadawi 
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it as you wish ONLY for legal and ethical purposes
 * 
 *  I ask you only, as a professional courtesy, to cite my name, web page 
 *  and my YouTube Channel!
 *  
 *    Code adapted from:
 *    https://github.com/RichardKnop/ansi-c-perceptron
 */


import java.text.*;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class Perceptron {
	
	private int max_iter = 1000;
	private double learning_rate = 0.1;           
	private int num_instances;
	private int theta = 0;
	
	private RealMatrix samples;
	private RealVector t;
	
	private double[] weights = new double[4];// 3 for input variables and one for bias
	
	public Perceptron(RealMatrix PHIsamples, RealVector t) {
		this.samples = PHIsamples;
		this.t = t;
		this.num_instances = samples.getRowDimension();
	}
	
	public void executar(){ 
		//three variables (features)                      
		double[] x = new double [num_instances];    
		double[] y = new double [num_instances];
		double[] z = new double [num_instances];
		int[] outputs = new int [num_instances];

		//alocar os pontos
		for (int i = 0; i < num_instances; i++) {
			x[i] = samples.getEntry(i, 0);
			y[i] = samples.getEntry(i, 1);
			z[i] = samples.getEntry(i, 2);
			outputs[i] = (int)t.getEntry(i);
		}

		double localError, globalError;
		int p, iteration, output;

//		weights[0] = randomNumber(0,1);// w1
//		weights[1] = randomNumber(0,1);// w2
//		weights[2] = randomNumber(0,1);// w3
//		weights[3] = randomNumber(0,1);// this is the bias
		weights[0] = 0.0;// w1
		weights[1] = 0.0;// w2
		weights[2] = 0.0;// w3
		weights[3] = 0.0;// this is the bias

		iteration = 0;
		do {
			iteration++;
			globalError = 0;
			//loop through all instances (complete one epoch)
			for (p = 0; p < num_instances; p++) {
				// calculate predicted class
				output = calculateOutput(theta,weights, x[p], y[p], z[p]);
				// difference between predicted and actual class values
				localError = outputs[p] - output;
				//update weights and bias
				weights[0] += learning_rate * localError * x[p];
				weights[1] += learning_rate * localError * y[p];
				weights[2] += learning_rate * localError * z[p];
				weights[3] += learning_rate * localError;
				//summation of squared error (error value for all instances)
				globalError += (localError*localError);
			}

			/* Root Mean Squared Error */
			System.out.println("Iteration "+iteration+" : RMSE = "+Math.sqrt(globalError/num_instances));
		} while (globalError != 0 && iteration<=max_iter);

		System.out.println("\n=======\nDecision boundary equation:");
		System.out.println(weights[0] +"*x + "+weights[1]+"*y +  "+weights[2]+"*z + "+weights[3]+" = 0");
		
		//generate 10 new random points and check their classes
		//notice the range of -10 and 10 means the new point could be of class 1 or 0
		//-10 to 10 covers all the ranges we used in generating the 50 classes of 1's and 0's above
//		for(int j = 0; j < 10; j++){
//			double x1 = randomNumber(-10 , 10);
//			double y1 = randomNumber(-10 , 10);   
//			double z1 = randomNumber(-10 , 10); 
//
//			output = calculateOutput(theta,weights, x1, y1, z1);
//			System.out.println("\n=======\nNew Random Point:");
//			System.out.println("x = "+x1+",y = "+y1+ ",z = "+z1);
//			System.out.println("class = "+output);
//		}
	}//end main  

	public RealVector getW() {
		double[] wd = {weights[0], weights[1], weights[2]};
		RealVector w = new ArrayRealVector(wd);
		return w;
	}
	
	public double getC() {
		return weights[3];
	}
	
	/**
	 * returns a random double value within a given range
	 * @param min the minimum value of the required range (int)
	 * @param max the maximum value of the required range (int)
	 * @return a random double value between min and max
	 */ 
	public static double randomNumber(int min , int max) {
		DecimalFormat df = new DecimalFormat("#.####");
		double d = min + Math.random() * (max - min);
		String s = df.format(d);
		double x = Double.parseDouble(s);
		return x;
	}

	/**
	 * returns either 1 or 0 using a threshold function
	 * theta is 0range
	 * @param theta an integer value for the threshold
	 * @param weights[] the array of weights
	 * @param x the x input value
	 * @param y the y input value
	 * @param z the z input value
	 * @return 1 or 0
	 */ 
	static int calculateOutput(int theta, double weights[], double x, double y, double z)
	{
		double sum = x * weights[0] + y * weights[1] + z * weights[2] + weights[3];
		return (sum >= theta) ? 1 : -1;
	}

}


