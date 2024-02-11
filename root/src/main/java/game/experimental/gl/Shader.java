package game.experimental.gl;

import static org.lwjgl.opengl.GL46.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Implements an OpenGL shader.
 */
public class Shader {
    /**
     * Used to be thrown only from Shader compilation errors.
     */
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

    /**
     * Creates an OpenGL Shader. 
     * Reads the text data from the file specified by 'filename' and compiles the shader.
     * Throws an exception if compilation fails.
     * Must be destroyed manually by calling Shader.destroy()
     * @param type Type of the shader, currently supported only VERTEX_SHADER and FRAGMENT_SHADER. 
     * @param filename Absolute or relative path to the file containing the GLSL code. 
     * @throws ShaderException
     * @throws IOException
     */
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

        // read the data from the text file
        {
            File file = new File(filename);
            byte [] rawData = new byte[(int)file.length()];
            (new FileInputStream (file)).read(rawData);

            glShaderSource(shader, new String(rawData));
        }

        glCompileShader(shader);

        // stupid C style API
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

    /**
     * Returns a GLuint typed number that OpenGL internally uses to identify this shader. 
     * @return
     */
    public int getInternalHandle() {
        return shader;
    }

    /**
     * Destroys the OpenGL shader. 
     */
    public void destroy() {
        if (shader != 0) {
            glDeleteShader(shader);
        }
    }
}
