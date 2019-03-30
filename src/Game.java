import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Game extends JFrame implements Runnable{

    public int zoomX = 3;
    public int zoomY = 3;

    //Create a color value to indicate a clear pixel
    public static int clear = 0xFFFF00DC;

    //Create a canvas to draw on
    private Canvas canvas = new Canvas();

    //Declare an object to handle the rendering of components
    private RenderHandler renderHandler;

    //Declare an object holding all sprites
    private SpriteSheet sheet;

    //Declare an object holding all tiles
    public Tiles tiles1;

    //Declare an object to draw and edit the map
    private Map map;

    //Declare the player object
    private Player player;

    //Add ability to take in user input
    private KeyboardHandler keyboardHandler = new KeyboardHandler(this);

    //An ArrayList of type GameObject for ease of making game loops
    ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();

    private MouseHandler mouseHandler = new MouseHandler(this);

    public Game(){

        //Configure the JFrame
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(0, 0, 1000, 800);
        setLocationRelativeTo(null);
        setResizable(false);

        //Add the canvas instantiated earlier to the JFrame
        add(canvas);

        //make the JFrame visible
        setVisible(true);

        //Make a BufferStrategy for easy buffering in our game
        canvas.createBufferStrategy(3);

        //Instantiate the RenderHandler made earlier
        renderHandler = new RenderHandler(getWidth(), getHeight());

        //Making our spritesheet for the file "Tiles1.png"
        BufferedImage tiles1Image = loadImage("res/Images/Tiles1.png");
        sheet = new SpriteSheet(tiles1Image, 0xffffff);
        sheet.loadSprites(16, 16);

        //Create the object holding our tiles
        tiles1 = new Tiles(new File("./src/Tiles1.txt"), sheet);

        //Create and read our map
        map = new Map(new File("./src/Map.txt"), tiles1);

        //Create the player object
        player = new Player(sheet);

        //Add objects to the render and update queue
        //MUST BE ADDED IN CORRECT ORDER
        gameObjects.add(map);
        gameObjects.add(player);

        //Attach the handlers
        canvas.addKeyListener(keyboardHandler);
        canvas.addMouseListener(mouseHandler);
        canvas.addMouseMotionListener(mouseHandler);
    }

    public KeyboardHandler getKeyboardHandler(){return keyboardHandler;}

    public static void main(String[] args) {
        Game g = new Game();
        Thread gameThread = new Thread(g, "GameThread");
        gameThread.start();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double deltaSeconds = 0;
        double nanoSecondConversion = 1000000000.0 / 60;
        while(true){
            long now = System.nanoTime();
            deltaSeconds += (now - lastTime) / nanoSecondConversion;
            while(deltaSeconds >= 1){
                update();
                deltaSeconds = 0;
            }
            render();
            lastTime = now;
        }
    }

    public void update(){
        for (int i = 0; i < gameObjects.size(); i++) {
            gameObjects.get(i).update(this);
        }
        if(keyboardHandler.control()){
            if(keyboardHandler.keys[KeyEvent.VK_S])map.save();
        }
    }

    public void render(){
        //Get graphics from the buffer
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();

        //Super.paint() to avoid problems
        super.paint(graphics);

        //Render all gameObjects to the buffer
        for (int gameObject = 0; gameObject < gameObjects.size(); gameObject++) {
            gameObjects.get(gameObject).render(renderHandler, zoomX, zoomY);
        }

        //Write to the current buffer
        renderHandler.render(graphics);

        //Memory management (Sounds like an oxymoron in java)
        graphics.dispose();

        //Display the next buffer
        bufferStrategy.show();

        renderHandler.clear();
    }

    private BufferedImage loadImage(String path) {
        BufferedImage image;
        try {
            image = ImageIO.read(Game.class.getResource(path));
            BufferedImage formatted = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            formatted.getGraphics().drawImage(image, 0, 0, null);
            return formatted;
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public void leftClick(int x, int y){
        x = (int)(Math.floor((x + renderHandler.getViewport().xPosition)/((double)tiles1.spriteSheet.spriteSizeX * zoomX)));
        y = (int)(Math.floor((y + renderHandler.getViewport().yPosition)/((double)tiles1.spriteSheet.spriteSizeY * zoomY)));
        map.placeTile(2, x, y);
    }

    public void rightClick(int x, int y){
        x = (int)(Math.floor((x + renderHandler.getViewport().xPosition)/((double)tiles1.spriteSheet.spriteSizeX * zoomX)));
        y = (int)(Math.floor((y + renderHandler.getViewport().yPosition)/((double)tiles1.spriteSheet.spriteSizeY * zoomY)));
        map.removeTile(x, y);
    }

    public RenderHandler getRenderer(){
        return renderHandler;
    }

    public MouseHandler getMouseHandler(){
        return mouseHandler;
    }

    public Map getMap(){
        return map;
    }

    public Player getPlayer() {
        return player;
    }
}
