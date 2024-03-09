package NeuralNetworks.Layers;

import NeuralNetworks.Matrix.Matrix;
import NeuralNetworks.Matrix.UTILFunctions;

public class FullyConnectedLayer extends Layer{
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
    public void backPropogation(double[] DeDy) { // we have been given the grad loss

        // we want DeDw, DeDb
        try {
            Matrix DeDymatrix = UTILFunctions.ArrayToMatrix(DeDy);
            Matrix DeDw = lastInputs.mult(DeDymatrix.transpose());
            Matrix DeDb = DeDymatrix;
            Matrix DeDx = weights.mult(DeDymatrix);


            weights = weights.add(DeDw.mult(-learningRate));
            bias = bias.add(DeDb.mult(-learningRate));

            if(_prevLayer != null){
                _prevLayer.backPropogation(UTILFunctions.outputAsArray(DeDx));
            }
        } catch (Exception e) {

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

    
}
