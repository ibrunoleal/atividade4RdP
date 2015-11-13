package br.ufc.arida.bcl.rp20152.atv4.exercicios.ex3;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class Exercicio3 {

	public static void main(String[] args) {
		
		Exercicio3Functions f = new Exercicio3Functions();
		
		RealMatrix PHI_learning = f.getMatrizPHI(f.get_Data_I_samples_learning());
		RealVector t_learning = f.getData_I_labels_learning();
		
		Perceptron perceptron = new Perceptron(PHI_learning, t_learning);
		perceptron.executar();
		
		RealVector w = perceptron.getW();
		System.out.println("w: " + w);
		

	}

}
