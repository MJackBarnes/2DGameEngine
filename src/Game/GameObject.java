package Game;

public interface GameObject {
    void render(RenderHandler renderer, int zoomX, int zoomY);

    void update(Game game);
}
