
package game.experimental.app;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;

import game.experimental.engine.QuadTree;
import game.experimental.gl.*;
import game.experimental.gl.Shader.ShaderException;
import game.experimental.gl.Program.ProgramException;
import game.experimental.gl.Shader.ShaderType;
import game.experimental.utils.BoundingBox;
import game.experimental.utils.Matrix4x4F;
import game.experimental.utils.Vector2F;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;
import static org.lwjgl.opengl.GL46.*;

import java.util.ArrayList;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;

public class App {
	private GameWindow gameWindow;

	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        // bind GLFW error output to cerr
		GLFWErrorCallback.createPrint(System.err).set();

		

        if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");
		// create the main output window
		// this may be recreated if options are changed (such as window size of vsync)
		gameWindow = new GameWindow(1280, 720, "OpenGL Output window", true);

		GL.createCapabilities();

        glEnable(GL_MULTISAMPLE);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);  

		loop();

		gameWindow.destroy();

		glfwSetErrorCallback(null).free();
		// Terminate GLFW and free the error callback
		glfwTerminate();
	}

	private void loop() {

		Gizmos.initialize();

		Texture texture, texture2, textureWall1, textureWall2;
		try {
			texture = new Texture("./assets/textures/texture_steel.psd");
			texture2 = new Texture("./assets/textures/texture_pawn.psd");
			textureWall1 = new Texture("./assets/textures/texture_wall_line.psd");
			textureWall2 = new Texture("./assets/textures/texture_wall_corner.psd");
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

        QuadTree<Integer> qt = new QuadTree<Integer>(null, new BoundingBox(new Vector2F(-300.f, -300.f), new Vector2F(600.f, 600.f)));

		final int testBoxCount = 1000;
		BoundingBox[] boxes = new BoundingBox[testBoxCount];

		Random r = new Random();
        for (int i = 0; i < testBoxCount; i++) {
			boxes[i] = new BoundingBox(new Vector2F(r.nextFloat() * 600.f - 300.f, r.nextFloat() * 600.f - 300.f), new Vector2F(3.f, 3.f));
            if (false == qt.insert(i, boxes[i])) {
                System.out.println("failed to insert " + i);
            }
        }

		Matrix4x4F projection = Matrix4x4F.projectionOrthographic(-640.f, -360.f, 640.f, 360.f, 0.f, 1.0f);

		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while ( !gameWindow.shouldClose() ) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            glClearColor(229.f / 255.f, 207.f / 255.f, 163.f / 255.f, 1.0f);

			Gizmos.beginDrawing(projection);

			drawQuadTree(qt);

			Vector2F mousePos = gameWindow.getCursorPosition().subtract(new Vector2F(640.f, 360.f));

			ArrayList<Integer> queryInts = new ArrayList<Integer>();
			BoundingBox xdd = new BoundingBox(mousePos.subtract(new Vector2F(50.f, 50.f)), new Vector2F(100.f, 100.f));
			final float[] rectColor3 = {0.8f, 0.1f, 0.1f, 1.0f};
			Gizmos.drawBoundingBox(xdd, rectColor3);
			qt.query(xdd, queryInts);
			for (int i : queryInts) {
				final float[] rectColor = {0.1f, 0.4f, 0.1f, 1.0f};
				Gizmos.drawBoundingBox(boxes[i], rectColor);
			}
			
			gameWindow.present();
			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
		}

		//circle.destroy();
		//program.destroy();
		//quad.destroy();

		Gizmos.destroy();
	}

	private void drawQuadTree(QuadTree<Integer> qt) {
		final float[] rectColor = {0.1f, 0.1f, 0.1f, 1.0f};
		Gizmos.drawBoundingBox(qt.getRange(), rectColor);

		// for (QuadTree<Integer>.Node<Integer> obj : qt.getObjects()) {
		// 	final float[] rectColor2 = {0.1f, 0.5f, 0.1f, 1.0f};
		// 	Gizmos.drawBoundingBox(obj.getBoundingBox(), rectColor2);
		// }

		if (qt.getChildren() != null) {
			for (int i = 0; i < 4; i++) {
				drawQuadTree(qt.getChildren()[i]);
			}
		}

	}

	public static void main(String[] args) {
		new App().run();
	}

}
