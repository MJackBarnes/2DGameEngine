package Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Map implements GameObject{

    //Declare an object to hold all tiles used in this map
    private Tiles tileset;

    //The tile id to fill all undeclared spaces
    private int fillTileID = -1;

    //ArrayList of all tiles specifically declared
    private ArrayList<MappedTile> mappedTiles = new ArrayList<MappedTile>();

    public Map(File mapFile, Tiles tileSet){

        //assign the Tileset
        this.tileset = tileSet;

        //Must have try catch to function
        try{
            //Read map file
            Scanner sc = new Scanner(mapFile);
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
                    String[] splitString = line.split("-");

                    //create and add the mappedtile to the arraylist
                    if(splitString.length >= 3){
                       MappedTile mappedTile = new MappedTile(Integer.parseInt(splitString[0]),
                                                                Integer.parseInt(splitString[1]),
                                                                Integer.parseInt(splitString[2]));
                        mappedTiles.add(mappedTile);
                    }
                }
            }
        }catch(FileNotFoundException e){
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
            for (int y = 0; y < camera.getHeight(); y+=tileset.spriteSheet.spriteSizeY) {
                for (int x = 0; x < camera.getWidth(); x+=tileset.spriteSheet.spriteSizeX) {
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
