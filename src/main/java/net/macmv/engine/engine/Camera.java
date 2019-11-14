package net.macmv.engine.engine;

import org.joml.Matrix4f;

public class Camera {
  private final Matrix4f viewMatrix;

  public Camera() {
    viewMatrix = new Matrix4f();
  }

  public void move(float dx, float dy, float dz) {
    viewMatrix.translate(-dx, -dy, -dz);
  }

  public Matrix4f getViewMatrix() {
    return viewMatrix;
  }
}
