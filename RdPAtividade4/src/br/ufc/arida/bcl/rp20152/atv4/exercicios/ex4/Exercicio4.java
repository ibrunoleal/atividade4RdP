package br.ufc.arida.bcl.rp20152.atv4.exercicios.ex4;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

public class Exercicio4 {

	public static void main(String[] args) {
		
		Exercicio4Functions f = new Exercicio4Functions();
		
		RealVector yPreditos = new ArrayRealVector(f.getData_III_samples_testing().getRowDimension());
		for (int i = 0; i < yPreditos.getDimension(); i++) {
			int classificação = -1;
			double p = -1;
			RealVector xi = f.getData_III_samples_testing().getRowVector(i);
			for (int k = 0; k < 3; k++) {
				double pi = f.pCkX(xi, k);
				if (pi > p) {
					classificação = k;
					p = pi;
				}
			}
			yPreditos.setEntry(i, classificação);
		}
		System.out.println(yPreditos);
		
	}

}
