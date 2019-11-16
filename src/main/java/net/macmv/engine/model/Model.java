package net.macmv.engine.model;

public class Model {

  private final RawModel model;
  private final int tex;

  private float roughness = 0;
  private float damping = 100;

  public Model(RawModel model, int tex) {
    this.model = model;
    this.tex = tex;
  }

  public RawModel getModel() {
    return model;
  }

  public int getTex() {
    return tex;
  }

  public float getRoughness() {
    return roughness;
  }

  public float getDamping() {
    return damping;
  }

  public void setRoughness(float roughness) {
    this.roughness = roughness;
  }

  public void setDamping(float damping) {
    this.damping = damping;
  }
}
