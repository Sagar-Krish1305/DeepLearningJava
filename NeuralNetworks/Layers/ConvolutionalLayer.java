package NeuralNetworks.Layers;

import java.util.ArrayList;

import NeuralNetworks.Martrix.Matrix;
import NeuralNetworks.Martrix.UTILFunctions;

public class ConvolutionalLayer extends Layer{

    public int rows, cols; 
    public long SEED;
    public ArrayList<Matrix> _filters;
    public ArrayList<Matrix> _bias;
    public int depth;
    public ConvolutionalLayer(int rows, int cols, int depth, int nOutputs, double learningRate, long SEED) {
        super(rows * cols, nOutputs, learningRate);
        this.rows = rows;
        this.cols = cols;
        this.SEED = SEED;
        this._filters = new ArrayList<>();
        this._bias = new ArrayList<>();
        this.depth = depth;
    }

    
    public ArrayList<Matrix> convolutionForwardPass(ArrayList<Matrix> m){
        ArrayList<Matrix> _output = new ArrayList<>();

        _output = _bias;
        try {  
            for(int i = 0 ; i < m.size() ; i++){
                for(int j = 0 ; j < _filters.size() ; j++){
                    _output.get(i).add(m.get(i).conv(_filters.get(j)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return _output;
    }

    @Override
    public Matrix getOutput(double[] inputs) {

        return UTILFunctions.ArrayToMatrix(inputs);
    }

    @Override
    public void backPropogation(double[] gradLoss) {

    }
    
}
