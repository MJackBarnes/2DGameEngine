package Game;

public class Player implements GameObject {

    //TODO fill out this class

    //TODO find a suitable sprite for the player

    //temporary way to display the player
    Rectangle playerRectangle;

    //The players speed
    int speed = 10;

    public Player(){
        //TODO change sprite
        playerRectangle = new Rectangle(32, 16, 16, 32);
        playerRectangle.generateGraphics(3, 0xff00ff90);
    }

    @Override
    public void render(RenderHandler renderer, int zoomX, int zoomY) {
        //TODO write the rendering method
        renderer.renderRectangle(playerRectangle, zoomX, zoomX);
    }

    @Override
    public void update(Game game) {
        //TODO write the updating method
    }

    public void move(int direction){}
}
