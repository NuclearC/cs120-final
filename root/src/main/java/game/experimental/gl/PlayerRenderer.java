package game.experimental.gl;

import java.io.IOException;

import game.experimental.gl.Program.ProgramException;
import game.experimental.gl.Shader.ShaderException;
import game.experimental.gl.Shader.ShaderType;
import game.experimental.utils.Logger;
import game.experimental.utils.Vector2F;

public final class PlayerRenderer implements EntityRenderer {

    private static PlayerRenderer INSTANCE;

    private Texture texture;
    private Shape shape;
    private Program program;

    @Override
    public void load() {
        try {
            texture = new Texture("./assets/textures/texture_pawn.psd");
        } catch (Exception e) {
            Logger.error("Failed to load texture; " + e.getMessage());
        }

        shape = new Shape(Shape.buildQuad());
        
        try {
            Shader vertexShader = new Shader(ShaderType.VERTEX_SHADER, "./assets/shaders/textured_vs.glsl");
            Shader fragmentShader = new Shader(ShaderType.FRAGMENT_SHADER, "./assets/shaders/textured_fs.glsl");
            program.attachShader(fragmentShader);
            program.attachShader(vertexShader);
            program.link();
            vertexShader.destroy();
            fragmentShader.destroy();

        } catch(ShaderException e) {
            Logger.error("Shader compile error " + e.getMessage());
			return;
		} catch (ProgramException e) {
            Logger.error("Program linking error " + e.getMessage());
			return;
		} catch (IOException e) {
            Logger.error("I/O error " + e.getMessage());
			return;
        }
    }
    
    @Override
    public void draw(Vector2F position, Vector2F size) {}

    private PlayerRenderer() {
        this.load();
    }

    public static PlayerRenderer getSingleton() {
        if (INSTANCE == null)
            INSTANCE = new PlayerRenderer();
        
        return INSTANCE;
    }
}
