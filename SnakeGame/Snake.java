package SnakeGame;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class Snake{

    PApplet parent;
    ArrayList<Box> boxPositions;
    int dirX = 0;
    int dirY = 0;

    int size = 1;
    Box previoslyVistedBox;
    Snake(PApplet parent_){
        this.parent = parent_;
        int x_ = (int) PApplet.floor(parent.random(0, Constants.BOXLENGTH_));
        int y_ = (int) PApplet.floor(parent.random(0, Constants.BOXLENGTH_));
        boxPositions = new ArrayList<>();
        boxPositions.add(new Box(parent, x_, y_));
        previoslyVistedBox = boxPositions.get(0);
    }

    int fruitTouch(ArrayList<Box> fruits){
        for(int i = 0 ; i < fruits.size() ; i++){
            if(fruits.get(i).x == boxPositions.get(0).x && fruits.get(i).y == boxPositions.get(0).y){
                boxPositions.add(previoslyVistedBox);
                size++;
                return i;
            }
        }
        return -1;
    }

    public void updateDirection(int dirX_, int dirY_){
        this.dirX = dirX_;
        this.dirY = dirY_;
    }
    void update(){
        if(dirX == 1){
            moveSnake('d');
        }else if(dirX == -1){
            moveSnake('a');
        }else if(dirY == 1){
            moveSnake('s');
        }else if(dirY == -1){
            moveSnake('w');
        }
    }
    void moveSnake(char c){
        int newPosX = boxPositions.get(0).x;
        int newPosY = boxPositions.get(0).y;

        if(c == 'w'){
            newPosY--;
        }else if(c == 's'){
            newPosY++;
        }else if(c == 'a'){
            newPosX--;
        }else if(c == 'd'){
            newPosX++;
        }

        previoslyVistedBox = boxPositions.get(boxPositions.size() - 1);

        for(int i = boxPositions.size() - 2 ; i >= 0 ; i--){
            Collections.swap(boxPositions, i, i + 1);
        }
        boxPositions.set(0, new Box(parent, newPosX, newPosY));
    }

    boolean wallViolate(){
        if(boxPositions.get(0).x >= 30 || boxPositions.get(0).x < 0){
            return true;
        }

        if(boxPositions.get(0).y >= 30 || boxPositions.get(0).y < 0){
            return true;
        }

        return false;
    }
    void showSnake(){
        for(int i = boxPositions.size() - 1 ; i >= 0 ; i--){
            boxPositions.get(i).show();
        }
    }

    public double size() {
        return size;
    }
}
