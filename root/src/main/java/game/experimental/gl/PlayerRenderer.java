package game.experimental.gl;

import game.experimental.utils.Vector2F;

public class PlayerRenderer implements EntityRenderer {

    private static PlayerRenderer instance = null;

    private Texture playerTexture;
    private Shape playerShape;
    private Program playerMaterial;

    @Override
    public void load() {
        try {
            playerTexture = new Texture("./assets/textures/texture_pawn.psd");

        } catch (Exception e) {
            
        }
    }
    
    @Override
    public void draw(Vector2F position, Vector2F size) {}

    private PlayerRenderer() {
        this.load();
    }

    public static PlayerRenderer getSingleton() {
        if (instance == null)
            instance = new PlayerRenderer();
        
        return instance;
    }
}
