public class Player implements GameObject {

    //TODO fill out this class

    //TODO find a suitable sprite for the player

    //temporary way to display the player
    Rectangle playerRectangle;

    //The players speed
    int speed = 10;

    public Player(SpriteSheet s){
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
        KeyboardHandler keyboardHandler = game.getKeyboardHandler();
        if(keyboardHandler.up())
                playerRectangle.yPosition -= speed;
        if(keyboardHandler.down())
                playerRectangle.yPosition += speed;
        if(keyboardHandler.left())
                playerRectangle.xPosition -= speed;
        if(keyboardHandler.right())
                playerRectangle.xPosition += speed;
        updateCamera(game.getRenderer().getViewport());
    }

    public void updateCamera(Rectangle camera){
        camera.xPosition = playerRectangle.xPosition - (camera.width / 2);
        camera.yPosition = playerRectangle.getY() - (camera.height/2);
    }

}
