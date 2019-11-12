package net.macmv.lwjgltest.model;

public class TextureModel {

  private final RawModel model;
  private final int tex;

  public TextureModel(RawModel model, int tex) {
    this.model = model;
    this.tex = tex;
  }

  public RawModel getModel() {
    return model;
  }

  public int getTex() {
    return tex;
  }
}
