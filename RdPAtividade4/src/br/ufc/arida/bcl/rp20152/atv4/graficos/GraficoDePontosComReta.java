package br.ufc.arida.bcl.rp20152.atv4.graficos;

import java.awt.Color;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

@SuppressWarnings("serial")
public class GraficoDePontosComReta extends JFrame {

	private CategoryPlot plot;
	
	private List<PontoDoGrafico> pontosDeScatterPlot;
	
	private List<PontoDoGrafico> pontosDeLinha;
	
	private String eixoX;
	
	private String eixoY;
	
	public GraficoDePontosComReta(String title, List<PontoDoGrafico> pontosDeScatterPlot, List<PontoDoGrafico> pontosDeLinha, String eixoX, String eixoY) {
		super(title);
		this.pontosDeScatterPlot = pontosDeScatterPlot;
		this.pontosDeLinha = pontosDeLinha;
		this.eixoX = eixoX;
		this.eixoY = eixoY;
		
		plot = new CategoryPlot();
		
		setDataSet();
		
		final JFreeChart grafico = createChart();
		final ChartPanel chartPanel = new ChartPanel(grafico);
		chartPanel.setPreferredSize(new java.awt.Dimension(1280, 720));
		setContentPane(chartPanel);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void setDataSet() {
		DefaultCategoryDataset pontosDataSet = new DefaultCategoryDataset();
		for (PontoDoGrafico pontoDoGrafico : pontosDeScatterPlot) {
			pontosDataSet.addValue(pontoDoGrafico.getY(), "H", String.valueOf(pontoDoGrafico.getX()));
		}
		
		DefaultCategoryDataset lineDataSet = new DefaultCategoryDataset();
		for (PontoDoGrafico pontoDoGrafico : pontosDeLinha) {
			lineDataSet.addValue(pontoDoGrafico.getY(), "p(x)", String.valueOf(pontoDoGrafico.getX()));
		}
		
		CategoryItemRenderer rendererPoints = new LineAndShapeRenderer(false, true);
		plot.setDataset(pontosDataSet);
        plot.setRenderer(rendererPoints);
        
        CategoryItemRenderer rendererLine = new LineAndShapeRenderer(true, true);
        plot.setDataset(1, lineDataSet);
        plot.setRenderer(1, rendererLine);
	}
	
	private JFreeChart createChart() {
		final JFreeChart chart = new JFreeChart(plot);;
		
		plot.setDomainAxis(new CategoryAxis(this.eixoX));
        plot.setRangeAxis(new NumberAxis(this.eixoY));
        plot.setOrientation(PlotOrientation.VERTICAL);
        plot.setRangeGridlinesVisible(true);
        plot.setDomainGridlinesVisible(true);
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.gray);
        plot.setRangeGridlinePaint(Color.gray);
		return chart;
	}

	public void exibirGrafico() {
		final GraficoDePontosComReta g = new GraficoDePontosComReta(getTitle(), pontosDeScatterPlot, pontosDeLinha, "x", "y");
        g.pack();
        RefineryUtilities.centerFrameOnScreen(g);
        g.setVisible(true);
	}
}
