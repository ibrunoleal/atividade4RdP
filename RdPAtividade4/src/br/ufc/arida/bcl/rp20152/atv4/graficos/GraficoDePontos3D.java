package br.ufc.arida.bcl.rp20152.atv4.graficos;

import java.util.List;

import javax.swing.JFrame;

import org.math.plot.Plot3DPanel;

@SuppressWarnings("serial")
public class GraficoDePontos3D extends Plot3DPanel {
	
	private String tituloDaAplicacao;
	
	public GraficoDePontos3D(String tituloDaAplicacao) {
		this.tituloDaAplicacao = tituloDaAplicacao;
	}
	
	public void adicionarPontos3D(String label, List<PontoDoGrafico3D> pontos3D) {
		double[] x = new double[pontos3D.size()];
		double[] y = new double[pontos3D.size()];
		double[] z = new double[pontos3D.size()];
		for (int i = 0; i < pontos3D.size(); i++) {
			x[i] = pontos3D.get(i).getX();
			y[i] = pontos3D.get(i).getY();
			z[i] = pontos3D.get(i).getZ();
		}
		addScatterPlot(label, x, y, z);
	}
	
	public void adicionarPontos3D(String label, double[] x, double[] y, double[] z) {
		addScatterPlot(label, x, y, z);
	}

	public void exibirGrafico() {
		JFrame frame = new JFrame(tituloDaAplicacao);
		frame.setSize(1024, 768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(this);
		frame.setVisible(true);
	}
	
}