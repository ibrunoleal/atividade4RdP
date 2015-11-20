package br.ufc.arida.bcl.rp20152.atv4.exercicios.ex6;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.util.MathArrays;

import com.orsoncharts.Range;

import br.ufc.arida.bcl.rp20152.atv4.entidades.Matriz;

public class Exercicio6 {

	public static void main(String[] args) {
		
		Exercicio6Functions f = new Exercicio6Functions();
		
		RealMatrix C0 = f.elementosDaClasseK(0, f.getData_iris_samples(), f.getData_iris_labels());
		RealMatrix C1 = f.elementosDaClasseK(1, f.getData_iris_samples(), f.getData_iris_labels());
		RealMatrix C2 = f.elementosDaClasseK(2, f.getData_iris_samples(), f.getData_iris_labels());
		
		/* Exercício 6.1 */
		System.out.println("Exercício 6.1)\n");
		
		RealVector atributo0 = f.getData_iris_samples().getColumnVector(0);
		RealVector atributo1 = f.getData_iris_samples().getColumnVector(1);
		RealVector atributo2 = f.getData_iris_samples().getColumnVector(2);
		RealVector atributo3 = f.getData_iris_samples().getColumnVector(3);
		
		double rangeMin0 = StatUtils.min(atributo0.toArray());
		double rangeMax0 = StatUtils.max(atributo0.toArray());
		double range0 = new Range(rangeMin0, rangeMax0).getLength();
		double rangeMin1 = StatUtils.min(atributo1.toArray());
		double rangeMax1 = StatUtils.max(atributo1.toArray());
		double range1 = new Range(rangeMin1, rangeMax1).getLength();
		double rangeMin2 = StatUtils.min(atributo2.toArray());
		double rangeMax2 = StatUtils.max(atributo2.toArray());
		double range2 = new Range(rangeMin2, rangeMax2).getLength();
		double rangeMin3 = StatUtils.min(atributo3.toArray());
		double rangeMax3 = StatUtils.max(atributo3.toArray());
		double range3 = new Range(rangeMin3, rangeMax3).getLength();
//		System.out.println("range do atributo 0: " + range0);
//		System.out.println("range do atributo 1: " + range1);
//		System.out.println("range do atributo 2: " + range2);
//		System.out.println("range do atributo 3: " + range3);

		
		double variance0 = StatUtils.variance(atributo0.toArray());
		double variance1 = StatUtils.variance(atributo1.toArray());
		double variance2 = StatUtils.variance(atributo2.toArray());
		double variance3 = StatUtils.variance(atributo3.toArray());
		System.out.println();
//		System.out.println("variancia do atributo 0: " + variance0);
//		System.out.println("variancia do atributo 1: " + variance1);
//		System.out.println("variancia do atributo 2: " + variance2);
//		System.out.println("variancia do atributo 3: " + variance3);
		
		StandardDeviation standardDeviation = new StandardDeviation();
		double sd0 = standardDeviation.evaluate(atributo0.toArray());
		double sd1 = standardDeviation.evaluate(atributo1.toArray());
		double sd2 = standardDeviation.evaluate(atributo2.toArray());
		double sd3 = standardDeviation.evaluate(atributo3.toArray());
		System.out.println();
//		System.out.println("desvio padrao do atributo 0: " + sd0);
//		System.out.println("desvio padrao do atributo 1: " + sd1);
//		System.out.println("desvio padrao do atributo 2: " + sd2);
//		System.out.println("desvio padrao do atributo 3: " + sd3);
		
		/* Exercicio 6.2 */
		RealMatrix X = f.computarMatrizX(f.getData_iris_samples());
		//System.out.println("Matriz X: \n" + new Matriz(X));
		
		RealMatrix S = f.computarMatrizS(f.getData_iris_samples());
		System.out.println("Matriz S: \n" + new Matriz(S));
		
		RealMatrix A = f.matrizDeAutoValores(S);
		System.out.println("Matriz de auto valores: \n" + new Matriz(A));
		RealMatrix P = f.matrizDeAutoVetores(S);
		System.out.println("Matriz de auto vetores: \n" + new Matriz(P));

		RealMatrix Ptil = f.matrizPTil(P);
		System.out.println("Matriz P_til: \n" + new Matriz(Ptil));
		
	}

}
