import java.awt.image.BufferedImage;

public class Sprite {

    //pixels for drawing by renderhandler
    protected int[] pixels;

    //width and height in pixels
    protected int width, height;

    protected SpriteSheet sheet;

    //main constructor to get sprite from spritesheet
    public Sprite(SpriteSheet sheet, int startX, int startY, int height, int width){
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
        sheet.getImage().getRGB(startX, startY, width, height, pixels, 0, width);
    }

    public Sprite(SpriteSheet sheet){
        this.sheet = sheet;
        this.width = sheet.spriteSizeX;
        this.height = sheet.spriteSizeY;
        pixels = new int[width * height];
    }

    //debug and testing constructor to get a sprite straight from an image
    public Sprite(BufferedImage image){
        width = image.getWidth();
        height = image.getHeight();
        pixels = new int[width*height];
        image.getRGB(0, 0, width, height, pixels, 0, width);
    }

    //getter and setter methods from here on out

    public int getWidth(){return width;}
    public int getHeight(){return height;}
    public int[] getPixels(){return pixels;}
}
