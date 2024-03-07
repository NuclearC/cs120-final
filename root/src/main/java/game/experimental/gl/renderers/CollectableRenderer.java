package game.experimental.gl.renderers;

import java.io.IOException;

import static org.lwjgl.opengl.GL20.glUniform4fv;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
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
 * Represents a flyweight for rendering CollectableEntities. 
 */
public final class CollectableRenderer implements EntityRenderer {

    private static CollectableRenderer INSTANCE;

    private Texture texture;
    private Shape shape;
    private Program program;
    private float[] modulation;

    /**
     * Load the necessary assets for drawing the collectable. 
     */
    @Override
    public void load() {
        Logger.log("Loading data for CollectableRenderer...");
        try {
            texture = new Texture("./assets/textures/texture_collect1.psd");
        } catch (Exception e) {
            Logger.error("Failed to load texture; " + e.getMessage());
        }

        shape = new Shape(Shape.buildQuad());
        modulation = new float[]{1.f, 1.f, 1.f, 1.f};
        
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
     * Draw the collectable with the specified Camera (view and projection), position and size
     */
    @Override
    public void draw(Camera camera, float rotation, Vector2F position, Vector2F size) {
        program.use();

        int pvmLocation = program.getUniform("pvm");
        int colorLocation = program.getUniform("color");
        glUniform4fv(colorLocation, modulation);

        Matrix4x4F model = Matrix4x4F.transformTranslate(position).multiply(Matrix4x4F.transformRotate(rotation).multiply(Matrix4x4F.transformScale(size)));
        Matrix4x4F pvm = camera.getProjectionView().multiply(model);
        glUniformMatrix4fv(pvmLocation, false, pvm.getRaw());
        texture.bind();
        shape.draw();
    }

    /**
     * Only use singleton. 
     */
    private CollectableRenderer() {
        this.load();
    }

    /**
     * Acquire the singleton instance for CollectableRenderer.
     * @return the instance
     */
    public static CollectableRenderer getSingleton() {
        if (INSTANCE == null)
            INSTANCE = new CollectableRenderer();
        
        return INSTANCE;
    }

    @Override
    public void setColorModulation(float r, float g, float b) {
        modulation[0] = r;
        modulation[1] = g;
        modulation[2] = b;
    }

    @Override
    public void setAlphaModulation(float alpha) {
        modulation[3] = alpha;
    }
}
