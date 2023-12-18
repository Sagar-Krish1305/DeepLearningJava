package NeuralNetworks.Builder;

import NeuralNetworks.ActivationFunctions.ActivationFunction;
import NeuralNetworks.Layers.FullyConnectedLayer;
import NeuralNetworks.Layers.Layer;
import NeuralNetworks.Martrix.Matrix;
import NeuralNetworks.Martrix.UTILFunctions;

import java.util.ArrayList;


public class NeuralNetwork {
    ArrayList<Layer> _layers;

    int inputsSize;
    int outputsSize;
    double leak;

    public NeuralNetwork(){
        _layers = new ArrayList<>();
        inputsSize = 0;
        outputsSize = 0;
    }

    public void addFullyConnectedLayer(int _inputSize, int _outputSize, double learningRate, long SEED, int type){

        FullyConnectedLayer _fcl = new FullyConnectedLayer(_inputSize, _outputSize, learningRate, SEED) {

            @Override
            public double activationFunction(double d) {
                if(type == 0){
                    return ActivationFunction.LeakyReLU(leak, d);
                }

                return ActivationFunction.SigmoidFunction(d);
            }

            @Override
            public double activationFunctionDerivative(double d) {
                if(type == 0){
                    return ActivationFunction.LeakyReLUDerivative(leak, d);
                }

                return ActivationFunction.SigmoidFunctionDerivative(d);
            }
            
        };

        if(_layers.size() != 0){
            _layers.get(_layers.size() - 1)._nextLayer = _fcl;
            _fcl._prevLayer = _layers.get(_layers.size() - 1);
        }else{
            inputsSize = _inputSize;
        }
            _layers.add(_fcl);
            outputsSize = _outputSize;


    }


    public double[] getOutput(double[] input){
        return UTILFunctions.outputAsArray(_layers.get(0).getOutput(input));
    }

    public void train(double[] inputs, double[] target){

        double[] output = getOutput(inputs);

        try {
            Matrix gradLoss = UTILFunctions.ArrayToMatrix(output).add(UTILFunctions.ArrayToMatrix(target).mult(-1));

            _layers.get(_layers.size() - 1).backPropogation(UTILFunctions.outputAsArray(gradLoss));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
