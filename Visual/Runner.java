package Visual;

import processing.core.PApplet;

public class Runner {
    public static void main(String[] args) {
        String[] pArgs = new String[] {
            "Window"
        };
        HandWriting h = new HandWriting();
        PApplet.runSketch(pArgs, h);
    }
}
