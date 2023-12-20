package NeuralNetworks.LossFunctions;

import NeuralNetworks.Matrix.Matrix;

public class LossFunction {
    public static double MeanSquaredLoss(double output, double target){
        return (target - output) * (target - output);
    }

    public static Matrix MeanSquaredLossGrad(double[] output, double[] target){
        Matrix ans = new Matrix(output.length, 1);
        for(int i = 0 ; i < output.length ; i++){
            ans.data[i][0] = 2*(output[i] - target[i]);
        }
        return ans;
    }

    public static Matrix BinaryCrossEntropyGrad(double[] output, double[] target){
        Matrix ans = new Matrix(output.length, 1);
        for(int i = 0 ; i < output.length ; i++){
            ans.data[i][0] = clippedBinaryCrossEntropyGrad(output[i], target[i]);
        }
        return ans;
    }

    public static double clippedBinaryCrossEntropyGrad(double predictedProbability, double trueLabel) {
        double epsilon = 1e-25; // Small value to avoid division by zero

        // Avoid numerical instability by clipping predicted probabilities
        predictedProbability = Math.max(epsilon, Math.min(1 - epsilon, predictedProbability));

        // Compute the gradient of binary cross-entropy loss
        double grad = -(trueLabel / predictedProbability - (1 - trueLabel) / (1 - predictedProbability));

        return grad;
    }
    



}
