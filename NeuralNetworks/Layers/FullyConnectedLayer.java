package NeuralNetworks.Layers;
import java.util.Arrays;

import NeuralNetworks.Matrix.Matrix;
import NeuralNetworks.Matrix.UTILFunctions;

public abstract class FullyConnectedLayer extends Layer{
    long SEED;
    Matrix weights;
    Matrix bias;
    int nInputs;
    // Keeping track of previous inputs and outputs;
        Matrix lastInputs;
        Matrix lastOutputs;
        
    public FullyConnectedLayer(int nInputs, int nOutputs, double learningRate, long sEED){
        super(nInputs, 1, nOutputs, 1, learningRate);
        this.SEED = sEED;
        this.nInputs = nInputs;
        
        weights = new Matrix(nInputs, nOutputs); // one extra for bias
        bias = new Matrix(nOutputs, 1);
        // Initally Random Weights and are Assigned     
        weights.randomizeMatrix(SEED); 
        bias.randomizeMatrix(SEED);
    }


    public Matrix feedForwardPass(double[] inputs){

        // setting this up as last input
            lastInputs = UTILFunctions.ArrayToMatrix(inputs);
        
        
        Matrix inputMatrix = UTILFunctions.ArrayToMatrix(inputs);
        try {

            
            Matrix WTX = weights.transpose();
            

            WTX = WTX.mult(inputMatrix);
            Matrix Y = WTX.add(bias);
            // get hypothesis according to the above equation
            
            //setting up the lastOutput
            lastOutputs = (Y);

            // Now Activate
            Y = activate(Y);

            return Y;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    @Override
    public Matrix getOutput(double[] inputs){

        Matrix ff = feedForwardPass(inputs); // get output of the current layer 
        double[] outputOfThisLayer = UTILFunctions.outputAsArray(ff);

        if(_nextLayer != null){ // if there is a next layer that means we still have to send this data forwatd to get the output from the end
            return _nextLayer.getOutput(outputOfThisLayer);
        }else{
            return ff;
        }
        
    }

    @Override
    public void backPropogation(double[] DlDo) { // we have been given the grad loss

        Matrix DoDzmatrix = deactivate(lastOutputs);
        Matrix Dldomatrix = UTILFunctions.ArrayToMatrix(DlDo);
        Matrix DzDwmatrix = lastInputs;
        
        try {
             //Updating the bias Matrix
            Matrix x = DoDzmatrix.Dot(Dldomatrix);
            Matrix bias_error = x; // here we should also mult [1,1,1,1,1...]
            bias = bias.add(bias_error.mult(-learningRate));

            // Updating the weight Matrix
            
            Matrix weight_error = DzDwmatrix.mult(x.transpose());
            weights = weights.add(weight_error.mult(-learningRate));


            if(_prevLayer != null){
                // getting the new DlDo
                // it is just the gradLoss of last layer's output
                
                Matrix DlDxMatrix = weights.mult(Dldomatrix.Dot(DoDzmatrix));
                _prevLayer.backPropogation(UTILFunctions.outputAsArray(DlDxMatrix));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
            

    }

    @Override
    public Matrix getOutput(Matrix[] inputs) {
        return getOutput(UTILFunctions.outputAsArray(Matrix.flattenMatrix(inputs, _prevLayer.outRows, _prevLayer.outCols)));
    }

    @Override
    public void backPropogation(Matrix[] gradLoss) {
        backPropogation(UTILFunctions.outputAsArray(Matrix.flattenMatrix(gradLoss, _prevLayer.outputRows(), _prevLayer.outputCols())));
    }

    public abstract double activationFunction(double d);
    public abstract double activationFunctionDerivative(double d);



    private Matrix activate(Matrix m){
        Matrix m1 = new Matrix(m.rows, m.cols);
        for(int i = 0 ; i < m.rows ; i++){
            for(int j = 0 ; j < m.cols ; j++){
                m1.data[i][j] = activationFunction(m.data[i][j]);
            }
        }
        return m1;
    }

    private Matrix deactivate(Matrix m){
        Matrix m1 = new Matrix(m.rows, m.cols);
        for(int i = 0 ; i < m.rows ; i++){
            for(int j = 0 ; j < m.cols ; j++){
                m1.data[i][j] = activationFunctionDerivative(m.data[i][j]);
            }
        }
        return m1;
    }

    

    
}
