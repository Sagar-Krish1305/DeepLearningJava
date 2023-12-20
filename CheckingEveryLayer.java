import java.util.Arrays;

import NeuralNetworks.Builder.NeuralNetwork;
import NeuralNetworks.Matrix.Matrix;
import NeuralNetworks.Matrix.UTILFunctions;

public class CheckingEveryLayer {
    public static void main(String[] args) {
        
        NeuralNetwork nn = new NeuralNetwork(2, 124, 0.4);

        // nn.addConvolutionLayer(10,10,1,1,5);
        nn.addFullyConnectedLayer(100, 10, 0.5, 123);
        while(true){
            Matrix m = Matrix.scalarMatrix(10, 10, 4);
            double[] input = UTILFunctions.outputAsArray(Matrix.flattenMatrix(new Matrix[]{m}, 10, 10));
            double[] output = nn.getOutput(input);
            nn.train(input, output);

            System.out.println(
                Arrays.toString(output)
            );
        }
    }
}
