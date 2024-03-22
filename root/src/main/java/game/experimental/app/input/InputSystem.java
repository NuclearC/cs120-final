package game.experimental.app.input;

import static org.lwjgl.glfw.GLFW.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;

import game.experimental.app.GameWindow;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

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
        SETTINGS_OPEN("SettingsOpen"),
//        SETTINGS_CLOSE("SettingsClose"),
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
    private boolean cursorState;

    public static InputSystem getInstance() {
        if (INSTANCE == null)
            INSTANCE = new InputSystem();

        return INSTANCE;
    }

    private InputSystem() {
        keyToInputMap = new HashMap<Integer, RegisteredInput>();
        keyToInputMap.put(GLFW_KEY_W, RegisteredInput.MOVE_UP);
        keyToInputMap.put(GLFW_KEY_A, RegisteredInput.MOVE_LEFT);
        keyToInputMap.put(GLFW_KEY_S, RegisteredInput.MOVE_DOWN);
        keyToInputMap.put(GLFW_KEY_D, RegisteredInput.MOVE_RIGHT);
        keyToInputMap.put(GLFW_KEY_M, RegisteredInput.SETTINGS_OPEN);
//        keyToInputMap.put(GLFW_KEY_ESCAPE, RegisteredInput.SETTINGS_CLOSE);

        inputToStateMap = new EnumMap<RegisteredInput, Boolean>(RegisteredInput.class);
    }    private float userInputAngle;


    public void setMapping(Map<Integer, RegisteredInput> keyToInputMap) {
        this.keyToInputMap = keyToInputMap;
    }

    public boolean getInputState(RegisteredInput input) {
        Boolean res = inputToStateMap.get(input);
        return (res == null) ? false : res;
    }

    private void onKeyPress(int key) {
        RegisteredInput input = keyToInputMap.get((Integer)key);
        if (input != null)
            inputToStateMap.put(input, true);
    }

    private void onKeyRelease(int key) {
        RegisteredInput input = keyToInputMap.get(key);
        if (input != null)
            inputToStateMap.put(input, false);
    }

    private void setCursorPosition(double x, double y) {
        cursorX = (float)x;
        cursorY = (float)y;
    }
    public void onMouseClick(Boolean state) {
        this.cursorState = state;
    }

    public float getCursorX() {
        return cursorX;
    }

    public float getCursorY() {
        return cursorY;
    }

    public void updateShooting(){
        if (cursorState){
            inputToStateMap.put(RegisteredInput.ATTACK1, true);
        }else{
            inputToStateMap.put(RegisteredInput.ATTACK1, false);
        }
    }

    public void setName(String name){
        System.out.println("new name should be " + name);
    }
    public void subscribeWindow(GameWindow window) {
        long windowHandle = window.getNativeHandle();

        glfwSetKeyCallback(windowHandle, new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (action == GLFW_PRESS)
                {
                    onKeyPress(key);
                }
                else if (action == GLFW_RELEASE)
                    onKeyRelease(key);


            }
         });

         glfwSetCursorPosCallback(windowHandle, new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double x, double y) {
                setCursorPosition(x, y);
            }
         });

         glfwSetMouseButtonCallback(windowHandle, new GLFWMouseButtonCallbackI() {
             @Override
             public void invoke(long l, int i, int i1, int i2) {

                 if(i == 0 && i1 == 1 && i2 == 0){
                     onMouseClick(true);}
                 else
                     onMouseClick(false);

             }
         });

    }
}
