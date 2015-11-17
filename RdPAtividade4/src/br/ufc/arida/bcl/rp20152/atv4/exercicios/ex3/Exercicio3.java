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
		
		perceptron.Train(PHI_learning.getData(), f.realVectorToIntVector(t_learning), threshold, lrate, epoch);
		
		double[] w_ = perceptron.weights;
		RealVector w = new ArrayRealVector(w_);
		System.out.println(w);
		
//		List<PontoDoGrafico> boundaryPoints = f.getPontosDaReta(w, 1000, -18.0, 18.0, 0);
//		List<PontoDoGrafico> pontosC1 = new ArrayList<PontoDoGrafico>();
//		List<PontoDoGrafico> pontosC2 = new ArrayList<PontoDoGrafico>();
//		for (int i = 0; i < PHI_learning.getRowDimension(); i++) {
//			RealVector xi = PHI_learning.getRowVector(i);
//			PontoDoGrafico pi = new PontoDoGrafico(xi.getEntry(1), xi.getEntry(2));
//			int ti = (int)t_learning.getEntry(i);
//			if (ti == -1) {
//				pontosC1.add(pi);
//			} else {
//				pontosC2.add(pi);
//			}
//		}
//		GraficoDePontos gp = new GraficoDePontos("", "");
//		gp.adicionarSerie(pontosC1, "C1");
//		gp.adicionarSerie(pontosC2, "C2");
//		gp.adicionarSerie(boundaryPoints, "boundary");
//		gp.exibirGrafico();
		
		/*
		 * Etapa de Teste
		 * Dados testing do exercício 1
		 */
		RealMatrix PHI_testing = f.getMatrizPHI(f.getData_I_samples_testing());
		RealVector t_testing = f.getData_I_labels_testing();
		
		RealVector yPreditos =  new ArrayRealVector(PHI_testing.getRowDimension());
		List<PontoDoGrafico> pontos1 = new ArrayList<PontoDoGrafico>();
		List<PontoDoGrafico> pontos2 = new ArrayList<PontoDoGrafico>();
		int numAcertos = 0;
		for (int i = 0; i < PHI_testing.getRowDimension(); i++) {
			RealVector xi = PHI_testing.getRowVector(i);
			int ypredito = f.yPredito(xi, w);
			yPreditos.setEntry(i, ypredito);
			PontoDoGrafico pi = new PontoDoGrafico(xi.getEntry(1), xi.getEntry(2));
			int ti = (int)t_testing.getEntry(i);
			if (ti == -1) {
				pontos1.add(pi);
			} else {
				pontos2.add(pi);
			}
			if (ypredito == ti) {
				numAcertos++;
			}
		}
		List<PontoDoGrafico> boundary = f.getPontosDaReta(w, 1000, -18.0, 18.0, 0);
		
		GraficoDePontos gp = new GraficoDePontos("", "");
		gp.adicionarSerie(pontos1, "C1");
		gp.adicionarSerie(pontos2, "C2");
		gp.adicionarSerie(boundary, "boundary");
		gp.exibirGrafico();
		
		double taxaDeAcerto = (numAcertos * 100.0) / yPreditos.getDimension();
		System.out.println("Taxa de acerto para os dados do exercício 1: " + taxaDeAcerto + "%");
		
		
		/*
		 * Com os dados do exercício 2
		 */

		/* Fase learning */
		double[] u1 = {2.5,9.0};
		RealVector means1 = new ArrayRealVector(u1);
		
		double[] u2 = {0.0, 2.0};
		RealVector means2 = new ArrayRealVector(u2);
		
		double[][] covariances = {{1.0,-1.0},{-1.0,2.0}};
		RealMatrix covarianceMatrix = new Array2DRowRealMatrix(covariances);
		
		RealMatrix samples1Learning = f.getMatrizPHI(f.getNormalSamples(1000, means1, covarianceMatrix));
		RealMatrix samples2Learning = f.getMatrizPHI(f.getNormalSamples(1000, means2, covarianceMatrix));
		RealMatrix samplesAllLearning = f.concatenarMatrizes(samples1Learning, samples2Learning);
		
		RealVector t1Learning = f.vetorComValorRepetido(samples1Learning.getRowDimension(), -1.0);
		RealVector t2Learning = f.vetorComValorRepetido(samples2Learning.getRowDimension(), 1.0);
		RealVector tAllLearning = t1Learning.append(t2Learning);
		
		Perceptron perceptron2 = new Perceptron();
		
		perceptron2.Train(samplesAllLearning.getData(), f.realVectorToIntVector(tAllLearning), threshold, lrate, epoch);
		double[] wAllLearning_ = perceptron2.weights;
		RealVector wAllLearning = new ArrayRealVector(wAllLearning_);
		System.out.println(wAllLearning);
		
//		List<PontoDoGrafico> pontosSamples1Learning = new ArrayList<PontoDoGrafico>();
//		List<PontoDoGrafico> pontosSamples2Learning = new ArrayList<PontoDoGrafico>();
//		for (int i = 0; i < samples1Learning.getRowDimension(); i++) {
//			RealVector xi = samples1Learning.getRowVector(i);
//			PontoDoGrafico pi = new PontoDoGrafico(xi.getEntry(1), xi.getEntry(2));
//			pontosSamples1Learning.add(pi);
//		}
//		for (int i = 0; i < samples2Learning.getRowDimension(); i++) {
//			RealVector xi = samples2Learning.getRowVector(i);
//			PontoDoGrafico pi = new PontoDoGrafico(xi.getEntry(1), xi.getEntry(2));
//			pontosSamples2Learning.add(pi);
//		}
//		
//		List<PontoDoGrafico> boundaryPointsLearning = f.getPontosDaReta(wAllLearning, 1000, -3.5, 6.0, 0);
//		GraficoDePontos gpLearning = new GraficoDePontos("", "");
//		gpLearning.adicionarSerie(pontosSamples1Learning, "C1");
//		gpLearning.adicionarSerie(pontosSamples2Learning, "C2");
//		gpLearning.adicionarSerie(boundaryPointsLearning, "boundary");
//		gpLearning.exibirGrafico();
		
		/* Fase testing */
		RealMatrix samples1Testing = f.getMatrizPHI(f.getNormalSamples(1000, means1, covarianceMatrix));
		RealMatrix samples2Testing = f.getMatrizPHI(f.getNormalSamples(1000, means2, covarianceMatrix));
		
		int acertosTesting = 0;
		List<PontoDoGrafico> pontos1Testing = new ArrayList<PontoDoGrafico>();
		RealVector y1PreditosTesting = new ArrayRealVector(samples1Testing.getRowDimension());
		for (int i = 0; i < samples1Testing.getRowDimension(); i++) {
			RealVector xi = samples1Testing.getRowVector(i);
			int yiPredito = f.yPredito(xi, wAllLearning);
			y1PreditosTesting.setEntry(i, yiPredito);
			if (yiPredito == -1) {
				acertosTesting++;
			}
			PontoDoGrafico pi = new PontoDoGrafico(xi.getEntry(1), xi.getEntry(2));
			pontos1Testing.add(pi);
		}
		List<PontoDoGrafico> pontos2Testing = new ArrayList<PontoDoGrafico>();
		RealVector y2PreditosTesting = new ArrayRealVector(samples2Testing.getRowDimension());
		for (int i = 0; i < samples2Testing.getRowDimension(); i++) {
			RealVector xi = samples2Testing.getRowVector(i);
			int yiPredito = f.yPredito(xi, wAllLearning);
			y2PreditosTesting.setEntry(i, yiPredito);
			if (yiPredito == 1) {
				acertosTesting++;
			}
			PontoDoGrafico pi = new PontoDoGrafico(xi.getEntry(1), xi.getEntry(2));
			pontos2Testing.add(pi);
		}
		List<PontoDoGrafico> boundaryPointsTesting = f.getPontosDaReta(wAllLearning, 1000, -3.5, 6.0, 0);
		GraficoDePontos gpLearning = new GraficoDePontos("", "");
		gpLearning.adicionarSerie(pontos1Testing, "C1");
		gpLearning.adicionarSerie(pontos2Testing, "C2");
		gpLearning.adicionarSerie(boundaryPointsTesting, "boundary");
		gpLearning.exibirGrafico();
		
		double n = y1PreditosTesting.getDimension() + y2PreditosTesting.getDimension();
		double taxaDeAcerto2 = (acertosTesting * 100.0) / n;
		System.out.println("Taxa de acerto para os dados do exercício 2: " + taxaDeAcerto2 + "%");
		
	}
}
