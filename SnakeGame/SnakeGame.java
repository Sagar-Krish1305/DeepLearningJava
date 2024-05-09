package SnakeGame;
import Qlearning.DeepQForSnake;
import processing.core.PApplet;

import java.util.ArrayList;

public class SnakeGame extends PApplet {

    Snake s;

    ArrayList<Box> fruits;
    DeepQForSnake dqs;

    public void settings(){
        size(640, 640);
        s = new Snake(this);
        fruits = new ArrayList<>();
        for(int i = 0 ; i < Constants.TOTAL_FRUIT ; i++){
            fruits.add(new Box(this));
        }
        dqs = new DeepQForSnake(s, fruits);
    }

    public void draw(){
        background(0);
        // <----------RENDERING THE GRID------------------------------------------------->
        for(int i = 1 ; i <= Constants.GRIDSIZE_ + 1 ; i++){
            stroke(255);
            line(i * Constants.BOXLENGTH_, Constants.BOXLENGTH_, i*Constants.BOXLENGTH_, height - Constants.BOXLENGTH_);
            line(Constants.BOXLENGTH_, i * Constants.BOXLENGTH_, height - Constants.BOXLENGTH_, i*Constants.BOXLENGTH_);
        }
        // <----------------------------------------------------------------------------->
        delay(200);
        showFruits();
        int fruitTouch = s.fruitTouch(fruits);
        if(fruitTouch != -1){
            fruits.set(fruitTouch, new Box(this));
            dqs.updateFruits(fruits);
        }
        s.showSnake();
        s.update();
//        if(keyPressed){
//            if(key == 'w' && s.dirY != 1){
//                s.updateDirection(0, -1);
//            }else if(key == 's' && s.dirY != -1){
//                s.updateDirection(0, +1);
//            }else if(key == 'a' && s.dirX != 1){
//                s.updateDirection(-1, 0);
//            }else if(key == 'd' && s.dirX != -1){
//                s.updateDirection(+1, 0);
//            }
//        }

        dqs.getOutput();
        if(s.wallViolate()){
            s = new Snake(this);
            dqs.updateSnake(s);
        }
    }

    private void showFruits(){
        for(Box fruit : fruits){
            fruit.show();
        }
    }

    public static void main(String[] args) {
        String[] processingArgs = {"SnakeGame"};
        SnakeGame sketch = new SnakeGame();
        PApplet.runSketch(processingArgs, sketch);
    }
}
