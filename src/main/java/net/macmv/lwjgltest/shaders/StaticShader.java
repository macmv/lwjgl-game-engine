package net.macmv.lwjgltest.shaders;

import net.macmv.lwjgltest.engine.Light;
import org.joml.Matrix4f;

public class StaticShader extends ShaderProgram {

  private static final String vertexFile = "src/main/java/net/macmv/lwjgltest/shaders/vertexShader.glsl";
  private static final String fragmentFile = "src/main/java/net/macmv/lwjgltest/shaders/fragmentShader.glsl";

  private int transform;
  private int projection;
  private int view;
  private int lightPos;
  private int lightColor;
  private int roughness;
  private int damping;

  public StaticShader() {
    super(vertexFile, fragmentFile);
  }

  @Override
  protected void getAllUniformLocations() {
    transform = super.getUniformLocation("transform");
    projection = super.getUniformLocation("projection");
    view = super.getUniformLocation("view");
    lightPos = super.getUniformLocation("lightPos");
    lightColor = super.getUniformLocation("lightColor");
    roughness = super.getUniformLocation("roughness");
    damping = super.getUniformLocation("damping");
  }

  @Override
  protected void bindAttributes() {
    super.bindAttribute(0, "position");
    super.bindAttribute(1, "texCord");
    super.bindAttribute(2, "normal");
  }

  public void loadRoughness(float roughness, float damping) {
    super.loadFloat(this.roughness, roughness);
    super.loadFloat(this.damping, damping);
  }

  public void loadLight(Light light) {
    super.loadVector(lightColor, light.getColor());
    super.loadVector(lightPos, light.getPos());
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
