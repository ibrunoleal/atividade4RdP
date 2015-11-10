package exercicio1;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;


public class Exercicio1 {

	public static void main(String[] args) {
	
		Exercicio1Functions f = new Exercicio1Functions();
		RealMatrix PHI = f.getMatrizPHI(f.get_Data_I_samples_learning());
		
		RealVector t = f.getData_I_labels_learning();
		
		RealVector w = f.wML(PHI, t);
		
		RealVector labelsPreditosLearning = new ArrayRealVector(PHI.getRowDimension());
		for (int i = 0; i < PHI.getRowDimension(); i++) {
			RealVector xi = PHI.getRowVector(i);
			double yi = f.yPredito(xi, w);
			int classificacao = f.classificarY(yi);
			labelsPreditosLearning.setEntry(i, classificacao);
		}

		RealMatrix PHI_testing = f.getMatrizPHI(f.getData_I_samples_testing());
		RealVector t_testing = f.getData_I_labels_testing();
		
		RealVector preditosTesting = new ArrayRealVector(PHI_testing.getRowDimension()); 
		RealVector labelsPreditosTesting = new ArrayRealVector(PHI_testing.getRowDimension());
		for (int i = 0; i < PHI_testing.getRowDimension(); i++) {
			RealVector xi = PHI_testing.getRowVector(i);
			double yi = f.yPredito(xi, w);
			preditosTesting.setEntry(i, yi);
			int classificacao = f.classificarY(yi);
			labelsPreditosTesting.setEntry(i, classificacao);
		}
		
		System.out.println(preditosTesting);
		System.out.println(labelsPreditosTesting);
		System.out.println(t_testing);
		System.out.println(f.taxaDeSemelhanca(labelsPreditosTesting, t_testing));
		
	}

}
