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

  public BasicShader(String vertexFile, String fragmentFile) {
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
