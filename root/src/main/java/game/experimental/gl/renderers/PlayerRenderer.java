package game.experimental.gl.renderers;

import java.io.IOException;

import static org.lwjgl.opengl.GL46.*;

import game.experimental.gl.Camera;
import game.experimental.gl.Program;
import game.experimental.gl.Shader;
import game.experimental.gl.Shape;
import game.experimental.gl.Texture;
import game.experimental.gl.Program.ProgramException;
import game.experimental.gl.Shader.ShaderException;
import game.experimental.gl.Shader.ShaderType;
import game.experimental.utils.Logger;
import game.experimental.utils.Matrix4x4F;
import game.experimental.utils.Vector2F;

/**
 * Represents a flyweight for rendering PlayerEntities. 
 */
public final class PlayerRenderer implements EntityRenderer {

    private static PlayerRenderer INSTANCE;

    private Texture texture;
    private Texture barrelTexture;
    private Shape shape;
    private Program program;

    /**
     * Load the necessary assets for drawing the player. 
     */
    @Override
    public void load() {
        Logger.log("Loading data for PlayerRenderer...");
        try {
            texture = new Texture("./assets/textures/texture_pawn.psd");
            barrelTexture = new Texture("./assets/textures/texture_steel.psd");
        } catch (Exception e) {
            Logger.error("Failed to load texture; " + e.getMessage());
        }

        shape = new Shape(Shape.buildQuad());
        
        try {
            program = new Program();
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
    
    /**
     * Draw the player with the specified Camera (view and projection), position and size
     */
    @Override
    public void draw(Camera camera, float rotation, Vector2F position, Vector2F size) {
        program.use();

        int pvmLocation = program.getUniform("pvm");

        Vector2F barrelSize = new Vector2F(size.getX() * 0.5f, size.getY() * 0.3f);
        Vector2F barrelPosition = new Vector2F(size.getX() * 0.7f, size.getY() * 0.5f - barrelSize.getY() * 0.5f);

        Matrix4x4F model = Matrix4x4F.transformTranslate(barrelPosition).multiply(Matrix4x4F.transformScale(barrelSize));
        model = Matrix4x4F.transformTranslate(position).multiply(Matrix4x4F.transformRotate(rotation).multiply(model));
        Matrix4x4F pvm = camera.getProjectionView().multiply(model);
        
        glUniformMatrix4fv(pvmLocation, false, pvm.getRaw());
        barrelTexture.bind();
        shape.draw();

        model = Matrix4x4F.transformTranslate(position).multiply(Matrix4x4F.transformRotate(rotation).multiply(Matrix4x4F.transformScale(size)));

        pvm = camera.getProjectionView().multiply(model);
        glUniformMatrix4fv(pvmLocation, false, pvm.getRaw());
        texture.bind();
        shape.draw();
    }

    /**
     * Only use singleton. 
     */
    private PlayerRenderer() {
        this.load();
    }

    /**
     * Acquire the singleton instance for PlayerRenderer.
     * @return the instance
     */
    public static PlayerRenderer getSingleton() {
        if (INSTANCE == null)
            INSTANCE = new PlayerRenderer();
        
        return INSTANCE;
    }
}
