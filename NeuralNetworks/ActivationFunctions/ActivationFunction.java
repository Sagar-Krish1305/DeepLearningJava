package NeuralNetworks.ActivationFunctions;

public class ActivationFunction {
    
    public static double SigmoidFunction(double d){
        return 1 / (1 + Math.exp(-d));
    }

    public static double SigmoidFunctionDerivative(double d){
        return SigmoidFunction(d) * (1 - SigmoidFunction(d));
    }

    public static double LeakyReLU(double leak, double d){
        return Math.max(leak*d, d);
    }

    public static double LeakyReLUDerivative(double leak, double d){
        return Math.max(leak, 1);
    }

}
