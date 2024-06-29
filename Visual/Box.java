package Visual;

import processing.core.PApplet;

public class Box {
    PApplet parent;
    int x, y;
    int width, height;
    boolean selected = false;
    Box(PApplet parent, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.parent =  parent;
    }

    public static boolean isSafe(int x, int y, int n, int m) {
        return x >= 0 && x < n && y >= 0 && y < m;
    }

    void setSelected(boolean selected) {
        this.selected = selected;
    }

    void show(){
        parent.push();
            parent.stroke(0);
            parent.fill((selected ? 0 : 255));
            parent.rect(x*width + Constants.OFFSET, y*width + Constants.OFFSET, width, height);
        parent.pop();
    }


}
