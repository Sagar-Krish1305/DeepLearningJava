package NeuralNetworks.Builder;

import NeuralNetworks.ActivationFunctions.ActivationFunction;
import NeuralNetworks.Layers.ActivationLayer;
import NeuralNetworks.Layers.ConvolutionalLayer;
import NeuralNetworks.Layers.FullyConnectedLayer;
import NeuralNetworks.Layers.Layer;
import NeuralNetworks.LossFunctions.LossFunction;
import NeuralNetworks.Matrix.Matrix;
import NeuralNetworks.Matrix.UTILFunctions;

import java.util.ArrayList;


public class NeuralNetwork {
    ArrayList<Layer> _layers;

    int inRows, inCols;
    int outRows, outCols;
    int type;
    double leak;
    long SEED;
    double learningRate;
    public NeuralNetwork(int type, long SEED, double learningRate){
        _layers = new ArrayList<>();
        this.type = type;
        this.SEED = SEED;
        this.learningRate = learningRate;
    }

    public void addFullyConnectedLayer(int _inputSize, int _outputSize, double learningRate, long SEED){

        FullyConnectedLayer _fcl = new FullyConnectedLayer(_inputSize, _outputSize, learningRate, SEED);

        if(_layers.size() != 0){
            _layers.get(_layers.size() - 1)._nextLayer = _fcl;
            _fcl._prevLayer = _layers.get(_layers.size() - 1);
        }else{
            inRows = _inputSize;
            outRows = 1;
        }
            _layers.add(_fcl);
            outRows = _outputSize;
            outCols = 1;


    }

    public void addFullyConnectedLayer(int _outputSize){

        int _inputSize = _layers.get(_layers.size() - 1).outputElements();

        FullyConnectedLayer _fcl = new FullyConnectedLayer(_inputSize, _outputSize, learningRate, SEED);

        if(_layers.size() != 0){
            _layers.get(_layers.size() - 1)._nextLayer = _fcl;
            _fcl._prevLayer = _layers.get(_layers.size() - 1);
        }else{
            inRows = _inputSize;
            outRows = 1;
        }
            _layers.add(_fcl);
            outRows = _outputSize;
            outCols = 1;


    }


    public void addConvolutionLayer(int rows, int cols, int channels,  int depth, int kernalSize){

        ConvolutionalLayer cl = new ConvolutionalLayer(rows, cols, channels, depth, kernalSize, learningRate, this.SEED);
        if(_layers.size() != 0){
            _layers.get(_layers.size() - 1)._nextLayer = cl;
            cl._prevLayer = _layers.get(_layers.size() - 1);
        }else{
            inRows = rows;
            inCols = cols;
        }
            _layers.add(cl);
            outRows = cl.outputRows();
            outCols = cl.outputCols();
    }
    public void addActivationLayer(int type, int inputs){

        ActivationLayer a = new ActivationLayer(type, inputs, 1);
        if(_layers.size() != 0){
            _layers.get(_layers.size() - 1)._nextLayer = a;
            a._prevLayer = _layers.get(_layers.size() - 1);
        }else{
            inRows = inputs;
            inCols = 1;
        }
            _layers.add(a);
            outRows = a.outputRows();
            outCols = a.outputCols();

    }

    public void addActivationLayer(int type){
        int _inputSize = _layers.get(_layers.size() - 1).outputElements();
        ActivationLayer a = new ActivationLayer(type, _inputSize, learningRate);
        if(_layers.size() != 0){
            _layers.get(_layers.size() - 1)._nextLayer = a;
            a._prevLayer = _layers.get(_layers.size() - 1);
        }else{
            inRows = _inputSize;
            inCols = 1;
        }
            _layers.add(a);
            outRows = a.outputRows();
            outCols = a.outputCols();

    }

    public double[] getOutput(double[] input){
        return UTILFunctions.outputAsArray(_layers.get(0).getOutput(input));
    }

    public void train(double[] inputs, double[] target){
        
        double[] output = getOutput(inputs);
        try {
            Matrix gradLoss = LossFunction.BinaryCrossEntropyGrad(output, target);
            _layers.get(_layers.size() - 1).backPropogation(UTILFunctions.outputAsArray(gradLoss));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void printDetails(){
        int i = 1;
        for(Layer l : _layers){
            System.out.println("LAYER " + i);
            i++;
            if(l instanceof FullyConnectedLayer){
                System.out.println("FULLY CONNECTED LAYER : ");
            }else if(l instanceof ConvolutionalLayer){
                System.out.println("CONVOLUTIONAL LAYER");
            }else{
                System.out.println("ACTIVATION LAYER");
            }
            System.out.println();
            System.out.println("Input ROWS : " + l.inRows);
            System.out.println("Input COLS : " + l.inCols);
            System.out.println("Output ROWS : " + l.outRows);
            System.out.println("Output COLS : " + l.outCols);
        }
    }

    

}
