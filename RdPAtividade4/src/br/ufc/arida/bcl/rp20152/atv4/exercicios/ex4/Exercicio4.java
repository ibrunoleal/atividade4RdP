package br.ufc.arida.bcl.rp20152.atv4.exercicios.ex4;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class Exercicio4 {

	public static void main(String[] args) {
		
		Exercicio4Functions f = new Exercicio4Functions();
		
		System.out.println("pi_0; " + f.piK(0));
		System.out.println("pi_1; " + f.piK(1));
		System.out.println("pi_2; " + f.piK(2));
		
		RealVector u0 = f.meanK(0);
		System.out.println("u0: " + u0);
		RealVector u1 = f.meanK(1);
		System.out.println("u1: " + u1);
		RealVector u2 = f.meanK(2);
		System.out.println("u2: " + u2);
		
		RealMatrix s0 = f.covarianceMatrixOfK(0);
		System.out.println(s0);
		
		RealMatrix Sigma = f.sigma();
		System.out.println(Sigma);
		
	}

}
