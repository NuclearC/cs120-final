package game.experimental.gl;

import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.stb.STBImage.*;

import java.nio.ByteBuffer;

public class Texture {
    private int texture = 0;

    public Texture(String filename) throws Exception {
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
            throw new Exception("Unable to load texture file: " + filename);
        }

        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, texture);
    }

    public void destroy() {
        glDeleteTextures(texture);
    }
}
