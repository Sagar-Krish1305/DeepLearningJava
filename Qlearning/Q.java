package Qlearning;
import java.util.Random;

import NeuralNetworks.Matrix.Matrix;

class Q{

    /*
     *  Implement a Q Learning Algorothm for Reinforcement Learning. 
     *  
     *  STEPs for Implementing that are : 
     *   1. Create A new Class Called Q - Leaning
     * 
     */
    public static void main(String[] args) {
        
    }
}

/*
 *  According to this ALGORITHM
 *  An Agent Can Move From One State to Another. 
 *   This Algorithm Requires the Bellman Equation for the Updation of Matrix; 
 *  
 */ 
 abstract class QLearner{

    double discountRate; // Discount Rate for The Algo. (SO THAT THE VALUES GET CONVERGE AT SOME POINT)
    double learningRate; // Learning Rate for The Algo. 
    int nStates; // Number of states;
    int nActions; // Number of Actions;
    Matrix qTable;  // qTable Which Will Get Updated;
    double epsilon; // for Epsilon Greedy Policy;
    int episodes;
    public QLearner(int episodes, double discountRate, double learningRate, int nStates, int nActions, double epsilon) {
        this.discountRate = discountRate;
        this.learningRate = learningRate;
        this.nStates = nStates;
        this.nActions = nActions;
        this.epsilon = epsilon;
        this.episodes = episodes;
        this.qTable = new Matrix(nStates, nActions);
        
         // Initialize the Q Values to Zero (INITIALLY THE AGENT KNOWS NOTHING)

        for (int i = 0; i < qTable.rows; i++) {
            for(int j = 0 ; j < qTable.cols ; j++){
                qTable.data[i][j] = 0.0;
            }
        }
        
    }

    private int epsilonGreedyPolicy(int state){
        Random r = new Random();
        if(r.nextDouble(0, 1) >= epsilon){
            // do Exploitation
            
            return findMaxQValueAction(state);
        }else{
            // do Exploration
            return r.nextInt(nActions);
        }
    }

    abstract public int getNextState(int state, int action);
    abstract public double getReward(int newState);

    private int findMaxQValueAction(int state){
        double maxQVal = Double.MIN_VALUE;
        int thatAction = -1;
        for(int j = 0 ; j < nActions ; j++){
            double currQVal = qTable.data[state][j];
            if(maxQVal < currQVal){
                thatAction = j;
                maxQVal = currQVal;
            }
        }
        return thatAction;
    }

    private double findMaxQValue(int state){
        double maxQVal = Double.MIN_VALUE;
            for(int j = 0 ; j < nActions ; j++){
                double currQVal = qTable.data[state][j];
                if(maxQVal < currQVal){
                    maxQVal = currQVal;
                }
            }
        return maxQVal;
    }
    public  void updateTable(int state){
        int totalEpisodes = episodes;
        while(totalEpisodes-- == 0){
            // Find A Good Action
            int action = epsilonGreedyPolicy(state); // check betweenExploration and Exploitation

            // Take the Action
            int newState = getNextState(state, action); // get next State According to Actions

            // Find the Reward
            double reward = getReward(newState); // checkFor the Reward for the respective State

            // get The max q value for the newState
            double maxQValue = findMaxQValue(newState); 

            // update according to Bellman-Equation
            qTable.data[state][action] = (1 - learningRate)*(qTable.data[state][action]) + learningRate*(reward + maxQValue);

            // Change state to new State
            state = newState;
        }
    }
}