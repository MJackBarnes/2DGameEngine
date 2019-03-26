package RenderingEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Renderer{

    private Canvas canvas = new Canvas();

    private JFrame frame;

    public RenderHandler renderHandler;

    public Renderer(JFrame jFrame, RenderHandler renderHandler){
        frame = jFrame;
        this.renderHandler = renderHandler;
        frame.add(canvas);

    }

    public void render(){
        //Get graphics from the buffer
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();

        //Super.paint() to avoid problems
        frame.paint(graphics);

        //Write to the current buffer

        for (int i = 0; i < renderHandler.getViewport().getWidth(); i++) {
            for (int j = 0; j < renderHandler.getViewport().getHeight(); j++) {
                renderHandler.setPixel((int) Math.random() * 0xFFFFFF, i, j);
            }

        }
        renderHandler.render(graphics);

        //Memory management (Sounds like an oxymoron in java)
        graphics.dispose();

        //Display the next buffer
        bufferStrategy.show();
    }
}
