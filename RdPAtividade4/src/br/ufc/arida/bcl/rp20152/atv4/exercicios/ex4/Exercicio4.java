package br.ufc.arida.bcl.rp20152.atv4.exercicios.ex4;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.math.plot.Plot3DPanel;

import br.ufc.arida.bcl.rp20152.atv4.entidades.Matriz;

public class Exercicio4 {

	public static void main(String[] args) {
		
		Exercicio4Functions f = new Exercicio4Functions();
		
		RealMatrix M0 = f.elementosDaClasseK(0, f.getData_III_samples_learning(), f.getData_III_labels_learning());
		RealMatrix M1 = f.elementosDaClasseK(1, f.getData_III_samples_learning(), f.getData_III_labels_learning());
		RealMatrix M2 = f.elementosDaClasseK(2, f.getData_III_samples_learning(), f.getData_III_labels_learning());
		
		double pi0 = f.piK(0, f.getData_III_samples_learning(), f.getData_III_labels_learning());
		double pi1 = f.piK(1, f.getData_III_samples_learning(), f.getData_III_labels_learning());
		double pi2 = f.piK(2, f.getData_III_samples_learning(), f.getData_III_labels_learning());
		System.out.println("pi0: " + pi0);
		System.out.println("pi1: " + pi1);
		System.out.println("pi2: " + pi2);
		System.out.println();
		
		RealVector u0 = f.mean(M0);
		RealVector u1 = f.mean(M1);
		RealVector u2 = f.mean(M2);
		System.out.println("u0: " + u0);
		System.out.println("u1: " + u1);
		System.out.println("u2: " + u2);
		System.out.println();
		
		RealMatrix Sigma = f.sigma(f.getData_III_samples_learning(), f.getData_III_labels_learning());
		System.out.println("Matriz Sigma:\n" + new Matriz(Sigma));
		
		RealVector w0 = f.w(M0, Sigma);
		RealVector w1 = f.w(M1, Sigma);
		RealVector w2 = f.w(M2, Sigma);
		List<RealVector> ws = new ArrayList<RealVector>();
		ws.add(w0);
		ws.add(w1);
		ws.add(w2);
		System.out.println("w0: " + w0);
		System.out.println("w1: " + w1);
		System.out.println("w2: " + w2);
		System.out.println();
		
		double w0_0 = f.w0(u0, Sigma, pi0);
		double w0_1 = f.w0(u1, Sigma, pi1);
		double w0_2 = f.w0(u2, Sigma, pi2);
		List<Double> w0s = new ArrayList<Double>();
		w0s.add(w0_0);
		w0s.add(w0_1);
		w0s.add(w0_2);
		System.out.println("w0_0: " + w0_0);
		System.out.println("w0_1: " + w0_1);
		System.out.println("w0_2: " + w0_2);
		System.out.println();

		RealVector testingPreditos = f.ysPreditos(f.getData_III_samples_testing(), ws, w0s);

		double taxa = f.taxaDeSemelhanca(testingPreditos, f.getData_III_labels_testing());
		System.out.println("taxa de acerto: " + taxa + "%");
		
		double[] x = f.getData_III_samples_learning().getColumnVector(0).toArray();
		double[] y = f.getData_III_samples_learning().getColumnVector(1).toArray();
		double[] z = f.getData_III_samples_learning().getColumnVector(2).toArray();
		Plot3DPanel panel = new Plot3DPanel();
		panel.addScatterPlot("grafico 3d teste", x, y, z);
		JFrame frame = new JFrame("testanto plot 3d");
		frame.setSize(1024, 768);
		frame.setContentPane(panel);
		frame.setVisible(true);
	}

}
