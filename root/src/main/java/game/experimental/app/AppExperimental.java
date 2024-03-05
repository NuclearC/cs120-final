package game.experimental.app;

import game.experimental.engine.Client;
import game.experimental.engine.ClientChannel;
import game.experimental.engine.Entity;
import game.experimental.gl.*;
import game.experimental.utils.BoundingBox;
import game.experimental.utils.Logger;
import game.experimental.utils.Matrix4x4F;
import game.experimental.utils.Vector2F;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;

public class AppExperimental {
    private GameWindow gameWindow;

    private ClientChannel myChannel;

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
        } catch(Shader.ShaderException e) {
            System.out.println("Shader Compile error");
            System.out.println(e.getMessage());
            return;
        } catch (Program.ProgramException e) {
            System.out.println("Program link error");
            System.out.println(e.getMessage());
            return;
        } catch (Exception e) {
            System.out.println("Exception");
            System.out.println(e.getMessage());

            return;
        }
        Logger.log("hello world");


        Matrix4x4F projection = Matrix4x4F.projectionOrthographic(-640.f, -360.f, 640.f, 360.f, 0.f, 1.0f);

        Camera c = new Camera(1280, 720);

        Client myClient = new Client(1);

        myClient.setConnectionMode(Client.ConnectionMode.REMOTE);  // this is where the rest of the structure is being decided

        ClientChannel myChannel = myClient.getChannelInstance();




        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( !gameWindow.shouldClose() ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            glClearColor(229.f / 255.f, 207.f / 255.f, 163.f / 255.f, 1.0f);

            PlayerRenderer playerRenderer = PlayerRenderer.getSingleton();

            ArrayList<Entity> viewBoxData = myChannel.getViewBoxData();

            for(Entity ent: viewBoxData) {
                playerRenderer.draw(c, ent.getAngle(), ent.getPosition(), ent.getSize());
            }

            Gizmos.beginDrawing(projection);

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


    public static void main(String[] args) {
        new AppExperimental().run();
    }

}
