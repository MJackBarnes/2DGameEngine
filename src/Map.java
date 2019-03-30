import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Map implements GameObject{

    //Declare an object to hold all tiles used in this map
    private Tiles tileset;

    private HashMap<Integer, String> comments = new HashMap<>();

    //The tile id to fill all undeclared spaces
    private int fillTileID = -1;
    private File mapFile;

    //ArrayList of all tiles specifically declared
    private ArrayList<MappedTile> mappedTiles = new ArrayList<MappedTile>();

    public Map(File mapFile, Tiles tileSet){

        this.mapFile = mapFile;

        //assign the Tileset
        this.tileset = tileSet;

        //Must have try catch to function
        try{
            //Read map file
            Scanner sc = new Scanner(mapFile);
            int currentLine = 0;
            while (sc.hasNextLine()){

                //Get the next line
                String line = sc.nextLine();

                //Make sure the line is not a comment
                if(!line.startsWith("//")){

                    //Check if the line is a command
                    if(line.contains(":")){

                        //Split the command and the data
                        String[] splitString = line.split(":");

                        //Set the fill tile
                        if(splitString[0].equalsIgnoreCase("fill"))
                            fillTileID = Integer.parseInt(splitString[1]);

                        //TODO add more commands?

                        //we don't need to run the rest of this loop so skip back to the beginning
                        continue;
                    }

                    //Split the string to interpret locations
                    String[] splitString = line.split(",");

                    //create and add the mappedtile to the arraylist
                    if(splitString.length >= 3){
                       MappedTile mappedTile = new MappedTile(Integer.parseInt(splitString[0]),
                                                                Integer.parseInt(splitString[1]),
                                                                Integer.parseInt(splitString[2]));
                        mappedTiles.add(mappedTile);
                    }
                }
                else {
                    comments.put(currentLine, line);
                }
                currentLine++;
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void placeTile(int tileId, int tileX, int tileY){
        for (int i = 0; i < mappedTiles.size(); i++) {
            MappedTile t = mappedTiles.get(i);
            if(t.x == tileX && t.y == tileY){
                t.id = tileId;
                return;
            }
        }
        MappedTile mappedTile = new MappedTile(tileId, tileX, tileY);
        mappedTiles.add(mappedTile);
    }

    public void removeTile(int tileX, int tileY){
        for (int i = 0; i < mappedTiles.size(); i++) {
            MappedTile t = mappedTiles.get(i);
            if(t.x == tileX && t.y == tileY){
                mappedTiles.remove(t);
                return;
            }
        }
    }

    public void save(){
        int currentLine = 0;
        if(mapFile.exists()){
            mapFile.delete();
        }
        try {
            mapFile.createNewFile();
            mapFile.setWritable(true);
            PrintWriter printWriter = new PrintWriter(mapFile);

            if(fillTileID >= 0){
                if(comments.containsKey(currentLine)){
                    printWriter.println(comments.get(currentLine));
                }
                currentLine++;
                printWriter.println("Fill:" + fillTileID);
            }
            for(int i = 0; i < mappedTiles.size(); i ++){
                if(comments.containsKey(currentLine)){
                    printWriter.println(comments.get(currentLine));
                }
                MappedTile mappedTile = mappedTiles.get(i);
                printWriter.println(mappedTile.id + "," + mappedTile.x + "," + mappedTile.y);
                currentLine++;
            }
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Draw the map, handles all tiles
    public void render(RenderHandler rh, int xZoom, int yZoom){

        //Set the increments to avoid overlaps or gaps
        int xIncrement = tileset.spriteSheet.spriteSizeX * xZoom;
        int yIncrement = tileset.spriteSheet.spriteSizeY * yZoom;

        //make sure the fillTileId has been set and render the fill tile
        if(fillTileID >= 0){

            //Get a copy of the camera to find viewport
            Rectangle camera = rh.getViewport();

            //For loop to draw the map
            for (int y = camera.getY() - yIncrement -  (camera.yPosition % yIncrement); y < camera.getHeight() + camera.getY(); y+=tileset.spriteSheet.spriteSizeY) {
                for (int x = camera.getX()  - xIncrement - (camera.xPosition % xIncrement); x < camera.getWidth() + camera.getX(); x+=tileset.spriteSheet.spriteSizeX) {
                    tileset.renderTile(fillTileID, rh, x, y, xZoom, yZoom);
                }
            }
        }

        //Render all explicitly declared tiles
        for (int tileIndex = 0; tileIndex < mappedTiles.size(); tileIndex++) {
            MappedTile mappedTile = mappedTiles.get(tileIndex);
            tileset.renderTile(mappedTile.id, rh, mappedTile.x * xIncrement, mappedTile.y * yIncrement, xZoom, yZoom);
        }

    }

    @Override
    public void update(Game game) {
        //TODO does this need anything?
    }

    //struct to hold a tile with an X and Y coordinate
    class MappedTile{
        public int id, x, y;

        public MappedTile(int id, int x, int y){
            this.id = id;
            this.x = x;
            this.y = y;
        }
    }
}
