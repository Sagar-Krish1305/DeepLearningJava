package NeuralNetworks.Layers;

import NeuralNetworks.ActivationFunctions.ActivationFunction;
import NeuralNetworks.Matrix.Matrix;
import NeuralNetworks.Matrix.UTILFunctions;

public class ActivationLayer extends Layer{


    private double[] lastOutput;
    public ActivationLayer(int type, int r, double lr) {
        super(r, 1, r, 1, lr);
    }

    @Override
    public Matrix getOutput(double[] inputs) {
        Matrix m = activate(UTILFunctions.ArrayToMatrix(inputs));
        lastOutput = UTILFunctions.outputAsArray(m);
        if(_nextLayer != null){
            return _nextLayer.getOutput(lastOutput);
        }
        return m;
    }

    @Override
    public Matrix getOutput(Matrix[] inputs) {
        return getOutput(UTILFunctions.outputAsArray(Matrix.flattenMatrix(inputs, _prevLayer.outRows, _prevLayer.outCols)));
    }

    @Override
    public void backPropogation(double[] gradLoss) {
        double[] afp = null;
        try {
            afp = UTILFunctions.outputAsArray(UTILFunctions.ArrayToMatrix(gradLoss).Dot(deactivate(UTILFunctions.ArrayToMatrix(lastOutput))));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(_prevLayer != null){
            _prevLayer.backPropogation(afp);
        }
    }

    @Override
    public void backPropogation(Matrix[] gradLoss) {
        backPropogation(UTILFunctions.outputAsArray(Matrix.flattenMatrix(gradLoss, _prevLayer.outputRows(), _prevLayer.outputCols())));
    }

    private Matrix activate(Matrix m){
        Matrix m1 = new Matrix(m.rows, m.cols);
        for(int i = 0 ; i < m.rows ; i++){
            for(int j = 0 ; j < m.cols ; j++){
                m1.data[i][j] = ActivationFunction.SigmoidFunction(m.data[i][j]);
            }
        }
        return m1;
    }

    private Matrix deactivate(Matrix m){
        Matrix m1 = new Matrix(m.rows, m.cols);
        for(int i = 0 ; i < m.rows ; i++){
            for(int j = 0 ; j < m.cols ; j++){
                m1.data[i][j] = ActivationFunction.SigmoidFunctionDerivative(m.data[i][j]);
            }
        }
        return m1;
    }

    
}
