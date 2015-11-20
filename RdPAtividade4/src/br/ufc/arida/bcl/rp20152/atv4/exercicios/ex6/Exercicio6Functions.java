package br.ufc.arida.bcl.rp20152.atv4.exercicios.ex6;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import br.ufc.arida.bcl.rp20152.atv4.file.FileHandler;

public class Exercicio6Functions {
	
	private final String DATA_IRIS = "data/data-iris.csv";
	
	private FileHandler fileHandler;
	
	private RealMatrix data_iris_samples;
	
	private RealMatrix data_iris_labels;
	
	public Exercicio6Functions() {
		fileHandler = new FileHandler(DATA_IRIS, ";");
		RealMatrix data = new Array2DRowRealMatrix(fileHandler.getMatriz());
		
		int nRows = data.getRowDimension();
		int nCols = data.getColumnDimension();
		
		data_iris_samples = data.getSubMatrix(0, nRows - 1, 0, 3);
		data_iris_labels = data.getSubMatrix(0, nRows - 1, 4, nCols - 1);
	}
	
	public RealMatrix removeMean(RealMatrix M) {
		int nRows = M.getRowDimension();
		int nCols = M.getColumnDimension();
		RealMatrix R = new Array2DRowRealMatrix(nRows,nCols);
		for (int j = 0; j < nCols; j++) {
			RealVector xi = M.getColumnVector(j);
			double mean = StatUtils.mean(xi.toArray());
			xi = xi.mapSubtract(mean);
			R.setColumnVector(j, xi);
		}
		return R;
	}
	
	public RealMatrix divideByStandardDeviation(RealMatrix M) {
		int nRows = M.getRowDimension();
		int nCols = M.getColumnDimension();
		RealMatrix R = new Array2DRowRealMatrix(nRows,nCols);
		StandardDeviation desvioPadrao = new StandardDeviation();
		for (int j = 0; j < nCols; j++) {
			RealVector xi = M.getColumnVector(j);
			double e = desvioPadrao.evaluate(xi.toArray());
			xi = xi.mapDivide(e);
			R.setColumnVector(j, xi);
		}
		return R;
	}
	
	public RealMatrix computarMatrizX(RealMatrix M) {
		RealMatrix R = removeMean(M);
		RealMatrix X = divideByStandardDeviation(R);
		return X;
	}
	
	
	public RealMatrix computarMatrizS(RealMatrix M) {
		RealMatrix X = computarMatrizX(M);
		int N = M.getRowDimension();
		RealMatrix XT = X.transpose();
		RealMatrix S = XT.scalarMultiply(1.0 / (N-1)).multiply(X);
		return S;
	}
	
	public int contarElementosDaClasse(int k, RealMatrix samples, RealMatrix labels) {
		int cont = 0;
		for (int i = 0; i < samples.getRowDimension(); i++) {
			int classificacao = (int)labels.getEntry(i, k);
			if (classificacao == 1) {
				cont++;
			}
		}
		return cont;
	}
	
	public RealMatrix elementosDaClasseK(int k, RealMatrix samples, RealMatrix labels) {
		int n = contarElementosDaClasse(k, samples, labels);
		RealMatrix m = new Array2DRowRealMatrix(n,samples.getColumnDimension());
		int cont = 0;
		for (int i = 0; i < labels.getRowDimension(); i++) {
			int classificacao = (int)labels.getEntry(i, k);
			if (classificacao == 1) {
				RealVector xi = samples.getRowVector(i);
				m.setRowVector(cont, xi);
				cont++;
			}
		}
		return m;
	}
	
	public RealMatrix matrizDeAutoValores(RealMatrix M) {
		EigenDecomposition eigen = new EigenDecomposition(M);
		return eigen.getD();
	}
	
	public RealMatrix matrizDeAutoVetores(RealMatrix M) {
		EigenDecomposition eigen = new EigenDecomposition(M);
		return eigen.getV();
	}
	
	public RealMatrix matrizPTil(RealMatrix M) {
		RealVector c1 = M.getColumnVector(0);
		RealVector c2 = M.getColumnVector(1);
		RealMatrix R = new Array2DRowRealMatrix(c1.getDimension(), 2);
		R.setColumnVector(0, c1);
		R.setColumnVector(1, c2);
		return R;
	}

	public RealMatrix getData_iris_samples() {
		return data_iris_samples;
	}

	public void setData_iris_samples(RealMatrix data_iris_samples) {
		this.data_iris_samples = data_iris_samples;
	}

	public RealMatrix getData_iris_labels() {
		return data_iris_labels;
	}

	public void setData_iris_labels(RealMatrix data_iris_labels) {
		this.data_iris_labels = data_iris_labels;
	}
	
	

}
