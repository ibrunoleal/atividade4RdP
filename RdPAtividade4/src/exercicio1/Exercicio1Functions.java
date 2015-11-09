package exercicio1;

import br.ufc.arida.bcl.rp20152.atv4.file.FileHandler;
import weka.core.matrix.Matrix;

public class Exercicio1Functions {
	
	private final String DATA_I_SAMPLES_LEARNING = "data/data-I-samples-learning.csv";
	
	private FileHandler fileHandler;
	
	private Matrix data_I_samples_learning;
	
	public Exercicio1Functions() {
		fileHandler = new FileHandler(DATA_I_SAMPLES_LEARNING, ";");
		data_I_samples_learning = new Matrix(fileHandler.getMatriz());
	}

	public Matrix get_Data_I_samples_learning() {
		return data_I_samples_learning;
	}
	
	public Matrix getMatrizPHI(Matrix m) {
		Matrix temp = new Matrix(m.getRowDimension(), m.getColumnDimension() + 1);
		for (int i = 0; i < m.getRowDimension(); i++) {
			for (int j = 0; j < m.getColumnDimension() + 1; j++) {
				if (j == 0) {
					temp.set(i, j, 1);
				} else {
					temp.set(i, j, m.get(i, j-1));
				}
			}
		}
		return temp;
	}
	
}
