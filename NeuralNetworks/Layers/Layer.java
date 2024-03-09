package NeuralNetworks.Layers;
import NeuralNetworks.Matrix.Matrix;

public abstract class Layer{
    // abstract class means different functions can be written for different types of these class. 

    public Layer _nextLayer; // to add a new Layer
    public Layer _prevLayer;

    public int outRows, outCols; // Number of inputs and outputs;
    public double learningRate; // 
    
    public int inRows, inCols;

    public Layer(int rows, int cols, int outRows, int outCols, double learningRate) {
        this.inCols = cols;
        this.inRows = rows;
        this.outCols = outCols;
        this.outRows = outRows;
        this.learningRate = learningRate;

        _nextLayer = null;
        _prevLayer = null;
    }
    abstract public Matrix getOutput(double[] inputs);
    abstract public Matrix getOutput(Matrix[] inputs);
    abstract public void backPropogation(double[] gradLoss);
    abstract public void backPropogation(Matrix[] gradLoss);

    public int outputRows(){
        return outRows;
    }
    public int outputCols(){
        return outCols;
    }
    public int outputElements(){
        return outCols * outRows;
    }



}