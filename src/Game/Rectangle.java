package Game;

public class Rectangle {

    //dimensions
    private int xPosition;
    private int yPosition;
    private int width;
    private int height;

    //pixels for debugging
    private int[] pixels;

    //default constructor
    public Rectangle(){
        xPosition = 0;
        yPosition = 0;
        width = 0;
        height = 0;
    }

    //Other constructor
    public Rectangle(int x, int y, int width, int height){
        this.xPosition = x;
        this.yPosition = y;
        this.width = width;
        this.height = height;
    }

    //get the pixels for drawing by renderHandler, must call generateGraphics first
    public int[] getPixels(){
        if(pixels != null){
            return  pixels;
        }
        System.out.println("Rectangle attempted to be drawn before graphics generation!");
        return null;
    }

    //fill the rectangle with a color for debugging
    public void generateGraphics(int color){
        pixels = new int[width*height];
        for(int i = 0; i < pixels.length; i ++){
            pixels[i] = color;
        }
    }

    //only a border this time
    public void generateGraphics(int border, int color){
        pixels = new int[width * height];
        for(int y = 0; y < height; y ++){
            for(int x = 0; x < width; x++){
                if(x < border || x >= width - border || y < border || y >= height - border){ pixels[x + y * width] = color;}
                else{
                    pixels[x + y * width] = Game.clear;
                }
            }
        }
    }


    //From here on out its getter and setter methods

    public int getX() {
        return xPosition;
    }

    public void setX(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getY() {
        return yPosition;
    }

    public void setY(int yPosition) {
        this.yPosition = yPosition;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
