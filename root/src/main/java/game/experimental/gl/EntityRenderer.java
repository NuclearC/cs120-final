package game.experimental.gl;

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
     */
    void draw(Vector2F position, Vector2F size);


}
