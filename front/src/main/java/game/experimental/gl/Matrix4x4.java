package game.experimental.gl;

/**
 * Implements a 4x4 matrix of floats
 */
public class Matrix4x4 {
    private float[] values;

    /**
     * Default constructor creates a zero matrix.
     */
    public Matrix4x4() {
        values = new float[16];
    }

    /**
     * Creates a matrix with diagonal with all values set to the argument
     * 
     * @param v The value of the entries in diagonal (1.0f for identity)
     */
    public Matrix4x4(float v) {
        values = new float[] {
                v, 0.f, 0.f, 0.f,
                0.f, v, 0.f, 0.f,
                0.f, 0.f, v, 0.f,
                0.f, 0.f, 0.f, v
        };
    }

    /**
     * Constructs a matrix with the array specified.
     * Implicit copy is not performed.
     * 
     * @param v The values of the matrix
     */
    public Matrix4x4(float[] v) {
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
     * 
     * @param other the matrix to multiply with.
     * @return the newly resulting matrix.
     */
    public Matrix4x4 multiply(Matrix4x4 other) {

        return new Matrix4x4();
    }

    /**
     * Perform matrix multiplication with scalar.
     * Does not change the matrix of the callee object.
     * 
     * @param other the scalar to multiply with.
     * @return the newly resulting matrix.
     */
    public Matrix4x4 multiply(float scalar) {
        return new Matrix4x4();
    }
}
