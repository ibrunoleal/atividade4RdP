package br.ufc.arida.bcl.rp20152.atv4.exercicios.ex4;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.descriptive.moment.VectorialMean;

import br.ufc.arida.bcl.rp20152.atv4.file.FileHandler;

public class Exercicio4Functions {
	
	private final String DATA_III_SAMPLES_LEARNING = "data/data-III-samples-learning.csv";
	private final String DATA_III_LABELS_LEARNING = "data/data-III-labels-learning.csv";
	
	private final String DATA_III_SAMPLES_TESTING = "data/data-III-samples-testing.csv";
	private final String DATA_III_LABELS_TESTING = "data/data-III-labels-testing.csv";
	
	private FileHandler fileHandler;
	
	private RealMatrix data_III_samples_learning;
	
	private RealMatrix data_III_labels_learning;
	
	private RealMatrix data_III_samples_testing;
	
	private RealMatrix data_III_labels_testing;
	
	public Exercicio4Functions() {
		fileHandler = new FileHandler(DATA_III_SAMPLES_LEARNING, ";");
		data_III_samples_learning = new Array2DRowRealMatrix(fileHandler.getMatriz());
		fileHandler = new FileHandler(DATA_III_LABELS_LEARNING, ";");
		data_III_labels_learning = new Array2DRowRealMatrix(fileHandler.getMatriz());
		
		fileHandler = new FileHandler(DATA_III_SAMPLES_TESTING, ";");
		data_III_samples_testing = new Array2DRowRealMatrix(fileHandler.getMatriz());
		fileHandler = new FileHandler(DATA_III_LABELS_TESTING, ";");
		data_III_labels_testing = new Array2DRowRealMatrix(fileHandler.getMatriz());
	}

	public RealMatrix getData_III_samples_learning() {
		return data_III_samples_learning;
	}

	public RealMatrix getData_III_labels_learning() {
		return data_III_labels_learning;
	}
	
	
	
	public RealMatrix getData_III_samples_testing() {
		return data_III_samples_testing;
	}


	public RealMatrix getData_III_labels_testing() {
		return data_III_labels_testing;
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
	
	public double piK(int k, RealMatrix samples, RealMatrix labels) {
		double nk = contarElementosDaClasse(k, samples, labels);
		return nk / labels.getRowDimension();
	}
	
	public RealMatrix elementosDaClasseK(int k, RealMatrix samples, RealMatrix labels) {
		int n = contarElementosDaClasse(k, samples, labels);
		RealMatrix m = new Array2DRowRealMatrix(n,3);
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
	
	public RealVector meanK(int k, RealMatrix samples, RealMatrix labels) {
		RealMatrix mk = elementosDaClasseK(k, samples, labels);
		VectorialMean vm = new VectorialMean(mk.getColumnDimension());
		for (int i = 0; i < mk.getRowDimension(); i++) {
			RealVector xi = mk.getRowVector(i);
			vm.increment(xi.toArray());
		}
		return new ArrayRealVector(vm.getResult());
	}
	
	public RealMatrix covarianceMatrixOfK(int k, RealMatrix samples, RealMatrix labels) {
		RealMatrix Mk = elementosDaClasseK(k, samples, labels);
		Covariance cov = new Covariance(Mk);
		return cov.getCovarianceMatrix();
	}
	
	public RealMatrix sigma(RealMatrix samples, RealMatrix labels) {
		int nRows = covarianceMatrixOfK(0, samples, labels).getRowDimension();
		int nCols = covarianceMatrixOfK(0, samples, labels).getColumnDimension();
		RealMatrix Sigma = new Array2DRowRealMatrix(nRows, nCols);
		for (int k = 0; k < 3; k++) {
			double pi = piK(k, samples, labels);
			RealMatrix Sk = covarianceMatrixOfK(k, samples, labels);
			RealMatrix Temp = Sk.scalarMultiply(pi);
			Sigma = Sigma.add(Temp);
		}
		return Sigma;
	}
	
	public RealVector w(int k, RealMatrix samples, RealMatrix labels) {
		RealVector uk = meanK(k, samples, labels);
		RealMatrix SigmaI = MatrixUtils.inverse(sigma(samples, labels));
		return SigmaI.operate(uk);
	}
	
	public double w0(int k, RealMatrix samples, RealMatrix labels) {
		double pCk = piK(k, samples, labels);
		RealVector uk = meanK(k, samples, labels);
		RealMatrix SigmaI = MatrixUtils.inverse(sigma(samples, labels));
		double ln_pCk = Math.log(pCk);
		
		RealMatrix ukT = new Array2DRowRealMatrix(uk.toArray()).transpose();
		return ukT.scalarMultiply(-1.0/2.0).multiply(SigmaI).operate(uk).mapAdd(ln_pCk).getEntry(0);
	}
	
	public double a(RealVector x, int k, RealMatrix samples, RealMatrix labels) {
		RealVector wk = w(k, samples, labels);
		double w0k = w0(k, samples, labels);
		RealMatrix wkT = new Array2DRowRealMatrix(wk.toArray()).transpose();
		return wkT.operate(x).mapAdd(w0k).getEntry(0);
	}
	
	public double pCkX(RealVector x, int k) {
		double ak = Math.exp(a(x, k, data_III_samples_learning, data_III_labels_learning));
		
		double sum = 0;
		for (int ki = 0; ki < 3; ki++) {
			double ai = Math.exp(a(x, ki, data_III_samples_learning, data_III_labels_learning));
			sum += ai;
		}
		
		return ak / sum;
	}
}
