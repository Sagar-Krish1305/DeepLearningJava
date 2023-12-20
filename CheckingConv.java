import NeuralNetworks.Matrix.Matrix;

public class CheckingConv {
    public static void main(String[] args) {
        
        Matrix m = new Matrix(3, 8, new double[][]{
            {1, 2, 3, 1, 2, 4, 3, 4},
            {4, 5, 6, 1, 4, 4, 22, 45},
            {7, 8, 9, 1, 2, 43, 3, 64}
        });
        
        Matrix m1 = new Matrix(2, 2, new double[][]{
            {1, 1},
            {1, 1}
        });

        
        Matrix c = m.correlate(m1);

        c.printMatrix();


    }
}
