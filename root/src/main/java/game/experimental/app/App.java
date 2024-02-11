
package game.experimental.app;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;

import game.experimental.gl.*;
import game.experimental.gl.Shader.ShaderException;
import game.experimental.gl.Program.ProgramException;
import game.experimental.gl.Shader.ShaderType;

import static org.lwjgl.opengl.GL46.*;
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
        Program program = new Program();

		Texture texture, texture2;
		try {
			Shader vertexShader = new Shader(ShaderType.VERTEX_SHADER, "./assets/shaders/textured_vs.glsl");
			Shader fragmentShader = new Shader(ShaderType.FRAGMENT_SHADER, "./assets/shaders/textured_fs.glsl");
			program.attachShader(fragmentShader);
			program.attachShader(vertexShader);
			program.link();
			vertexShader.destroy();
			fragmentShader.destroy();

			texture = new Texture("./assets/textures/texture_steel.psd");
			texture2 = new Texture("./assets/textures/texture_pawn.psd");
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

		Shape circle = new Shape(Shape.buildCircle(36));
		Shape quad = new Shape(Shape.buildQuad());

		Matrix4x4 proj = Matrix4x4.projectionOrthographic(-640.f, -360.f, 640.f, 360.f, 0.f, 1.0f);
		Matrix4x4 pvm2 = proj.multiply(Matrix4x4.transformTranslate(64.0f, 0.f).multiply(Matrix4x4.transformScale(64.0f)));
		Matrix4x4 pvm = proj.multiply(Matrix4x4.transformRotate(0.44f).multiply(Matrix4x4.transformScale(64.0f)));

		float circleColor[] = new float[]{1.0f, 0.f, 0.f, 1.0f};
		float quadColor[] = new float[]{0.f, 1.0f, 0.f, 0.5f};
		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while ( !gameWindow.shouldClose() ) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            glClearColor(229.f / 255.f, 207.f / 255.f, 163.f / 255.f, 1.0f);

			
			texture.bind();
			program.use();
			//pvm = Shape.buildProjection(320.f, 240.f, 50.0f,30.0f, 100.f, 0.f);
			glUniformMatrix4fv(program.getUniform("pvm"), false, pvm.getRaw());
			glUniform4fv(program.getUniform("color"), quadColor);
			quad.draw();

			texture2.bind();
			//pvm = Shape.buildProjection(320.f, 240.f, 100.0f, 100.0f, 0.f, 0.f);
			glUniformMatrix4fv(program.getUniform("pvm"), false, pvm2.getRaw());
			glUniform4fv(program.getUniform("color"), circleColor);
			circle.draw();

			gameWindow.present();
			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
		}

		//circle.destroy();
		program.destroy();
		quad.destroy();
	}

	public static void main(String[] args) {
		new App().run();
	}

}
