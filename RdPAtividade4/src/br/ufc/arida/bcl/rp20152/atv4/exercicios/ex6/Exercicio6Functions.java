package br.ufc.arida.bcl.rp20152.atv4.exercicios.ex6;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import br.ufc.arida.bcl.rp20152.atv4.file.FileHandler;
import br.ufc.arida.bcl.rp20152.atv4.graficos.PontoDoGrafico2D;

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

	public RealMatrix getMatrizPHI(RealMatrix M) {
		RealMatrix temp = new Array2DRowRealMatrix(M.getRowDimension(), M.getColumnDimension() + 1);
		for (int i = 0; i < M.getRowDimension(); i++) {
			for (int j = 0; j < M.getColumnDimension() + 1; j++) {
				if (j == 0) {
					temp.setEntry(i, j, 1);
				} else {
					temp.setEntry(i, j, M.getEntry(i, j-1));
				}
			}
		}
		return temp;
	}
	
	public RealVector wML(RealMatrix PHI, RealVector t) {
		RealMatrix PHI_t = PHI.transpose();
		RealMatrix A = PHI_t.multiply(PHI);
		RealMatrix A_I = MatrixUtils.inverse(A);
		RealMatrix B = A_I.multiply(PHI_t);
		RealVector r = B.operate(t);
		return r;
	}
	
	public double yPredito(RealVector x, RealVector w) {
		RealMatrix wm = new Array2DRowRealMatrix(w.toArray());
		RealMatrix wt = wm.transpose();
		return wt.operate(x).getEntry(0);
	}
	
	public RealVector vetorDeMesmoValor(int dimension, double valor) {
		RealVector vetor = new ArrayRealVector(dimension);
		for (int i = 0; i < dimension; i++) {
			vetor.setEntry(i, valor);
		}
		return vetor;
	}
	
	public int classificar(RealVector x, RealMatrix W) {
		RealVector ys = new ArrayRealVector(W.getRowDimension());
		for (int i = 0; i < W.getRowDimension(); i++) {
			RealVector wi = W.getRowVector(i);
			double yi = yPredito(x, wi);
			ys.setEntry(i, yi);
		}
		return ys.getMaxIndex();
	}
	
	public int getClasse(RealVector label) {
		for (int i = 0; i < label.getDimension(); i++) {
			if (label.getEntry(i) == 1) {
				return i;
			}
		}
		return -1;
	}
	
	public double taxaDeSemelhanca(RealVector v1, RealVector v2) {
		int cont = 0;
		if (v1.getDimension() == v2.getDimension()) {
			for (int i = 0; i < v1.getDimension(); i++) {
				if (v1.getEntry(i) == v2.getEntry(i)) {
					cont++;
				}
			}
			double taxa = (cont * 100.0) / v1.getDimension();
			return taxa;
		}
		return 0;
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
	
	public RealVector getVectorLabels(RealMatrix matrizDeLabels) {
		RealVector vLabels = new ArrayRealVector(matrizDeLabels.getRowDimension());
		for (int i = 0; i < matrizDeLabels.getRowDimension(); i++) {
			RealVector xi = matrizDeLabels.getRowVector(i);
			for (int j = 0; j < xi.getDimension(); j++) {
				if (xi.getEntry(j) == 1) {
					vLabels.setEntry(i, j);
				}
			}
		}
		return vLabels;
	}
	
	public List<List<PontoDoGrafico2D>> pontosAll(double xMin, double xMax, double yMin, double yMax, int breaksX, int breaksY, RealMatrix W) {
		List<List<PontoDoGrafico2D>> lista = new ArrayList<List<PontoDoGrafico2D>>();
		
		double xTemp = xMin;
		double yTemp = yMin;
		double comprimentoX = (xMax - xMin) / breaksX;
		double comprimentoY = (yMax - yMin) / breaksY;
		List<PontoDoGrafico2D> pontosC0 = new ArrayList<PontoDoGrafico2D>();
		List<PontoDoGrafico2D> pontosC1 = new ArrayList<PontoDoGrafico2D>();
		List<PontoDoGrafico2D> pontosC2 = new ArrayList<PontoDoGrafico2D>();
		for (int i = 0; i < breaksX; i++) {
			yTemp = yMin;
			for (int j = 0; j < breaksY; j++) {
				PontoDoGrafico2D p = new PontoDoGrafico2D(xTemp, yTemp);
				double[] vi = {1.0, xTemp, yTemp};
				RealVector pi = new ArrayRealVector(vi);
				int classe = classificar(pi, W);
				switch (classe) {
				case 0:
					pontosC0.add(p);
					break;
				case 1:
					pontosC1.add(p);
					break;
				case 2:
					pontosC2.add(p);
					break;
				default:
					break;
				}
				yTemp += comprimentoY;
			}
			xTemp += comprimentoX;
		}
		lista.add(pontosC0);
		lista.add(pontosC1);
		lista.add(pontosC2);
		return lista;
	}

}
