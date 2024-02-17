package game.experimental.gl;

import game.experimental.gl.Program.ProgramException;
import game.experimental.gl.Shader.ShaderException;
import game.experimental.gl.Shader.ShaderType;
import game.experimental.utils.*;

import static org.lwjgl.opengl.GL46.*;

/**
 * Gizmos implements a set of methods for drawing debugging graphics on screen.
 */
public class Gizmos {

    private static Shape quadShape = null;
    private static Shape circleShape = null;

    private static Program program = null;

    private static Matrix4x4F projectionView = null;

    /**
     * Creates all necessary vertex arrays for drawing.
     */
    public static void initialize() {
        program = new Program();
        circleShape = new Shape(Shape.buildCircle(16));
        quadShape = new Shape(Shape.buildQuad());

        try {
            Shader vertexShader = new Shader(ShaderType.VERTEX_SHADER, "./assets/shaders/gizmo_vs.glsl");
            Shader fragmentShader = new Shader(ShaderType.FRAGMENT_SHADER, "./assets/shaders/gizmo_fs.glsl");
            program.attachShader(fragmentShader);
            program.attachShader(vertexShader);
            program.link();
            vertexShader.destroy();
            fragmentShader.destroy();
        } catch(ShaderException e) {
			System.out.println("Shader Compile error");
			System.out.println(e.getMessage());
			return;
		} catch (ProgramException e) {
			System.out.println("Program link error");
			System.out.println(e.getMessage());
			return;
		} catch (Exception e) {
			System.out.println("Exception");
			System.out.println(e.getMessage());
			return;
		}
    }

    public static void beginDrawing(Matrix4x4F projectionView) {
        Gizmos.projectionView = projectionView;

        program.use();
    }

    public static void fillRectangle(Vector2F start, Vector2F size, float[] color) {
        Matrix4x4F pvm = projectionView.multiply(Matrix4x4F.transformTranslate(start).multiply(Matrix4x4F.transformScale(size)));

        glUniformMatrix4fv(program.getUniform("pvm"), false, pvm.getRaw());
        glUniform4fv(program.getUniform("color"), color);

        quadShape.draw();
    }
    
    public static void drawBoundingBox(BoundingBox box, float[] color) {
        // top side
        fillRectangle(box.getPosition(), new Vector2F(box.getSize().getX(), 1.0f), color);
        // left
        fillRectangle(box.getPosition(), new Vector2F(1.0f, box.getSize().getY()), color);
        // right
        fillRectangle(box.getTopRight(), new Vector2F(1.0f, box.getSize().getY()), color);
        // right
        fillRectangle(box.getBottomLeft(), new Vector2F(box.getSize().getX(), 1.0f), color);
    }

    /**
     * Release all the created vertex arrays.
     */
    public static void destroy() {
        quadShape.destroy();
        circleShape.destroy();
    }

}
