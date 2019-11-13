package net.macmv.lwjgltest.engine;

import net.macmv.lwjgltest.entity.Entity;
import net.macmv.lwjgltest.shaders.StaticShader;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Render {
  private final Display display;

  private final float FOV = 70;
  private final float NEAR_PLANE = 0.1f;
  private final float FAR_PLANE = 100;

  private final Matrix4f projectionMatrix;

  private final Camera cam;

  public Render() {
    display = new Display();
    projectionMatrix = new Matrix4f();
    this.cam = new Camera();

    float aspectRatio = (float) display.getWidth() / (float) display.getHeight();
    projectionMatrix.setPerspective(FOV, aspectRatio, NEAR_PLANE, FAR_PLANE);

    GL11.glEnable(GL11.GL_CULL_FACE);
    GL11.glCullFace(GL11.GL_BACK);
  }

  public Camera getCam() {
    return cam;
  }

  public void prepare() {
    GL11.glEnable(GL11.GL_DEPTH_TEST);
    GL11.glClearColor(0.0f, 1.0f, 0.0f, 0.0f);
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    display.sync(60);
  }

  public void update() {
    display.update();
  }

  public void dispose() {
    display.close();
  }

  public void render(Entity model, StaticShader shader, Light light) {
    shader.start();
    GL30.glBindVertexArray(model.getModel().getModel().getVaoID());
    GL20.glEnableVertexAttribArray(0);
    GL20.glEnableVertexAttribArray(1);
    GL20.glEnableVertexAttribArray(2);
    shader.loadProjection(projectionMatrix);
    shader.loadView(cam.getViewMatrix());
    shader.loadTransform(model.getTransform());
    shader.loadRoughness(model.getModel().getRoughness(), model.getModel().getDamping());
    shader.loadLight(light);
    GL13.glActiveTexture(GL13.GL_TEXTURE0);
    GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getModel().getTex());
    GL11.glDrawElements(GL11.GL_TRIANGLES, model.getModel().getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
    GL20.glDisableVertexAttribArray(0);
    GL20.glDisableVertexAttribArray(1);
    GL20.glDisableVertexAttribArray(2);
    GL30.glBindVertexArray(0);
    shader.stop();
  }

  public Display getDisplay() {
    return display;
  }
}
