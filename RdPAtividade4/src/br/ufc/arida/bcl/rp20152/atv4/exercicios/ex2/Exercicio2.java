package br.ufc.arida.bcl.rp20152.atv4.exercicios.ex2;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import br.ufc.arida.bcl.rp20152.atv4.graficos.GraficoDePontos;
import br.ufc.arida.bcl.rp20152.atv4.graficos.PontoDoGrafico;

public class Exercicio2 {

	public static void main(String[] args) {
		
		Exercicio2Functions f = new Exercicio2Functions();
		
		double[] u1 = {2.5,9.0};
		RealVector means1 = new ArrayRealVector(u1);
		
		double[] u2 = {0.0, 2.0};
		RealVector means2 = new ArrayRealVector(u2);
		
		double[][] covariances = {{1.0,-1.0},{-1.0,2.0}};
		RealMatrix covarianceMatrix = new Array2DRowRealMatrix(covariances);
		
		RealMatrix samples1 = f.getNormalSamples(1000, means1, covarianceMatrix);
		RealMatrix samples2 = f.getNormalSamples(1000, means2, covarianceMatrix);
		
		List<PontoDoGrafico> pontos1 = new ArrayList<PontoDoGrafico>();
		List<PontoDoGrafico> pontos2 = new ArrayList<PontoDoGrafico>();
		for (int i = 0; i < samples1.getRowDimension(); i++) {
			PontoDoGrafico p1 = new PontoDoGrafico(samples1.getEntry(i, 0), samples1.getEntry(i, 1));
			pontos1.add(p1);
			
			PontoDoGrafico p2 = new PontoDoGrafico(samples2.getEntry(i, 0), samples2.getEntry(i, 1));
			pontos2.add(p2);
		}
		
		GraficoDePontos g = new GraficoDePontos("", "");
		g.adicionarSerie(pontos1, "C1");
		g.adicionarSerie(pontos2, "C2");
		g.exibirGrafico();

		RealVector w = f.calcularW(samples1, samples2);
		System.out.println("w: " + w);
		
		RealVector yPreditos1 = new ArrayRealVector(samples1.getRowDimension());
		for (int i = 0; i < samples1.getRowDimension(); i++) {
			RealVector xi = samples1.getRowVector(i);
			double yi = f.yPredito(xi, w);
			yPreditos1.setEntry(i, yi);
		}
		
		RealVector yPreditos2 = new ArrayRealVector(samples2.getRowDimension());
		for (int i = 0; i < samples2.getRowDimension(); i++) {
			RealVector xi = samples2.getRowVector(i);
			double yi = f.yPredito(xi, w);
			yPreditos2.setEntry(i, yi);
		}
		
		System.out.println("valores preditos para C1: " + yPreditos1);
		System.out.println("valores preditos para C2: " + yPreditos2);
		
		double w0 = f.calcular_w0(w, samples1, samples2);
		double y0 = -1.0 * w0;
		System.out.println("y0 (valor de decisao para classificacao): " + y0);
		
		RealVector yPreditos1Classificados = new ArrayRealVector(yPreditos1.getDimension());
		for (int i = 0; i < yPreditos1.getDimension(); i++) {
			double yi = yPreditos1.getEntry(i);
			int classificacao = f.classificar(yi, w, samples1, samples2);
			yPreditos1Classificados.setEntry(i, classificacao);
		}
		//System.out.println(yPreditos1Classificados);
		
		RealVector yPreditos2Classificados = new ArrayRealVector(yPreditos2.getDimension());
		for (int i = 0; i < yPreditos2.getDimension(); i++) {
			double yi = yPreditos2.getEntry(i);
			int classificacao = f.classificar(yi, w, samples1, samples2);
			yPreditos2Classificados.setEntry(i, classificacao);
		}
		//System.out.println(yPreditos2Classificados);
		
		List<PontoDoGrafico> yPreditos1Pontos = new ArrayList<PontoDoGrafico>();
		for (int i = 0; i < yPreditos1.getDimension(); i++) {
			PontoDoGrafico p = new PontoDoGrafico(i, yPreditos1.getEntry(i));
			yPreditos1Pontos.add(p);
		}
		List<PontoDoGrafico> yPreditos2Pontos = new ArrayList<PontoDoGrafico>();
		for (int i = 0; i < yPreditos2.getDimension(); i++) {
			PontoDoGrafico p = new PontoDoGrafico(i, yPreditos2.getEntry(i));
			yPreditos2Pontos.add(p);
		}
		
		GraficoDePontos gPreditos = new GraficoDePontos("", "");
		gPreditos.adicionarSerie(yPreditos1Pontos, "C1");
		gPreditos.adicionarSerie(yPreditos2Pontos, "C2");
		gPreditos.exibirGrafico();
		
		double[] wtemp = {0.0};
		RealVector w_ = new ArrayRealVector(wtemp);
		w_ = w_.append(w);
		System.out.println();
		List<PontoDoGrafico> boundaryPoints = f.getPontosDaReta(w_, 1000, -3.5, 6.0, y0);
		GraficoDePontos gBoundary = new GraficoDePontos("", "");
		gBoundary.adicionarSerie(pontos1, "C1");
		gBoundary.adicionarSerie(pontos2, "C2");
		gBoundary.adicionarSerie(boundaryPoints, "Boundary");
		gBoundary.exibirGrafico();
		
	}

}
