package game.experimental.gl.renderers;

import game.experimental.gl.Camera;
import game.experimental.utils.Vector2F;

/**
 * Represents a renderer flyweight for in-game entities. 
 */
public interface EntityRenderer {

    /**
     * Loads the textures, vertex buffers and other data associated with this entity. 
     */
    void load();
    
    /**
     * Draws the entity in the currently active OpenGL context. 
     * @param camera the Camera specifiying projection and view. 
     * @param rotation the rotation of the entity along Z axis.
     * @param position the position of the entity.
     * @param size the size of the entity
     */
    void draw(Camera camera, float rotation, Vector2F position, Vector2F size);


}
