package game.experimental.gl;

import static org.lwjgl.opengl.GL46.*;

public class Program {
    public class ProgramException extends Exception {
        public ProgramException(String message) {
            super(message);
        }
    }

    private int program = 0;

    public Program() {
        program = glCreateProgram();
    }

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

    public void attachShader(Shader shader) {
        glAttachShader(program, shader.getInternalHandle());
    }

    public int getUniform(String name) {
        return glGetUniformLocation(program, name);
    }

    public void use() {
        glUseProgram(program);
    }

    public void destroy() {
        glDeleteProgram(program);
    }
}
