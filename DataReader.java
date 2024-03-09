

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import NeuralNetworks.Builder.NeuralNetwork;
import NeuralNetworks.Matrix.Matrix;
import NeuralNetworks.Matrix.UTILFunctions;


public class DataReader {
    public static void main(String[] args) {
        String trainImagesFile = "MNIST/train-images-idx3-ubyte/train-images-idx3-ubyte";
        String trainLabelsFile = "MNIST/train-labels-idx1-ubyte/train-labels-idx1-ubyte";
        String testImagesFile = "MNIST/t10k-images-idx3-ubyte/t10k-images-idx3-ubyte";
        String testLabelsFile = "MNIST/t10k-labels-idx1-ubyte/t10k-labels-idx1-ubyte";

        // Read training images
        List<Matrix> trainImages = readImages(trainImagesFile);

        // Read training labels
        List<Integer> trainLabels = readLabels(trainLabelsFile);

        // Read test images
        List<Matrix> testImages = readImages(testImagesFile);

        // Read test labels
        List<Integer> testLabels = readLabels(testLabelsFile);

        // Perform operations with the data as needed

        // Making the NeuralNetwork;
        NeuralNetwork nn = new NeuralNetwork(2, 143, 0.4);
        nn.addConvolutionLayer(28, 28, 1, 1, 5);
        // nn.addMaxPoolingLayer(5, 2);
        nn.addActivationLayer(2);
        nn.addFullyConnectedLayer(4);
        nn.addActivationLayer(2);
        
        

        double target[] = new double[]{0,0,0,0};
        // nn.printDetails();
        int n = 0;
        for(int i = 0 ; i < trainImages.size() ; i++){
            if(trainLabels.get(i) > 3) continue;
            target[trainLabels.get(i)] = 1; 
            nn.train(UTILFunctions.outputAsArray(Matrix.flattenMatrix(new Matrix[]{trainImages.get(i)}, 28, 28)), target);
            target[trainLabels.get(i)] = 0;
        }

        System.out.println(n);
        
        for(int a = 0 ; a < testImages.size() ; a++){
            // if(testLabels.get(a)!=0 || testLabels.get(a)!=1) continue;
            if(testLabels.get(a) > 3) continue;
            double[] d = nn.getOutput(UTILFunctions.outputAsArray(Matrix.flattenMatrix(new Matrix[]{
                testImages.get(a)
            }, 28 ,28)));
            
            System.out.println("OUTPUT PREDICTED : " + Arrays.toString(d));
            System.out.println("OUTPUT REQUIRED : " + testLabels.get(a));
            System.out.println();
        }   
    }

    private static List<Matrix> readImages(String filename) {
        List<Matrix> images = new ArrayList<>();

        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(filename))) {
            // Read magic number (ignore for now)
            dataInputStream.readInt();

            // Read number of images
            int numImages = dataInputStream.readInt();

            // Read number of rows and columns
            int numRows = dataInputStream.readInt();
            int numCols = dataInputStream.readInt();

            // Read image data
            for (int i = 0; i < numImages; i++) {
                Matrix image = new Matrix(numRows, numCols);
                for (int row = 0; row < numRows; row++) {
                    for (int col = 0; col < numCols; col++) {
                        image.data[row][col] = dataInputStream.readUnsignedByte();
                    }
                }
                images.add(image);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return images;
    }

    private static List<Integer> readLabels(String filename) {
        List<Integer> labels = new ArrayList<>();

        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(filename))) {
            // Read magic number (ignore for now)
            dataInputStream.readInt();

            // Read number of labels
            int numLabels = dataInputStream.readInt();

            // Read label data
            for (int i = 0; i < numLabels; i++) {
                labels.add((int) dataInputStream.readByte());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return labels;
    }


}
