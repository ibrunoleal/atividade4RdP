package br.ufc.arida.bcl.rp20152.atv4.exercicios.ex2;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.distribution.MultivariateNormalDistribution;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import br.ufc.arida.bcl.rp20152.atv4.graficos.PontoDoGrafico2D;

public class Exercicio2Functions {
	
	public RealMatrix getNormalSamples(int numSamples, RealVector means, RealMatrix covarianceMatrix) {
		MultivariateNormalDistribution mnd = new MultivariateNormalDistribution(means.toArray(), covarianceMatrix.getData());
		RealMatrix samples = new Array2DRowRealMatrix(mnd.sample(numSamples));
		return samples;
	}

	public RealVector calcularM(RealMatrix X) {
		RealVector m = new ArrayRealVector(X.getColumnDimension());
		for (int i = 0; i < X.getRowDimension(); i++) {
			RealVector xi = X.getRowVector(i);
			m = m.add(xi);
		}
		return m.mapDivide(X.getRowDimension());
	}
	
	public RealMatrix calcularSWSample(RealMatrix X) {
		RealVector m = calcularM(X);
		RealMatrix SUM = new Array2DRowRealMatrix(X.getColumnDimension(),m.getDimension());
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
	
	public RealMatrix calcularSW(RealMatrix samples1, RealMatrix samples2) {
		RealMatrix SW1 = calcularSWSample(samples1);
		RealMatrix SW2 = calcularSWSample(samples2);
		RealMatrix SW = SW1.add(SW2);
		return SW;
	}
	
	public RealVector calcularW(RealMatrix samples1, RealMatrix samples2) {
		RealMatrix SW = calcularSW(samples1, samples2);
		RealMatrix SWI = MatrixUtils.inverse(SW);
		RealVector m1 = calcularM(samples1);
		RealVector m2 = calcularM(samples2);
		RealVector mres = m2.subtract(m1);
		RealVector w = SWI.operate(mres);
		return w;
	}
	
	public double yPredito(RealVector x, RealVector w) {
		RealMatrix wm = new Array2DRowRealMatrix(w.toArray());
		RealMatrix wt = wm.transpose();
		return wt.operate(x).getEntry(0);
	}
	
	public double calcular_w0(RealVector w, RealMatrix samples1, RealMatrix samples2) {
		RealMatrix wm = new Array2DRowRealMatrix(w.toArray());
		RealMatrix wt = wm.transpose();
		RealVector m = calcular_mTotal(samples1, samples2);
		wt = wt.scalarMultiply(-1.0);
		RealVector w0 = wt.operate(m);
		return w0.getEntry(0);
	}
	
	public RealVector calcular_mTotal(RealMatrix samples1, RealMatrix samples2) {
		RealVector m1 = calcularM(samples1);
		RealVector m2 = calcularM(samples2);
		m1 = m1.mapMultiply(samples1.getRowDimension());
		m2 = m2.mapMultiply(samples2.getRowDimension());
		RealVector res = m1.add(m2);
		res = res.mapDivide(samples1.getRowDimension() + samples2.getRowDimension());
		return res;
	}
	
	public int classificar(double y, RealVector w, RealMatrix samples1, RealMatrix samples2) {
		double w0 = calcular_w0(w, samples1, samples2);
		if (y >= (-1.0 * w0)) {
			return 1;
		} else {
			return -1;
		}
	}
	
	public double yDaReta(RealVector w, double x, double c) {
		double y = (c - (x * w.getEntry(1)) - w.getEntry(0)) / w.getEntry(2) ;
		return y;
	}
	
	public List<PontoDoGrafico2D> getPontosDaReta(RealVector w, double numPontos, double xMin, double xMax, double c) {
		double comprimento = (xMax - xMin) / numPontos;
		double xTemp = xMin;
		List<PontoDoGrafico2D> pontosDaReta = new ArrayList<PontoDoGrafico2D>();
		while(xTemp < xMax) {
			double yTemp = yDaReta(w, xTemp, c);
			PontoDoGrafico2D ptemp = new PontoDoGrafico2D(xTemp, yTemp);
			pontosDaReta.add(ptemp);
			xTemp += comprimento;
		}
		return pontosDaReta;
	}
}
