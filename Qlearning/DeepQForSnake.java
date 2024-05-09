package Qlearning;
import SnakeGame.Box;
import SnakeGame.Snake;
import SnakeGame.Constants;

import java.util.ArrayList;

public class DeepQForSnake {

    DeepQ dq;
    ArrayList<Box> fruits;
    Snake s;
    int currState = 0;

    public void updateFruits(ArrayList<Box> fruits_){
        this.fruits = fruits_;
    }
    public void updateSnake(Snake s_){
        this.s = s_;
    }
    public DeepQForSnake(Snake s, ArrayList<Box> fruits){
        this.s = s;
        this.fruits = fruits;
         dq = new DeepQ(5, 0.01, 0.4, 4, 0.99, 123) {

            @Override
            public int getNextState(int state, int action) {
                int x = state % Constants.GRIDSIZE_;
                int y = state / Constants.GRIDSIZE_;
                if(action == 0){
                    x++;
                }else if(action == 1){
                    x--;
                }else if(action == 2){
                    y++;
                }else if(action == 3){
                    y--;
                }

                return x + y * Constants.GRIDSIZE_;
            }

            @Override
            public double getReward(int newState) {
                int x = newState % Constants.GRIDSIZE_;
                int y = newState / Constants.GRIDSIZE_;
                for(int i = 0 ; i < fruits.size() ; i++){
                    if(fruits.get(i).x == x && fruits.get(i).y == y){
                        return 1000;
                    }
                }
                return (double) (Math.min(x, Constants.GRIDSIZE_ - x) + Math.min(y, Constants.GRIDSIZE_ - y)) /2 + s.size();
            }

         };
    }

    public void getOutput(){
            if(currState == 0){
                s.updateDirection(1, 0);
            }else if(currState == 1){
                s.updateDirection(0, 1);
            }else if(currState == 2){
                s.updateDirection(0, -1);
            }else{
                s.updateDirection(-1, 0);
            }
            int action = dq.act(currState);
        currState = dq.getNextState(currState, action);
    }

}
