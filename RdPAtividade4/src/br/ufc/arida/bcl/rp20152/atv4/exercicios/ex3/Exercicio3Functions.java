package br.ufc.arida.bcl.rp20152.atv4.exercicios.ex3;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import br.ufc.arida.bcl.rp20152.atv4.file.FileHandler;

public class Exercicio3Functions {
	
	private final String DATA_I_SAMPLES_LEARNING = "data/data-I-samples-learning.csv";
	private final String DATA_I_LABELS_LEARNING = "data/data-I-labels-learning.csv";
	
	private final String DATA_I_SAMPLES_TESTING = "data/data-I-samples-testing.csv";
	private final String DATA_I_LABELS_TESTING = "data/data-I-labels-testing.csv";
	
	private FileHandler fileHandler;
	
	private RealMatrix data_I_samples_learning;
	
	private RealVector data_I_labels_learning;
	
	private RealMatrix data_I_samples_testing;
	
	private RealVector data_I_labels_testing;
	
	public Exercicio3Functions() {
		fileHandler = new FileHandler(DATA_I_SAMPLES_LEARNING, ";");
		data_I_samples_learning = new Array2DRowRealMatrix(fileHandler.getMatriz());
		fileHandler = new FileHandler(DATA_I_LABELS_LEARNING, ";");
		data_I_labels_learning = new ArrayRealVector(fileHandler.getVetor(0));
		
		fileHandler = new FileHandler(DATA_I_SAMPLES_TESTING, ";");
		data_I_samples_testing = new Array2DRowRealMatrix(fileHandler.getMatriz());
		fileHandler = new FileHandler(DATA_I_LABELS_TESTING, ";");
		data_I_labels_testing = new ArrayRealVector(fileHandler.getVetor(0));
	}

	public RealMatrix get_Data_I_samples_learning() {
		return data_I_samples_learning;
	}
	
	public RealVector getData_I_labels_learning() {
		return data_I_labels_learning;
	}

	public RealMatrix getData_I_samples_testing() {
		return data_I_samples_testing;
	}

	public RealVector getData_I_labels_testing() {
		return data_I_labels_testing;
	}
	
}
