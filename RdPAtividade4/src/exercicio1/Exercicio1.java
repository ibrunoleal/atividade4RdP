package exercicio1;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import br.ufc.arida.bcl.rp20152.atv4.graficos.GraficoDePontos;
import br.ufc.arida.bcl.rp20152.atv4.graficos.PontoDoGrafico;


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
		//System.out.println(yPreditosTesting);
		//System.out.println(labelsPreditosTesting);
		
		System.out.println("\n*******************************************************************");
		System.out.println("Exercício 1.1)");
		
		double erro = f.sumOfSquaresError(yPreditosTesting, t_testing);
		System.out.println("Sum of Squares Error: " + erro);
		
		System.out.println("\n*******************************************************************");
		System.out.println("Exercício 1.2)");
		
		List<PontoDoGrafico> pontosClasse1 = new ArrayList<PontoDoGrafico>();
		List<PontoDoGrafico> pontosClasse2 = new ArrayList<PontoDoGrafico>();
		for (int i = 0; i < f.getData_I_samples_testing().getRowDimension(); i++) {
			RealVector xi = f.getData_I_samples_testing().getRowVector(i);
			PontoDoGrafico p = new PontoDoGrafico(xi.getEntry(0), xi.getEntry(1));
			if (labelsPreditosTesting.getEntry(i) == 1) {
				pontosClasse1.add(p);
			} else {
				pontosClasse2.add(p);
			}
		}
		GraficoDePontos gp = new GraficoDePontos("Grafico", "");
		gp.adicionarSerie(pontosClasse1, "Classe 1");
		gp.adicionarSerie(pontosClasse2, "Classe 2");
		gp.exibirGrafico();
		
		System.out.println("\n*******************************************************************");
		System.out.println("Exercício 1.3)");
		
		double taxaDeSemelhanca = f.taxaDeSemelhanca(labelsPreditosTesting, t_testing);
		System.out.println("Taxa de acerto para o testing: " + taxaDeSemelhanca  + "%");
		
		double xmin = -18;
		double xmax = 18;
		int numbreaks = 1000;
		double xTemp = xmin;
		int cont = 0;
		while(cont < numbreaks) {
			
		}
		
	}

}
