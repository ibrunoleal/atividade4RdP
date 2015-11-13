package br.ufc.arida.bcl.rp20152.atv4.exercicios.ex3;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import br.ufc.arida.bcl.rp20152.atv4.graficos.GraficoDePontos;
import br.ufc.arida.bcl.rp20152.atv4.graficos.PontoDoGrafico;

public class Exercicio3 {

	public static void main(String[] args) {
		
		Exercicio3Functions f = new Exercicio3Functions();
		
		RealMatrix PHI_learning = f.getMatrizPHI(f.get_Data_I_samples_learning());
		RealVector t_learning = f.getData_I_labels_learning();
		
		Perceptron perceptron = new Perceptron(PHI_learning, t_learning);
		perceptron.executar();
		
		RealVector w = perceptron.getW();
		System.out.println(w);
		double c = perceptron.getC();
		
		List<PontoDoGrafico> boundaryPoints = f.getPontosDaReta(w, 1000, -18.0, 18.0, c);
		
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
