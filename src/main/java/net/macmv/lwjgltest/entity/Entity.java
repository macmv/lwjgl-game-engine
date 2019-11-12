package net.macmv.lwjgltest.entity;

import net.macmv.lwjgltest.model.TextureModel;
import org.joml.Matrix4f;

public class Entity {
  private final TextureModel model;
  private final Matrix4f transform;

  public Entity(TextureModel model, Matrix4f transform) {
    this.model = model;
    this.transform = transform;
  }

  public TextureModel getModel() {
    return model;
  }

  public Matrix4f getTransform() {
    return transform;
  }
}
