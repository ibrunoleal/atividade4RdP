package br.ufc.arida.bcl.rp20152.atv4.graficos;

public class PontoDoGrafico3D extends PontoDoGrafico2D {

	private double z;
	
	public PontoDoGrafico3D(double x, double y, double z) {
		super(x, y);
		this.z = z;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public String toString() {
		return "(" + getX() + ", " + getY() + ", " + z + ")";
	}
	
}
