package br.ufc.arida.bcl.rp20152.atv4.exercicios.ex4;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;

public class Exercicio4 {

	public static void main(String[] args) {
		
		Exercicio4Functions f = new Exercicio4Functions();
		
		System.out.println("pi_0; " + f.piK(0));
		System.out.println("pi_1; " + f.piK(1));
		System.out.println("pi_2; " + f.piK(2));
		
		Array2DRowRealMatrix M0 = (Array2DRowRealMatrix) f.elementosDaClasseK(0);
		Array2DRowRealMatrix M1 = (Array2DRowRealMatrix) f.elementosDaClasseK(1);
		Array2DRowRealMatrix M2 = (Array2DRowRealMatrix) f.elementosDaClasseK(2);
		
		
	}

}
