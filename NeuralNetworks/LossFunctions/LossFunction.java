package NeuralNetworks.LossFunctions;

import NeuralNetworks.Matrix.Matrix;

public class LossFunction {
    public static double MeanSquaredLoss(double output, double target){
        return (target - output) * (target - output);
    }

    public static double MeanSquaredLossGrad(double output, double target){
        return 2 * (target - output);
    }

    public static Matrix BinaryCrossEntropyGrad(double[] output, double[] target){
        Matrix ans = new Matrix(output.length, 1);
        for(int i = 0 ; i < output.length ; i++){
            ans.data[i][0] = ((1 - target[i])/(1 - output[i]) - target[i]/ output[i]);
        }
        return ans;
    }
    



}
