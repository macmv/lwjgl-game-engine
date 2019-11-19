package net.macmv.engine.shaders;

import net.macmv.engine.engine.Light;
import org.joml.Matrix4f;

public abstract class BasicShader extends ShaderProgram {
  private int transform;
  private int projection;
  private int view;
  private int lightPos;
  private int lightColor;
  private int roughness;
  private int damping;
  private int time;

  public BasicShader(String vertexFile, String geometryFile, String fragmentFile) {
    super(vertexFile, geometryFile, fragmentFile);
  }

  @Override
  protected void getAllUniformLocations() {
    transform = getUniformLocation("transform");
    projection = getUniformLocation("projection");
    view = getUniformLocation("view");
    lightPos = getUniformLocation("lightPos");
    lightColor = getUniformLocation("lightColor");
    roughness = getUniformLocation("roughness");
    damping = getUniformLocation("damping");
    time = getUniformLocation("time");
  }

  @Override
  protected void bindAttributes() {
    bindAttribute(0, "position");
    bindAttribute(1, "texCord");
    bindAttribute(2, "normal");
  }

  public void loadRoughness(float roughness, float damping) {
    loadFloat(this.roughness, roughness);
    loadFloat(this.damping, damping);
  }

  public void loadLight(Light light) {
    loadVector(lightColor, light.getColor());
    loadVector(lightPos, light.getPos());
  }

  public void loadTransform(Matrix4f mat) {
    loadMatrix(transform, mat);
  }

  public void loadProjection(Matrix4f mat) {
    loadMatrix(projection, mat);
  }

  public void loadView(Matrix4f mat) {
    loadMatrix(view, mat);
  }

  public void loadTime() {
    loadInt(time, (int) (System.currentTimeMillis() % 100000));
  }
}
