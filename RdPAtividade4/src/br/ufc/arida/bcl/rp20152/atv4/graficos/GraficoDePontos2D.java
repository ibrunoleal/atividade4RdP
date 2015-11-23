package br.ufc.arida.bcl.rp20152.atv4.graficos;

import java.util.List;

import javax.swing.JFrame;

import org.math.plot.Plot2DPanel;

@SuppressWarnings("serial")
public class GraficoDePontos2D extends Plot2DPanel{
	
	private String tituloDaAplicacao;
	
	public GraficoDePontos2D(String tituloDaAplicacao) {
		this.tituloDaAplicacao = tituloDaAplicacao;
	}

	public void adicionarPontos2D(String label, List<PontoDoGrafico2D> pontos2D) {
		double[] x = new double[pontos2D.size()];
		double[] y = new double[pontos2D.size()];
		for (int i = 0; i < pontos2D.size(); i++) {
			x[i] = pontos2D.get(i).getX();
			y[i] = pontos2D.get(i).getY();
		}
		addScatterPlot(label, x, y);
	}
	
	public void adicionarPontos2D(String label, double[] x, double[] y) {
		addScatterPlot(label, x, y);
	}
	
	public void exibirGrafico() {
		JFrame frame = new JFrame(tituloDaAplicacao);
		frame.setSize(1024, 768);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setContentPane(this);
		frame.setVisible(true);
	}
}
