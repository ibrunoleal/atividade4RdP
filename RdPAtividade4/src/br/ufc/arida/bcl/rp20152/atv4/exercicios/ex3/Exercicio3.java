package br.ufc.arida.bcl.rp20152.atv4.exercicios.ex3;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
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
		
		/*
		 * Fase de Treinamento
		 * Dados Learning do exercício 1
		 */
		Perceptron perceptron = new Perceptron();
		double threshold = 0.2;
		double lrate = 0.1;
		int epoch = 200;
		int[] t_int = new int[t_learning.getDimension()];
		for (int i = 0; i < t_learning.getDimension(); i++) {
			t_int[i] = (int)t_learning.getEntry(i);
		}
		perceptron.Train(PHI_learning.getData(), t_int, threshold, lrate, epoch);
		
		double[] w_ = perceptron.weights;
		RealVector w = new ArrayRealVector(w_);
		System.out.println(w);
		
		List<PontoDoGrafico> boundaryPoints = f.getPontosDaReta(w, 1000, -18.0, 18.0, 0);
		
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
		
		/*
		 * Etapa de Teste
		 * Dados testing do exercício 1
		 */
		RealMatrix PHI_testing = f.getMatrizPHI(f.getData_I_samples_testing());
		RealVector t_testing = f.getData_I_labels_testing();
		
		RealVector yPreditos =  new ArrayRealVector(PHI_testing.getRowDimension());
		for (int i = 0; i < PHI_testing.getRowDimension(); i++) {
			RealVector xi = PHI_testing.getRowVector(i);
			double ypredito = f.yPredito(xi, w);
			yPreditos.setEntry(i, ypredito);
		}
		
		int numAcertos = 0;
		for (int i = 0; i < yPreditos.getDimension(); i++) {
			int ti = (int)t_testing.getEntry(i);
			if ((int)yPreditos.getEntry(i) == ti) {
				numAcertos++;
			}
		}
		double taxaDeAcerto = (numAcertos * 100.0) / yPreditos.getDimension();
		System.out.println("Taxa de acerto para os dados do exercício 1: " + taxaDeAcerto + "%");
		
		
		/*
		 * Com os dados do exercício 2
		 */

		double[] u1 = {2.5,9.0};
		RealVector means1 = new ArrayRealVector(u1);
		
		double[] u2 = {0.0, 2.0};
		RealVector means2 = new ArrayRealVector(u2);
		
		double[][] covariances = {{1.0,-1.0},{-1.0,2.0}};
		RealMatrix covarianceMatrix = new Array2DRowRealMatrix(covariances);
		
		RealMatrix samples1Learning = f.getMatrizPHI(f.getNormalSamples(1000, means1, covarianceMatrix));
		RealMatrix samples2Learning = f.getMatrizPHI(f.getNormalSamples(1000, means2, covarianceMatrix));
		
		RealMatrix samplesAllLearning = new Array2DRowRealMatrix((samples1Learning.getRowDimension() + samples2Learning.getRowDimension()), samples1Learning.getColumnDimension());
		RealVector t_all_learning = new ArrayRealVector(samples1Learning.getRowDimension() + samples2Learning.getRowDimension());
		int linha = 0;
		for (int i = 0; i < samples1Learning.getRowDimension(); i++) {
			RealVector xi = samples1Learning.getRowVector(i);
			samplesAllLearning.setRowVector(linha, xi);
			t_all_learning.setEntry(linha, -1);
			linha++;
		}
		for (int i = 0; i < samples2Learning.getRowDimension(); i++) {
			RealVector xi = samples2Learning.getRowVector(i);
			samplesAllLearning.setRowVector(linha, xi);
			t_all_learning.setEntry(linha, 1);
			linha++;
		}
		
		int[] t_all_learning_int = new int[t_all_learning.getDimension()];
		for (int i = 0; i < t_all_learning.getDimension(); i++) {
			t_all_learning_int[i] = (int)t_all_learning.getEntry(i);
		}
		perceptron.Train(samplesAllLearning.getData(), t_all_learning_int, threshold, lrate, epoch);
		double[] w_all_learning_ = perceptron.weights;
		RealVector w_all_learning = new ArrayRealVector(w_all_learning_);
		System.out.println(w_all_learning);
		
	}
}
