package Qlearning;

import java.util.ArrayList;
import java.util.Random;

import NeuralNetworks.Builder.NeuralNetwork;


public abstract class DeepQ{
    Random r;
    int N = 100;
    ArrayList<ExperiencedMemory> memory;
    NeuralNetwork nn;

    double epsilon = 1;
    int nActions;
    double discountRate;
    double epsilon_decay;
    DeepQ(int episodes, double discountRate, double lR, int nActions, double epsilon_decay, long SEED){
        r = new Random(SEED);

        nn.addFullyConnectedLayer(1, 2*nActions, lR, SEED);
        nn.addFullyConnectedLayer(2*nActions, nActions, lR, SEED);

        memory = new ArrayList<>();


        this.nActions = nActions;
        this.discountRate = discountRate;
        this.epsilon_decay = epsilon_decay;
    }  


    public int act(int state){
        if(r.nextDouble(0,1) <= epsilon){
            return r.nextInt(nActions);
        }   

        double[] output = nn.getOutput(new double[]{state});
        return getMaxQValuedAction(output);
    }

    private int getMaxQValuedAction(double[] outputs){
        int a = -1;
        double m = Double.MIN_VALUE;
        for(int i = 0 ; i < nActions ; i++){
            if(outputs[i] > m){
                m = outputs[i];
                a = i;
            }
        }   
        return a;
    }

    private double getMaxQValue(double[] outputs){

        double m = Double.MIN_VALUE;
        for(int i = 0 ; i < nActions ; i++){
            if(outputs[i] > m){
                m = outputs[i];
            }
        }   
        return m;
    }

    public void addMemory(int state, int action, double reward, int nextState){
        memory.add(new ExperiencedMemory(state, action, reward, nextState));
    }

    public void replay(int batchSize){
        ArrayList<ExperiencedMemory> minibatch = randomSample(batchSize);
        for(ExperiencedMemory e : minibatch){
            double target = e.reward;
            if(!e.done){ 
                e.done = true;
                target = e.reward + discountRate * getMaxQValue(nn.getOutput(new double[]{ e.nextState }));
            }

            double[] input = new double[] {e.state};
            double[] target_ = nn.getOutput(input);
            target_[e.action] = target;

            nn.train(input, target_);

        }

        if(epsilon > 0.01){
            epsilon *= epsilon_decay;
        }
    }

    private ArrayList<ExperiencedMemory> randomSample(int batchSize){
        ArrayList<ExperiencedMemory> batch = new ArrayList<>();
        while(batchSize!=0){
            for(int i = 0 ; i < memory.size() ; i++){
                if(r.nextDouble(1) > 0.5 && batchSize!=0){
                    batch.add(memory.get(i));
                    batchSize--;
                }
            }
        }
        return batch;
    } 

    
}

class ExperiencedMemory{
    int state;
    int action;
    double reward;
    int nextState;
    Boolean done = false;
    public ExperiencedMemory(int state, int action, double reward, int nextState) {
        this.state = state;
        this.action = action;
        this.reward = reward;
        this.nextState = nextState;
    }
}
