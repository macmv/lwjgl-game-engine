package net.macmv.engine.engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Camera {
  private final Matrix4f viewMatrix;
  private float yaw = 0; // x rot in radians
  private float pitch = 0; // y rot in radians
  private final Vector3f loc = new Vector3f();

  public Camera() {
    viewMatrix = new Matrix4f();
  }

  public void move(float forward, float up, float strafe) {
    float cosYaw = (float) Math.cos(yaw);
    float sinYaw = (float) Math.sin(yaw);
    loc.add(sinYaw * forward, 0, cosYaw * forward);
    loc.add(cosYaw * strafe, 0, -sinYaw * strafe);
    loc.add(0, -up, 0);
    updateMatrix();
  }

  public void rotate(float dx, float dy) {
    yaw += Math.toRadians(dx / 10f);
    pitch += Math.toRadians(dy / 10f);
    updateMatrix();
  }

  private void updateMatrix() {
    float cosPitch = (float) Math.cos(pitch);
    float sinPitch = (float) Math.sin(pitch);
    float cosYaw = (float) Math.cos(yaw);
    float sinYaw = (float) Math.sin(yaw);
    Vector3f x = new Vector3f(cosYaw, 0, -sinYaw);
    Vector3f y = new Vector3f(sinYaw * sinPitch, cosPitch, cosYaw * sinPitch);
    Vector3f z = new Vector3f(sinYaw * cosPitch, -sinPitch, cosPitch * cosYaw);
    viewMatrix.set(
            new Vector4f(x.x, y.x, z.x, 0),
            new Vector4f(x.y, y.y, z.y, 0),
            new Vector4f(x.z, y.z, z.z, 0),
            new Vector4f(x.dot(loc), y.dot(loc), z.dot(loc), 1));
    System.out.println("mat: " + viewMatrix);
  }

  public Matrix4f getViewMatrix() {
    return viewMatrix;
  }
}
