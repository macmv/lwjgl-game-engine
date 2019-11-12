package net.macmv.lwjgltest.shaders;

import org.joml.Matrix4f;

public class StaticShader extends ShaderProgram {

  private static final String vertexFile = "src/main/java/net/macmv/lwjgltest/shaders/vertexShader.glsl";
  private static final String fragmentFile = "src/main/java/net/macmv/lwjgltest/shaders/fragmentShader.glsl";

  private int transform;
  private int projection;
  private int view;

  public StaticShader() {
    super(vertexFile, fragmentFile);
  }

  @Override
  protected void getAllUniformLocations() {
    transform = super.getUniformLocation("transform");
    projection = super.getUniformLocation("projection");
    view = super.getUniformLocation("view");
  }

  @Override
  protected void bindAttributes() {
    super.bindAttribute(0, "position");
    super.bindAttribute(1, "texCord");
  }

  public void loadTransform(Matrix4f mat) {
    super.loadMatrix(transform, mat);
  }

  public void loadProjection(Matrix4f mat) {
    super.loadMatrix(projection, mat);
  }

  public void loadView(Matrix4f mat) {
    super.loadMatrix(view, mat);
  }
}
