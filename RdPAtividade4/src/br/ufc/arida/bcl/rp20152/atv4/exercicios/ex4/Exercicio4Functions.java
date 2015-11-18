package br.ufc.arida.bcl.rp20152.atv4.exercicios.ex4;

import java.util.List;

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
	
	public RealVector mean(RealMatrix samples) {
		VectorialMean vm = new VectorialMean(samples.getColumnDimension());
		for (int i = 0; i < samples.getRowDimension(); i++) {
			RealVector xi = samples.getRowVector(i);
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
	
	public RealVector w(RealMatrix samples, RealMatrix sigma) {
		RealVector uk = mean(samples);
		RealMatrix SigmaI = MatrixUtils.inverse(sigma);
		return SigmaI.operate(uk);
	}
	
	public double w0(RealVector u, RealMatrix sigma, double pi) {
		RealMatrix SigmaI = MatrixUtils.inverse(sigma);
		double ln_pCk = Math.log(pi);
		
		RealMatrix ukT = new Array2DRowRealMatrix(u.toArray()).transpose();
		return ukT.scalarMultiply(-1.0/2.0).multiply(SigmaI).operate(u).mapAdd(ln_pCk).getEntry(0);
	}
	
	public double a(RealVector x, RealVector w, double w0) {
		RealMatrix wT = new Array2DRowRealMatrix(w.toArray()).transpose();
		return wT.operate(x).mapAdd(w0).getEntry(0);
	}
	
	public double pCkX(RealVector x, int k, List<RealVector> ws, List<Double> w0s) {
		double ak = Math.exp(a(x, ws.get(k), w0s.get(k)));
		
		double sum = 0;
		for (int ki = 0; ki < 3; ki++) {
			double ai = Math.exp(a(x, ws.get(ki), w0s.get(ki)));
			sum += ai;
		}
		
		return ak / sum;
	}
	
	public int classificarX(RealVector x, List<RealVector> ws, List<Double> w0s) {
		int classificacao = -1;
		double p = -1;
		for (int k = 0; k < 3; k++) {
			double pi = pCkX(x, k, ws, w0s);
			if (pi > p) {
				classificacao = k;
				p = pi;
			}
		}
		return classificacao;
	}
	
	public RealVector ysPreditos(RealMatrix samples, List<RealVector> ws, List<Double> w0s) {
		RealVector preditos = new ArrayRealVector(samples.getRowDimension());
		for (int i = 0; i < samples.getRowDimension(); i++) {
			RealVector xi = samples.getRowVector(i);
			int classe = classificarX(xi, ws, w0s);
			preditos.setEntry(i, classe);
		}
		return preditos;
	}
	
	public int classePeloLabel(RealVector label) {
		for (int i = 0; i < label.getDimension(); i++) {
			if (label.getEntry(i) == 1) {
				return i;
			}
		}
		return -1;
	}
	
	public double taxaDeSemelhanca(RealVector preditos, RealMatrix labels) {
		double cont = 0;
		for (int i = 0; i < preditos.getDimension(); i++) {
			if (preditos.getEntry(i) == classePeloLabel(labels.getRowVector(i))) {
				cont++;
			}
		}
		double n = preditos.getDimension();
		return (cont * 100) / n;
	}
}
