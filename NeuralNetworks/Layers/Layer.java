package NeuralNetworks.Layers;

import NeuralNetworks.Martrix.Matrix;

public abstract class Layer{
    // abstract class means different functions can be written for different types of these class. 

    public Layer _nextLayer; // to add a new Layer

    public Layer _prevLayer;

    public int nInputs, nOutputs; // Number of inputs and outputs;
    public double learningRate; // 
    

    public Layer(int nInputs, int nOutputs, double learningRate) {
        this.nInputs = nInputs;
        this.nOutputs = nOutputs;
        this.learningRate = learningRate;

        _nextLayer = null;
        _prevLayer = null;
    }
    abstract public Matrix getOutput(double[] inputs);
    abstract public void backPropogation(double[] gradLoss);

}