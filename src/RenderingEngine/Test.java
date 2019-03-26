package RenderingEngine;

import javax.swing.*;
import java.awt.*;

public class Test extends JFrame {

    Renderer r;
    Canvas c;
    RenderHandler rh;

    public static void main(String[] args){
        Test test = new Test();
        while (true){
            test.r.render();
        }
    }

    public Test(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(0, 0, 400, 400);
        rh = new RenderHandler(400, 400, 13241251);
        c = new Canvas();
        r = new Renderer(this, rh);
    }
}
