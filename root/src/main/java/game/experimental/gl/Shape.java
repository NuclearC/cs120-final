
package game.experimental.gl;

import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.system.MemoryStack.stackPush;

import java.nio.FloatBuffer;

/**
 * Represents a graphics shape for drawing on the screen.
 */
public class Shape {
    private int vertexArray = 0;
    private int vertexBuffer = 0;
    private int vertexCount = 0;

    public static float[] buildQuad() {
        return new float[] {
                1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 0.f,
                0.0f, 0.0f, 0.f, 0.f,
                1.0f, 1.0f, 1.0f, 1.0f,
                0.0f, 1.0f, 0.f, 1.0f,
                0.0f, 0.0f, 0.f, 0.f
        };
    }

    public static float[] buildCircle(int steps) {
        float[] res = new float[steps * 12];

        int offset = 0;

        for (int i = 0; i < steps; i++) {
            float startAngle = 2.0f * (float)Math.PI / (float)steps * (float)i;
            float endAngle = 2.0f * (float)Math.PI / (float)steps * (float)(i + 1);

            res[offset++] = (float)Math.cos(startAngle);
            res[offset++] = (float)Math.sin(startAngle);
            res[offset++] = (float)Math.cos(startAngle) * 0.5f + 0.5f;
            res[offset++] = (float)Math.sin(startAngle) * 0.5f + 0.5f;

            res[offset++] = (float)Math.cos(endAngle);
            res[offset++] = (float)Math.sin(endAngle);
            res[offset++] = (float)Math.cos(endAngle) * 0.5f + 0.5f;
            res[offset++] = (float)Math.sin(endAngle) * 0.5f + 0.5f;
            
            res[offset++] = 0.f;
            res[offset++] = 0.f;
            res[offset++] = 0.5f;
            res[offset++] = 0.5f;
        }

        return res;
    }

    public Shape(float[] vertices) {
        vertexArray = glCreateVertexArrays();
        vertexBuffer = glCreateBuffers();

        vertexCount = vertices.length / 4;

        glBindVertexArray(vertexArray);

        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);

        try (MemoryStack stack = stackPush()) {
            FloatBuffer pVertices = stack.floats(vertices);

            glBufferData(GL_ARRAY_BUFFER, pVertices, GL_STATIC_DRAW);
        }

        glVertexAttribPointer(0, 2, GL_FLOAT, false, 4 * 4, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 2, GL_FLOAT, false, 4 * 4, 2 * 4);
        glEnableVertexAttribArray(1);

        glBindVertexArray(0);
    }

    public void draw() {
        glBindVertexArray(vertexArray);
        glDrawArrays(GL_TRIANGLES, 0, vertexCount);
    }

    public void destroy() {
        glDeleteVertexArrays(vertexArray);
        glDeleteBuffers(vertexBuffer);
    }
}
