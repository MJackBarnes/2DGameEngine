import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.image.DataBufferInt;


public class RenderHandler {

    //The image that is displayed
    private BufferedImage view;

    //A raw pixel version of the image to be displayed
    private int[] pixels;

    //A viewport for the game
    private Rectangle viewport;

    //Only valid constructor
    public RenderHandler(int width, int height){
        view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        viewport = new Rectangle(0, 0, width, height);
        pixels = ((DataBufferInt)view.getRaster().getDataBuffer()).getData();
    }

    //render the bufferedimage to the gamebuffer
    public void render(Graphics g){
        g.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
    }

    //draw an image directly to the screen
    public void renderImage(BufferedImage image, int xPos, int yPos, int xZoom, int yZoom) {
        int[] imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        renderArray(imagePixels, image.getHeight(), image.getHeight(), xPos, yPos, xZoom, yZoom);
    }

    //Returns the viewport for the game
    public Rectangle getViewport(){return viewport;}

    //Set a single pixel on the display
    private void setPixel(int pixel, int x, int y){
        if(y >= viewport.getY() && x >= viewport.getX() && x <= viewport.getX() + viewport.getWidth() && y <= viewport.getY()+ viewport.getWidth()){
            int pixelIndex = (x - viewport.getX()) + (y - viewport.getY()) * view.getWidth();
            if (pixels.length > pixelIndex)
                pixels[pixelIndex] = pixel;
        }
    }


    //renders an entire pixel array onto the buffer
    public void renderArray(int[] renderPixels, int renderH, int renderW, int xPos, int yPos, int xZoom, int yZoom){
        for (int y = 0; y < renderH; y++)
            for (int x = 0; x < renderW; x++)
                for (int xz = 0; xz < xZoom; xz++)
                    for (int yz = 0; yz < yZoom; yz++)
                        if(renderPixels[x + y * renderW] != Game.clear)
                            setPixel(renderPixels[x + y * renderW], (x*xZoom) + xPos + xz, (y * yZoom) + yPos + yz);
    }

    //Renders a rectangle to the buffer (Mainly for debugging)
    public void renderRectangle(Rectangle rect, int xZoom , int yZoom){
        if(rect.getPixels() != null)
            renderArray(rect.getPixels(), rect.getHeight(), rect.getWidth(), rect.getX(), rect.getY(), xZoom, yZoom);
    }

    //Renders a sprite to the display
    public void renderSprite(Sprite sprite, int xPos, int yPos, int xZoom, int yZoom){
        renderArray(sprite.getPixels(), sprite.getHeight(), sprite.getWidth(), xPos, yPos, xZoom, yZoom);
    }

    public void clear(){
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }
}
