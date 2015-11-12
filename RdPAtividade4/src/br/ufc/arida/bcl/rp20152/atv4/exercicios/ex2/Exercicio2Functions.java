package br.ufc.arida.bcl.rp20152.atv4.exercicios.ex2;

import org.apache.commons.math3.distribution.MultivariateNormalDistribution;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class Exercicio2Functions {
	
	public RealMatrix getRandomSamples(int numSamples, RealVector means, RealMatrix covarianceMatrix) {
		MultivariateNormalDistribution mnd = new MultivariateNormalDistribution(means.toArray(), covarianceMatrix.getData());
		RealMatrix samples = new Array2DRowRealMatrix(mnd.sample(numSamples));
		return samples;
	}

}
