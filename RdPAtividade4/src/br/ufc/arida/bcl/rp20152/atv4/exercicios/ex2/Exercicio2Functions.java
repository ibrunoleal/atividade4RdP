package br.ufc.arida.bcl.rp20152.atv4.exercicios.ex2;

import org.apache.commons.math3.distribution.MultivariateNormalDistribution;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class Exercicio2Functions {
	
	public RealMatrix getNormalSamples(int numSamples, RealVector means, RealMatrix covarianceMatrix) {
		MultivariateNormalDistribution mnd = new MultivariateNormalDistribution(means.toArray(), covarianceMatrix.getData());
		RealMatrix samples = new Array2DRowRealMatrix(mnd.sample(numSamples));
		return samples;
	}

	public RealVector calcularM(RealMatrix X) {
		RealVector m = new ArrayRealVector(2);
		for (int i = 0; i < X.getRowDimension(); i++) {
			RealVector xi = X.getRowVector(i);
			m = m.add(xi);
		}
		return m.mapDivide(X.getRowDimension());
	}
	
	public RealMatrix sW(RealVector m, RealMatrix X) {
		RealMatrix SUM = new Array2DRowRealMatrix(2,2);
		for (int i = 0; i < X.getRowDimension(); i++) {
			RealVector xi = X.getRowVector(i);
			RealVector xa = xi.subtract(m);
			RealMatrix XA = new Array2DRowRealMatrix(xa.toArray());
			RealVector xbtemp = xi.subtract(m);
			RealMatrix XB = new Array2DRowRealMatrix(xbtemp.toArray()).transpose();
			RealMatrix R = XA.multiply(XB);
			SUM = SUM.add(R);
		}
		return SUM;
	}
}
