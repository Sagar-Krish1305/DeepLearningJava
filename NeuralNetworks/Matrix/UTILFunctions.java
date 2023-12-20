package NeuralNetworks.Matrix;
public class UTILFunctions{

    public static Matrix ArrayToMatrix(double[] d){
        Matrix ans = new Matrix(d.length, 1);
        for(int i = 0 ; i < d.length ; i++){
            ans.data[i][0] = d[i];
        }
        return ans;
    }


    public static Matrix ArrayToMatrix(double[][] d){
        Matrix ans = new Matrix(d.length, d[0].length);
        for(int i = 0 ; i < d.length ; i++){
            for(int j = 0 ; j < d[0].length ; j++){
                ans.data[i][j] = d[i][j];
            }
        }
        return ans;
    }

    
    // public static Matrix makeInputMatrix(double[] d){
    //     Matrix ans = new Matrix(d.length + 1, 1);
    //     ans.data[0][0] = 1;
    //     for(int i = 1 ; i <= d.length ; i++){
    //         ans.data[i][0] = d[i-1];
    //     }
    //     return ans;
    // }

    public static double[] outputAsArray(Matrix m){

        double[] d = new double[m.rows];
        for(int i = 0 ; i < m.rows ; i++){
            d[i] = m.data[i][0];
        }
        return d;
    }

    public static double summation(Matrix m){
        double d = 0;
        for(int i = 0 ; i < m.rows ; i++){
            d += m.data[i][0];
        }
        return d;
    }

}