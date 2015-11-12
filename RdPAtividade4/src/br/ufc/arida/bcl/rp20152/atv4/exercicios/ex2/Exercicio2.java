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

		RealVector m1 = f.calcularM(samples1);
		RealMatrix S1 = f.sW(m1, samples1);
		System.out.println(S1);
		
	}

}
