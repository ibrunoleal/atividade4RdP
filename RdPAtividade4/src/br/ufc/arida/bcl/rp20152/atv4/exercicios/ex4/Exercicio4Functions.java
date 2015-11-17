package br.ufc.arida.bcl.rp20152.atv4.exercicios.ex4;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import br.ufc.arida.bcl.rp20152.atv4.file.FileHandler;

public class Exercicio4Functions {
	
	private final String DATA_III_SAMPLES_LEARNING = "data/data-III-samples-learning.csv";
	private final String DATA_III_LABELS_LEARNING = "data/data-III-labels-learning.csv";
	
//	private final String DATA_III_SAMPLES_TESTING = "data/data-I-samples-testing.csv";
//	private final String DATA_III_LABELS_TESTING = "data/data-I-labels-testing.csv";
	
	private FileHandler fileHandler;
	
	private RealMatrix data_III_samples_learning;
	
	private RealMatrix data_III_labels_learning;
	
//	private RealMatrix data_III_samples_testing;
//	
//	private RealVector data_III_labels_testing;
	
	public Exercicio4Functions() {
		fileHandler = new FileHandler(DATA_III_SAMPLES_LEARNING, ";");
		data_III_samples_learning = new Array2DRowRealMatrix(fileHandler.getMatriz());
		fileHandler = new FileHandler(DATA_III_LABELS_LEARNING, ";");
		data_III_labels_learning = new Array2DRowRealMatrix(fileHandler.getMatriz());
	}

	public RealMatrix getData_III_samples_learning() {
		return data_III_samples_learning;
	}

	public RealMatrix getData_III_labels_learning() {
		return data_III_labels_learning;
	}
	
	public int contarElementosDaClasse(int k) {
		int cont = 0;
		for (int i = 0; i < data_III_labels_learning.getRowDimension(); i++) {
			int classificacao = (int)data_III_labels_learning.getEntry(i, k);
			if (classificacao == 1) {
				cont++;
			}
		}
		return cont;
	}
	
	public double piK(int k) {
		double nk = contarElementosDaClasse(k);
		return nk / data_III_labels_learning.getRowDimension();
	}
	
	public RealMatrix elementosDaClasseK(int k) {
		int n = contarElementosDaClasse(k);
		RealMatrix m = new Array2DRowRealMatrix(n,3);
		int cont = 0;
		for (int i = 0; i < data_III_labels_learning.getRowDimension(); i++) {
			int classificacao = (int)data_III_labels_learning.getEntry(i, k);
			if (classificacao == 1) {
				RealVector xi = data_III_samples_learning.getRowVector(i);
				m.setRowVector(cont, xi);
				cont++;
			}
		}
		return m;
	}
	


}
