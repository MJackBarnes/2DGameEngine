package Game;

import java.awt.image.BufferedImage;

public class SpriteSheet {

    // The entire sheet in a pixel array
    private int[] pixels;

    // the entire image in a buffered image
    private BufferedImage image;

    //final size variables
    public final int SIZEX;
    public final int SIZEY;

    //all of the sprites that have been loaded
    private Sprite[] loadedSprites = null;

    //to track if the sprites have been loaded
    private boolean spritesLoaded = false;

    //the width and height of a sprite
    public int spriteSizeX;
    public int spriteSizeY;


    //Constructor to get a spritesheet with the default color being used as transparrent
    public SpriteSheet(BufferedImage image){
        this.image = image;
        SIZEX = image.getWidth();
        SIZEY = image.getHeight();
        pixels = new int[SIZEX * SIZEY];
        pixels = image.getRGB(0, 0, SIZEX, SIZEY, pixels, 0, SIZEX);
    }

    //Constructor to make a custom color the transparent color for this image
    public SpriteSheet(BufferedImage image, int alpha){
        this(image);
        for (int i = 0; i < pixels.length; i++) {
            if(pixels[i] == alpha){
                pixels[i] = Game.clear;
            }
        }
    }

    //load all sprites int to the loadedSprites array
    public void loadSprites(int spriteSizeX, int spriteSizeY){
        loadedSprites = new Sprite[(SIZEX/spriteSizeX) * (SIZEY/spriteSizeY)];
        int spriteID = 0;
        this.spriteSizeX = spriteSizeX;
        this.spriteSizeY = spriteSizeY;
        for(int y = 0; y < SIZEY; y+= spriteSizeY){
            for (int x = 0; x < SIZEX; x+= spriteSizeX) {
                loadedSprites[spriteID] = new Sprite(this, x, y, spriteSizeY, spriteSizeX);
                spriteID ++;
            }
        }
        spritesLoaded = true;
    }

    //get a sprite from the image
    public Sprite getSprite(int x, int y){
        if(!spritesLoaded){
            System.out.println("No sprites loaded");
            return null;
        }
        int spriteID = x + y * (SIZEX/spriteSizeX);
        if(spriteID >= loadedSprites.length){
            System.out.println("Sprite ID out of bounds");
            return null;
        }
        return loadedSprites[spriteID];
    }

    //getter and setter methods from here on out boios

    public int[] getPixels(){return pixels;}
    public BufferedImage getImage(){return image;}
}
