package br.ufc.arida.bcl.rp20152.atv4.exercicios.ex3;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import br.ufc.arida.bcl.rp20152.atv4.graficos.GraficoDePontos;
import br.ufc.arida.bcl.rp20152.atv4.graficos.PontoDoGrafico;

public class Exercicio3 {

	public static void main(String[] args) {
		
		Exercicio3Functions f = new Exercicio3Functions();
		
		RealMatrix PHI_learning = f.getMatrizPHI(f.get_Data_I_samples_learning());
		RealVector t_learning = f.getData_I_labels_learning();
		
		Perceptron perceptron = new Perceptron();
		double threshold = 0.2;
		double lrate = 0.1;
		int epoch = 200;
		int[] t_int = new int[t_learning.getDimension()];
		for (int i = 0; i < t_learning.getDimension(); i++) {
			t_int[i] = (int)t_learning.getEntry(i);
		}
		perceptron.Train(PHI_learning.getData(), t_int, threshold, lrate, epoch);
		
		double[] w = perceptron.weights;
		RealVector w_v = new ArrayRealVector(w);
		System.out.println(w_v);
		
		List<PontoDoGrafico> boundaryPoints = f.getPontosDaReta(w_v, 1000, -18.0, 18.0, 0);
		
		List<PontoDoGrafico> pontosC1 = new ArrayList<PontoDoGrafico>();
		List<PontoDoGrafico> pontosC2 = new ArrayList<PontoDoGrafico>();
		for (int i = 0; i < PHI_learning.getRowDimension(); i++) {
			RealVector xi = PHI_learning.getRowVector(i);
			PontoDoGrafico pi = new PontoDoGrafico(xi.getEntry(1), xi.getEntry(2));
			int ti = (int)t_learning.getEntry(i);
			if (ti == 1) {
				pontosC1.add(pi);
			} else {
				pontosC2.add(pi);
			}
		}
		GraficoDePontos gp = new GraficoDePontos("", "");
		gp.adicionarSerie(pontosC1, "C1");
		gp.adicionarSerie(pontosC2, "C2");
		gp.adicionarSerie(boundaryPoints, "boundary");
		gp.exibirGrafico();

	}

}
