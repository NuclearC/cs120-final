package game.experimental.gl;

import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.stb.STBImage.*;

import java.nio.ByteBuffer;

/**
 * Represesents a texture (an image) residing within the GPU. 
 */
public class Texture {

    public class TextureLoadException extends Exception {
        public TextureLoadException(String message) {
            super(message);
        }
    }

    private int texture = 0;

    /**
     * Create a texture from a file
     * @param filename the path to the image file
     * @throws TextureLoadException
     */
    public Texture(String filename) throws TextureLoadException {
        texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);	
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        int [] textureWidth = {0},
             textureHeight = {0},
             textureChannels = {0};
            
        ByteBuffer buf = stbi_load(filename, textureWidth, textureHeight, textureChannels, 4);
        if (buf != null) {

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, textureWidth[0], textureHeight[0], 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);
            glGenerateMipmap(GL_TEXTURE_2D);

        } else {
            throw new TextureLoadException(ExceptionMessages.TEXTURE_NOT_FOUND);
        }

        glBindTexture(GL_TEXTURE_2D, 0);
    }

    /**
     * Bind the texture to the GL_TEXTURE_2D
     */
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, texture);
    }

    /**
     * Destroy the texture. 
     */
    public void destroy() {
        glDeleteTextures(texture);
    }
}
