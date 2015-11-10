package exercicio1;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;


public class Exercicio1 {

	public static void main(String[] args) {
	
		Exercicio1Functions f = new Exercicio1Functions();
		
		RealMatrix PHI_learning = f.getMatrizPHI(f.get_Data_I_samples_learning());
		
		RealVector t_learning = f.getData_I_labels_learning();
		
		RealVector w = f.wML(PHI_learning, t_learning);
		System.out.println("w: " + w);
		
		RealVector labelsPreditosLearning = new ArrayRealVector(PHI_learning.getRowDimension());
		for (int i = 0; i < PHI_learning.getRowDimension(); i++) {
			RealVector xi = PHI_learning.getRowVector(i);
			double yi = f.yPredito(xi, w);
			int classificacao = f.classificarY(yi);
			labelsPreditosLearning.setEntry(i, classificacao);
		}

		RealMatrix PHI_testing = f.getMatrizPHI(f.getData_I_samples_testing());
		RealVector t_testing = f.getData_I_labels_testing();
		
		RealVector yPreditosTesting = new ArrayRealVector(PHI_testing.getRowDimension());
		RealVector labelsPreditosTesting = new ArrayRealVector(PHI_testing.getRowDimension());
		for (int i = 0; i < PHI_testing.getRowDimension(); i++) {
			RealVector xi = PHI_testing.getRowVector(i);
			double yi = f.yPredito(xi, w);
			yPreditosTesting.setEntry(i, yi);
			int classificacao = f.classificarY(yi);
			labelsPreditosTesting.setEntry(i, classificacao);
		}
		
		System.out.println("Exercício 1.1)");
		double erro = f.sumOfSquaresError(yPreditosTesting, t_testing);
		System.out.println("Sum of Squares Error: " + erro);
		
		System.out.println("Exercício 1.3)");
		System.out.println("Taxa de acerto para o testing: " + f.taxaDeSemelhanca(labelsPreditosTesting, t_testing) + "%");
		
		
	}

}
