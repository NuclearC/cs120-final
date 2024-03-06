package game.experimental.app;
import game.experimental.app.input.InputSystem;
import game.experimental.engine.*;
import game.experimental.gl.*;
import game.experimental.gl.renderers.CollectableRenderer;
import game.experimental.gl.renderers.PlayerRenderer;
import game.experimental.utils.Logger;
import game.experimental.utils.Vector2F;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
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
        
        Logger.log("hello world");

        Camera c = new Camera(1280, 720);

        Client myClient = new Client(1);

        myClient.setConnectionMode(Client.ConnectionMode.LOCAL);  // this is where the rest of the structure is being decided

        InputSystem input = InputSystem.getInstance();
        input.subscribeWindow(gameWindow);

        ClientChannel myChannel = myClient.getChannelInstance();
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.f
        while ( !gameWindow.shouldClose() ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            glClearColor(229.f / 255.f, 207.f / 255.f, 163.f / 255.f, 1.0f);

            Gizmos.beginDrawing(c.getProjectionView());

            myChannel.update();

            c.setViewport(myChannel.getViewport().getCenter(), myChannel.getViewportZoom());

            Gizmos.drawBoundingBox(myChannel.getViewport(), new float[]{1.f, 0.f, 0.f, 1.f});

            PlayerRenderer playerRenderer = PlayerRenderer.getSingleton();
            CollectableRenderer collectableRenderer = CollectableRenderer.getSingleton();

            ArrayList<Entity> viewBoxData = myChannel.getViewBoxData();

            if (viewBoxData != null)
                for(Entity ent : viewBoxData) {
                    if (ent.getClass().getName() == "game.experimental.engine.PlayerEntity")
                        playerRenderer.draw(c, ent.getAngle(), ent.getPosition(), ent.getSize());
                    else if (ent.getClass().getName() == "game.experimental.engine.StaticCollectableEntity")
                        collectableRenderer.draw(c, ent.getAngle(), ent.getPosition(), ent.getSize());
                }


            gameWindow.present();
            
            gameWindow.pollEvents();

            int commandKey = PlayerCommand.RIGHT.set(0);   // needs to be generated using PlayerCommand set methods according to pressed keys
            commandKey = PlayerCommand.UP.set(commandKey);   // needs to be generated using PlayerCommand set methods according to pressed keys


            System.out.println("cscsdc " + commandKey);
            myChannel.setUserCommandKey(commandKey);
            myChannel.setCursorPosition(new Vector2F());

            myChannel.sendControlData();

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
