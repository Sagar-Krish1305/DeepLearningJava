package NeuralNetworks.Layers;

import NeuralNetworks.Matrix.Matrix;
import NeuralNetworks.Matrix.UTILFunctions;

public class ConvolutionalLayer extends Layer{

    public int rows, cols; 
    public int outRows, outCols;
    public long SEED;
    public Matrix[][] _filters;
    public Matrix[] _bias;
    public int depth;
    public int kernalSize;
    public int channels;
    public Matrix[] lastInput;

    public ConvolutionalLayer(int rows, int cols, int channels,  int depth, int kernalSize, double learningRate, long SEED) {
        
        super(rows, cols ,rows - kernalSize + 1, cols - kernalSize + 1, learningRate); 
        this.rows = rows;
        this.cols = cols;
        this.SEED = SEED;
        this._filters = new Matrix[depth][channels];
        this._bias = new Matrix[depth];
        this.depth = depth;
        this.channels = channels;
        this.outRows = rows - kernalSize + 1;
        this.outCols = cols - kernalSize + 1;

        this.kernalSize = kernalSize;


        // Make Random Filter and Bias
        for(int i = 0 ; i < depth ; i++){
            
            for(int j = 0 ; j < channels ; j++){
                Matrix f = new Matrix(kernalSize, kernalSize);
                f.randomizeMatrix(SEED);
                _filters[i][j] = f;
            }
            Matrix b = new Matrix(outRows, outCols);
            b.randomizeMatrix(SEED);
            _bias[i] = b;
        }

    }

    
    public Matrix[] convolutionForwardPass(Matrix[] m){
        lastInput = m;
        Matrix[] _output = new Matrix[_bias.length];
        _output = _bias;
        try {  
            for(int i = 0 ; i < depth ; i++){
                for(int j = 0 ; j < channels ; j++){
                    _output[i] = _output[i].add(m[j].correlate(_filters[i][j]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return _output;
    }


    @Override
    public Matrix getOutput(double[] inputs) {
        return getOutput(Matrix.imagifyMatrix(UTILFunctions.ArrayToMatrix(inputs), depth, rows, cols));
    }   

    @Override
    public void backPropogation(double[] gradLoss) {

        backPropogation(Matrix.imagifyMatrix(UTILFunctions.ArrayToMatrix(gradLoss), depth, outRows, outCols));
    }


    @Override
    public Matrix getOutput(Matrix[] inputs) { 

        Matrix[] cfp = convolutionForwardPass(inputs) ;


        if(_nextLayer != null){
            return _nextLayer.getOutput(cfp);
        }
        
        Matrix ans = Matrix.flattenMatrix(cfp, outRows, outCols);
        return ans;
    }


    @Override
    public void backPropogation(Matrix[] DlDy) { // here, y is the output
        Matrix[][] DlDk = new Matrix[depth][channels];
        Matrix[] DlDx = new Matrix[channels];
        for(int i = 0 ; i < depth ; i++){
            DlDx[i] = Matrix.scalarMatrix(rows, cols, 0);
        }
        try {
        for(int i = 0 ; i < depth ; i++){
            for(int j = 0 ; j < channels ; j++){
                DlDk[i][j] = lastInput[j].correlate(DlDy[i]);
                DlDx[j] = DlDx[j].add(DlDy[i].fullConvolve(_filters[i][j]));
            }
        }

            for(int i = 0 ; i < depth ; i++){
                for(int j = 0 ; j < channels ; j++){
                    _filters[i][j] = _filters[i][j].add(DlDk[i][j].mult(-learningRate));
                }   
            }
            for(int i = 0 ; i < depth ; i++){
                _bias[i] = _bias[i].add(DlDy[i].mult(-learningRate));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(_prevLayer!=null){
            _prevLayer.backPropogation(DlDx);
        }

    }
    
}
