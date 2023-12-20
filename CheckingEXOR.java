
import java.util.Random;

import NeuralNetworks.Builder.NeuralNetwork;

public class CheckingEXOR {
    static double leak = 0.01;
    public static double sigmoidFunction(double d){
        return 1.0/(1 + Math.exp(-d));
    }

    public static double sigmoidFunctionDiff(double d){
        return sigmoidFunction(d) * (1 - sigmoidFunction(d));
    }

    public static void main(String[] args) {
        
        NeuralNetwork nn = new NeuralNetwork(2, 324, 0.3);
        nn.addFullyConnectedLayer(2, 3, 0.4, 129);
        nn.addActivationLayer(2);
        nn.addFullyConnectedLayer(3, 1, 0.5, 125);
        nn.addActivationLayer(2);

        double[][] inputs = {
            {1,1}, {1,0}, {0,1}, {0,0}
        };

        double[][] outputs = {{0}, {1}, {1}, {0}};
        Random r = new Random(123421);
        double i1 = 0, i2 = 0, o = 0;
        for(; true ; ){

            for(int j = 0 ; j < 1000000 ; j++){
                int a = r.nextInt(4);
                double[] ans = nn.getOutput(inputs[a]);

                i1 = inputs[a][0];
                i2 = inputs[a][1];
                o = ans[0];

                nn.train(inputs[a], outputs[a]);
            }


            System.out.println("INPUT 1 : " + i1);
            System.out.println("INPUT 2 : " + i2);
            System.out.println("Output : " + o);



        }
    }
}
