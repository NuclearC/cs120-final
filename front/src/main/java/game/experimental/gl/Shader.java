package game.experimental.gl;

import static org.lwjgl.opengl.GL46.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Shader {
    public class ShaderException extends Exception {
        public ShaderException(String message) {
            super(message);
        }
    }

    public enum ShaderType {
        VERTEX_SHADER,
        FRAGMENT_SHADER
    }
    
    private int shader = 0;
    private ShaderType type;

    public Shader(ShaderType type, String filename) throws ShaderException, IOException {
        this.type = type;

        switch (type) {
            case VERTEX_SHADER:
                shader = glCreateShader(GL_VERTEX_SHADER);
                break;
            case FRAGMENT_SHADER:
                shader = glCreateShader(GL_FRAGMENT_SHADER);
                break;    
        }

        {

            File file = new File(filename);
            byte [] rawData = new byte[(int)file.length()];
            (new FileInputStream (file)).read(rawData);

            glShaderSource(shader, new String(rawData));
        }

        glCompileShader(shader);

        int[] shaderIv = { 0 };
        glGetShaderiv(shader, GL_COMPILE_STATUS, shaderIv);

        if (shaderIv[0] == GL_FALSE) {
            glGetShaderiv(shader, GL_INFO_LOG_LENGTH, shaderIv);
	        String errorMessage = glGetShaderInfoLog(shader, shaderIv[0]);

            throw new ShaderException("OpenGL Shader compilation failed: " + errorMessage);
        }
    }

    public ShaderType getType() {
        return type;
    }

    public int getInternalHandle() {
        return shader;
    }

    public void destroy() {
        if (shader != 0) {
            glDeleteShader(shader);
        }
    }
}
