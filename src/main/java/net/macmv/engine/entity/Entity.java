package net.macmv.engine.entity;

import net.macmv.engine.model.Model;
import org.joml.Matrix4f;

public class Entity {
  private final Model model;
  private final Matrix4f transform;

  public Entity(Model model, Matrix4f transform) {
    this.model = model;
    this.transform = transform;
  }

  public Entity(Model model) {
    this.model = model;
    this.transform = new Matrix4f();
  }

  public Model getModel() {
    return model;
  }

  public Matrix4f getTransform() {
    return transform;
  }
}
