package game.experimental.app.input;

import static org.lwjgl.glfw.GLFW.*;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;

import game.experimental.app.GameWindow;
import game.experimental.utils.Vector2F;

/**
 * Implements functionality for registering user input. 
 */
public class InputSystem {
    private static InputSystem INSTANCE = null;

    public enum RegisteredInput {
        MOVE_UP("Up"),
        MOVE_DOWN("Down"),
        MOVE_LEFT("Left"),
        MOVE_RIGHT("Right"),
        ATTACK1("Attack1"),
        ATTACK2("Attack2");
    
        private String string;
    
        private RegisteredInput(String string) {
            this.string = string;
        }
    
        public String getKey() {
            return this.string;
        }
    }

    private Map<Integer, RegisteredInput> keyToInputMap;
    private EnumMap<RegisteredInput, Boolean> inputToStateMap;

    private float cursorX, cursorY;

    public static InputSystem getInstance() {
        if (INSTANCE == null)
            INSTANCE = new InputSystem();

        return INSTANCE;
    }

    private InputSystem() {
        keyToInputMap = new HashMap<Integer, RegisteredInput>();

        inputToStateMap = new EnumMap<RegisteredInput, Boolean>(RegisteredInput.class);
    }

    public void setMapping(Map<Integer, RegisteredInput> keyToInputMap) {
        this.keyToInputMap = keyToInputMap;
    }

    public boolean getInputState(RegisteredInput input) {
        return inputToStateMap.get(input);
    }

    private void onKeyPress(int key) {
        RegisteredInput input = keyToInputMap.get(key);
        inputToStateMap.put(input, true);
    }

    private void onKeyRelease(int key) {
        RegisteredInput input = keyToInputMap.get(key);
        inputToStateMap.put(input, false);
    }

    private void setCursorPosition(double x, double y) {
        cursorX = (float)x;
        cursorY = (float)y;
    }

    public float getCursorX() {
        return cursorX;
    }

    public float getCursorY() {
        return cursorY;
    }

    public void subscribeWindow(GameWindow window) {
        long windowHandle = window.getNativeHandle();

        glfwSetKeyCallback(windowHandle, new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (action == GLFW_PRESS)
                    onKeyPress(key);
                else if (action == GLFW_PRESS)
                    onKeyRelease(key);
            }
         });

         glfwSetCursorPosCallback(windowHandle, new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double x, double y) {
                setCursorPosition(x, y);
            }
         });
    }
}
