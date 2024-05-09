package SnakeGame;

import processing.core.PApplet;

public class Box{
    PApplet parent;
    public int x;
    public int y;
    boolean isFruit = false;
    public Box(PApplet parent_, int x_, int y_){
        this.parent = parent_;
        this.x = x_;
        this.y = y_;
    }

    public Box(PApplet parent_) {
        this.parent = parent_;
        isFruit = true;
        this.x = (int) PApplet.floor(parent.random(0, Constants.BOXLENGTH_));
        this.y = (int) PApplet.floor(parent.random(0, Constants.BOXLENGTH_));
    }
    public void show(){
        parent.pushMatrix();
            if(isFruit){
                parent.fill(255, 0, 0);
            }else{
                parent.fill(255);
            }
            parent.rect(Constants.BOXLENGTH_ + Constants.BOXLENGTH_*x,
                    Constants.BOXLENGTH_ + Constants.BOXLENGTH_*y,
                    Constants.BOXLENGTH_,
                    Constants.BOXLENGTH_);
        parent.popMatrix();
    }
}
