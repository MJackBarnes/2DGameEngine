package Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Tiles {

    //Sheet to get the tile textures from
    public SpriteSheet spriteSheet;

    //List of all tiles
    private ArrayList<Tile> tileList = new ArrayList<Tile>();

    public Tiles(File tilesFile, SpriteSheet spriteSheet){

        //Assign the spriteSheet
        this.spriteSheet = spriteSheet;

        //Mandatory try catch block
        try {

            //read the tiles file
            Scanner sc = new Scanner(tilesFile);

            //continue reading until done with last line
            while (sc.hasNextLine()){

                //get the next line
                String line = sc.nextLine();

                //check if the line is a comment
                if(!line.startsWith("//")){

                    //split the string for reading the tile information
                    String[] splitString = line.split("-");

                    //create and add the tile to the tileList
                    String tileName = splitString[0];
                    int spriteX = Integer.parseInt(splitString[1]);
                    int spriteY = Integer.parseInt(splitString[2]);
                    Tile tile = new Tile(tileName, spriteSheet.getSprite(spriteX, spriteY));
                    tileList.add(tile);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Draw the tile to the RenderHandler
    public void renderTile(int tileID, RenderHandler rh, int xPosition, int yPosition, int xZoom, int yZoom){
        if(tileList.size() > tileID){
            rh.renderSprite(tileList.get(tileID).sprite, xPosition, yPosition, xZoom, yZoom);
        }
    }

    //Struct for ease of management with tiles
    class Tile{
        public String tileName;
        public Sprite sprite;

        public Tile(String tileName, Sprite sprite){
            this.sprite = sprite;
            this.tileName = tileName;
        }
    }
}
