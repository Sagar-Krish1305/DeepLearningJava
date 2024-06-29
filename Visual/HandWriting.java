package Visual;

import NeuralNetworks.Builder.NeuralNetwork;
import processing.core.PApplet;

import java.util.Arrays;

public class HandWriting extends PApplet{
    NeuralNetwork nn;
    Box[][] grid;
    HandWriting(){

          nn = TrainingMNIST.getTrainedNetwork();
//        nn = new NeuralNetwork(2, 143, 0.4);
//        nn.addConvolutionLayer(28, 28, 1, 1, 5);
//        nn.addActivationLayer(2);
//        nn.addFullyConnectedLayer(4);
//        nn.addActivationLayer(2);

        grid = new Box[Constants.GRIDSIZE][Constants.GRIDSIZE];
        for(int i = 0; i < Constants.GRIDSIZE; i++){
            for(int j = 0; j < Constants.GRIDSIZE; j++){
                grid[i][j] = new Box(this, i, j, Constants.BOXSIZE, Constants.BOXSIZE);
            }
        }
    }
    public void settings(){
        size(800, 800);
    }

    public void setup(){
    }
    public void draw(){
        background(255);
        for(int i = 0; i < Constants.GRIDSIZE; i++){
            for(int j = 0; j < Constants.GRIDSIZE; j++){
                grid[i][j].show();
            }
        }

        // if mouse is pressed, toggle selected and non-selected
        if(mousePressed){
            int x = (mouseX - Constants.OFFSET)/Constants.BOXSIZE;
            int y = (mouseY - Constants.OFFSET)/Constants.BOXSIZE;
            if(Box.isSafe(x, y, Constants.GRIDSIZE, Constants.GRIDSIZE)){
                grid[x][y].setSelected(true);
            }
        }

        if(keyPressed){
            keyPressed = false;
            if(key == 's') {
                // set the data to the input
                double[] input = new double[Constants.GRIDSIZE * Constants.GRIDSIZE];
                int k = 0;
                for (int i = 0; i < Constants.GRIDSIZE; i++) {
                    for (int j = 0; j < Constants.GRIDSIZE; j++) {
                        input[k++] = (grid[i][j].selected ? 1.0d : 0.0d);
//                        System.out.print((grid[i][j].selected ? 1 : 0));
                    }
//                    System.out.println();
                }
//                System.out.println();

                double[] ans = nn.getOutput(input);
                System.out.println(getMaxIdx(ans));
            }else if(key == 'c'){
                for (int i = 0; i < Constants.GRIDSIZE; i++) {
                    for (int j = 0; j < Constants.GRIDSIZE; j++) {
                        grid[i][j].setSelected(false);
                    }
                }
            }
        }
    }

    int getMaxIdx(double[] arr){
        int maxIdx = 0;
        double maxVal = Double.MIN_VALUE;
        for(int i = 0; i < arr.length; i++){
            if(arr[i] > maxVal){
                maxIdx = i;
                maxVal = arr[i];
            }
        }
        return maxIdx;
    }
}
