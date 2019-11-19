package net.macmv.engine.model;

import net.macmv.engine.shaders.BasicShader;
import net.macmv.engine.shaders.ShaderLoader;

public class Model {

  private final BasicShader shader;
  private final RawModel model;
  private final int tex;
  private float roughness = 1;
  private float damping = 100;

  public Model(RawModel model, ShaderLoader shaderLoader, Type type, int tex) {
    this.model = model;
    this.tex = tex;
    shader = shaderLoader.get(type);
  }

  public BasicShader getShader() {
    return shader;
  }

  public RawModel getRawModel() {
    return model;
  }

  public int getTex() {
    return tex;
  }

  public float getRoughness() {
    return roughness;
  }

  public void setRoughness(float roughness) {
    this.roughness = roughness;
  }

  public float getDamping() {
    return damping;
  }

  public void setDamping(float damping) {
    this.damping = damping;
  }

  public enum Type {
    STATIC, FLAT, WATER;
  }
}
