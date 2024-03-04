package game.experimental.gl;

import game.experimental.utils.Vector2F;
import game.experimental.utils.Matrix4x4F;

public class Camera {
    private Matrix4x4F projection;
    private Matrix4x4F view;

    public Camera(float width, float height) {
        this.projection = Matrix4x4F.projectionOrthographic(-width / 2.0f, -height / 2.0f, 
            width / 2.0f, height / 2.0f, 0.f, 1.f);

        this.view = new Matrix4x4F(1.0f);
    }

    public Matrix4x4F getProjectionView() {
        return projection.multiply(view);
    }

    public void setPosition(Vector2F position) {

        this.view = Matrix4x4F.transformTranslate(position);

    }

}
