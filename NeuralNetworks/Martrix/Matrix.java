package NeuralNetworks.Martrix;
import java.util.Random;

public class Matrix{
    public int rows;
    public int cols;
    public double data[][];
    public Matrix(int rows, int cols, double data[][]){
        this.rows = rows;
        this.cols = cols;
        this.data = data;
    }

    public Matrix(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        data = new double[rows][cols];
    }

    public Matrix add(Matrix m2) throws Exception{
        if(rows != m2.rows || cols != m2.cols){
            throw new Exception("OPERATION : ADDITION \n EXCEPTION : Matrices have different Dimensions.");
        }
        double[][] newData = new double[rows][cols];
        for(int i = 0 ; i < rows ; i++){
            for(int j = 0 ; j < cols ; j++){
                newData[i][j] = data[i][j] + m2.data[i][j];
            }
        }

        return new Matrix(rows, cols, newData);
    }

    public Matrix mult(double a){
        double newData[][] = new double[rows][cols];
        for(int i = 0 ; i < rows ; i++){
            for(int j = 0 ; j < cols ; j++){
                newData[i][j] = data[i][j] * a;
            }
        }
        return new Matrix(rows, cols, newData);
    }

    public Matrix mult(Matrix m1) throws Exception{
        if(cols != m1.rows){
            throw new Exception("OPERATION : MULTIPLICATION \n EXCEPTION : Matrices have different Dimensions.");
        }

        double newData[][] = new double[rows][m1.cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < m1.cols; j++) {
                newData[i][j] = 0;
                for (int k = 0; k < cols; k++) {
                    newData[i][j] += data[i][k] * m1.data[k][j];
                }
            }
        }
        return new Matrix(rows, m1.cols, newData);
    }

    public Matrix transpose(){
        Matrix ans = new Matrix(cols, rows);
        for(int i = 0 ; i < cols ; i++){
            for(int j = 0 ; j < rows ; j++){
                ans.data[i][j] = data[j][i];
            }
        }
        return ans;
    }

    public void randomizeMatrix(long SEED){
        Random r = new Random(SEED);
        for(int i = 0 ; i < rows ; i++){
            for(int j = 0 ; j < cols ; j++){
                data[i][j] = r.nextGaussian();
            }
        }
    }


    public static Matrix scalarMatrix(int rows, int cols, double scalar){
        Matrix m = new Matrix(rows, cols);
        for(int i = 0 ; i < rows ; i++){
            for(int j = 0 ; j < cols ; j++){
                m.data[i][j] = scalar;
            }
        }

        return m;
    }

    public void printDimensions(String s){
        System.out.println(s);
        System.out.println("ROWS " + rows);
        System.out.println("COLS " + cols);
        System.out.println();
    }

    public Matrix Dot(Matrix m1) throws Exception{
        
        if(m1.rows != rows){
            throw new Exception("OPERATION : INNER PRODUCT \n BOTH VECTORS HAVE DIFFERENT DIMENSIONS");
        }
            Matrix result = new Matrix(rows, cols);

            for(int i = 0 ; i < rows ; i++){
                for(int j = 0 ; j < cols ; j++){
                    result.data[i][j] = data[i][j] * m1.data[i][j];
                }
            } 

            return result;

    }

    public Matrix conv(Matrix m){
        
        Matrix ans = new Matrix(rows - m.rows + 1, cols - m.cols + 1);
        for(int i = 0 ; i <= rows - m.rows ; i++){
            for(int j = 0 ; j <= cols - m.cols ; j++){
                double d = 0;
                for(int k = 0 ; k < m.rows ; k++){
                    for(int w = 0 ; w < m.cols ; w++){
                        d += data[i + k][j + w]*m.data[k][w];
                    }
                }
                ans.data[i][j] = d;
            }
        }

        return ans;

    }

    public void printMatrix(){
        for(int i = 0 ; i < rows ; i++){
            for(int j = 0 ; j < cols ; j++){
                System.out.print(data[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    
}

