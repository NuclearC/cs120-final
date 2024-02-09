package game.experimental.gl;

import static org.lwjgl.opengl.GL46.*;

/**
 * Implements an OpenGL program.
 */
public class Program {

    /**
     * Used to be thrown only from Program linking errors.
     */
    public class ProgramException extends Exception {
        public ProgramException(String message) {
            super(message);
        }
    }

    private int program = 0;

    /**
     * Create an empty OpenGL program.
     * Call Program.destroy() whenever this program is not needed anymore.
     */
    public Program() {
        program = glCreateProgram();
    }

    /**
     * Links OpenGL program together. Shaders must had been attached prior.
     * @throws ProgramException
     */
    public void link() throws ProgramException {
        glLinkProgram(program);

        int[] programIv = { 0 };
        glGetProgramiv(program, GL_LINK_STATUS, programIv);

        if (programIv[0] == GL_FALSE) {
            glGetProgramiv(program, GL_INFO_LOG_LENGTH, programIv);
	        String errorMessage = glGetProgramInfoLog(program, programIv[0]);

            throw new ProgramException("OpenGL Program link failed: " + errorMessage);
        }
    }

    /**
     * Attaches a Shader object to the program.
     * @param shader reference to the compiled Shader object
     * @see Shader
     */
    public void attachShader(Shader shader) {
        glAttachShader(program, shader.getInternalHandle());
    }

    /**
     * Return a location index to a uniform with the specified name.
     * The uniform must be declared within the shader GLSL code.
     * Name is case sensitive. 
     * @param name the name of the uniform variable.
     * @return 
     */
    public int getUniform(String name) {
        return glGetUniformLocation(program, name);
    }

    /**
     * Update the graphics pipeline state to use this program. 
     */
    public void use() {
        glUseProgram(program);
    }

    /**
     * Destroy the program.
     */
    public void destroy() {
        glDeleteProgram(program);
    }
}
