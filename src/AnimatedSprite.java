public class AnimatedSprite extends Sprite implements GameObject{

    public int[][] sprites;
    public int x, y, currentSprite;
    public long timer = 0;
    public long updateFrames;

    public AnimatedSprite(int[] ids, SpriteSheet s, long updateFrames) {
        super(s);
        currentSprite = 0;
        sprites = new int[ids.length][pixels.length];
        this.updateFrames = updateFrames;
        for(int i = 0; i < sprites.length; i ++){
            sprites[i] = sheet.getSprite(ids[i] % (sheet.SIZEX/sheet.spriteSizeX), (int)(ids[i]/((sheet.SIZEX/sheet.spriteSizeX)))).getPixels();
        }
    }

    @Override
    public void render(RenderHandler renderer, int zoomX, int zoomY) {
    }

    public void updateSprite(){
        currentSprite ++;
        pixels = sprites[currentSprite];
        if(currentSprite == sprites.length - 1){
            currentSprite = 0;
        }
    }

    @Override
    public void update(Game game) {
        timer ++;
        if(timer >= updateFrames){
            System.out.println("Yeet");
            timer = 0;
            updateSprite();
        }
    }
}
