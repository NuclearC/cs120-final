package game.experimental.utils;

/**
 * Implements a 4x4 matrix of floats
 */
public class Matrix4x4F {
    private float[] values;

    /**
     * Creates an orthographic projection matrix.
     * 
     * @param left   X coordinate of the left-top corner.
     * @param top    Y coordinate of the left-top corner.
     * @param right  X coordinate of the right-bottom corner.
     * @param bottom Y coordinate of the right-bottom corner.
     * @param near   Minimum Z coordinate.
     * @param far    Maximum Z coordinate (depth)
     * @return the resulting matrix.
     */
    static public Matrix4x4F projectionOrthographic(float left, float top, float right, float bottom, float near,
            float far) {

        return new Matrix4x4F(new float[] {
                2.0f / (right - left),
                0.f,
                0.f,
                0.f,

                0.f,
                2.0f / (top - bottom),
                0.f,
                0.f,

                0.f,
                0.f,
                -2.0f / (far - near),
                0.f,

                (right + left) / (left - right),
                (top + bottom) / (bottom - top),
                (far + near) / (near - far),
                1.f
        });
    }

    static public Matrix4x4F transformScale(float v) {
        return new Matrix4x4F(new float[] {  
            v, 0.f, 0.f, 0.f,
            0.f, v, 0.f, 0.f,
            0.f, 0.f, 1.f, 0.f,
            0.f, 0.f, 0.f, 1.f
        });
    }

    static public Matrix4x4F transformTranslate(float x, float y) {
        return new Matrix4x4F(new float[] {  
            1.f, 0.f, 0.f, 0.f,
            0.f, 1.f, 0.f, 0.f,
            0.f, 0.f, 1.f, 0.f,
            x, y, 0.f, 1.f
        });
    }

    static public Matrix4x4F transformRotate(float angle) {
        return new Matrix4x4F(new float[] {  
            (float)Math.cos(angle), -(float)Math.sin(angle), 0.f, 0.f,
            (float)Math.sin(angle),  (float)Math.cos(angle), 0.f, 0.f,
            0.f, 0.f, 1.f, 0.f,
            0.f, 0.f, 0.f, 1.f
        });
    }
    /**
     * Default constructor creates a zero matrix.
     */
    public Matrix4x4F() {
        values = new float[16];
    }

    /**
     * Creates a matrix with diagonal with all values set to the argument
     * 
     * @param v The value of the entries in diagonal (1.0f for identity)
     */
    public Matrix4x4F(float v) {
        values = new float[] {
                v, 0.f, 0.f, 0.f,
                0.f, v, 0.f, 0.f,
                0.f, 0.f, 1.0f, 0.f,
                0.f, 0.f, 0.f, 1.0f
        };
    }

    /**
     * Constructs a matrix with the array specified.
     * Implicit copy is not performed.
     * 
     * @param v The values of the matrix
     */
    public Matrix4x4F(float[] v) {
        values = v;
    }

    /**
     * Get the array of floats representing this matrix.
     * 
     * @return reference to the array
     */
    public float[] getRaw() {
        return values;
    }

    /**
     * Get an entry at the specified index
     * For performance reasons, no checks for bounds are done.
     * 
     * @param r the row of the entry
     * @param c the column of the entry
     * @return the value of the entry
     */
    public float getAt(int r, int c) {
        return values[r * 4 + c];
    }

    /**
     * Set an entry at the specified position to the specified value.
     * 
     * @param r the row of the entry
     * @param c the column of the entry
     * @param v the new value of the entry
     */
    public void setAt(int r, int c, float v) {
        values[r * 4 + c] = v;
    }

    /**
     * Perform matrix multiplication.
     * Does not change the matrix of the callee object.
     * DONT DO THIS. USE MULTIPLICATION IN SHADERS INSTEAD.
     * 
     * @param other the matrix to multiply with.
     * @return the newly resulting matrix.
     */
    public Matrix4x4F multiply(Matrix4x4F other) {
        Matrix4x4F res = new Matrix4x4F();

        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                for (int i = 0; i < 4; i++)
                    res.values[r * 4 + c] += getAt(i, c) * other.getAt(r, i);
            }
        }

        return res;
    }

    /**
     * Perform matrix multiplication with scalar.
     * Does not change the matrix of the callee object.
     * 
     * @param other the scalar to multiply with.
     * @return the newly resulting matrix.
     */
    public Matrix4x4F multiply(float scalar) {
        Matrix4x4F res = new Matrix4x4F();
        for (int i = 0; i < 16; i++)
            res.values[i] = values[i] * scalar;
        return res;
    }
}
