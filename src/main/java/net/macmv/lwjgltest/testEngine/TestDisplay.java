package net.macmv.lwjgltest.testEngine;

import net.macmv.lwjgltest.engine.*;
import net.macmv.lwjgltest.entity.Entity;
import net.macmv.lwjgltest.model.TextureModel;
import net.macmv.lwjgltest.shaders.StaticShader;
import org.joml.Vector3f;

public class TestDisplay {

  public static void main(String... args) {
    ModelLoader l = new ModelLoader();
    Render r = new Render();
    InputManager input = new InputManager(r);
    StaticShader shader = new StaticShader();

    TextureModel model = new TextureModel(OBJLoader.load("model.obj", l), l.loadTexture("modelTex.png"));
    model.setRoughness(0);
    model.setDamping(10);
    Entity e = new Entity(model);

    Light light = new Light(new Vector3f(0, 5, 0), new Vector3f(1, 1, 1));

    e.getTransform().setTranslation(0, 0, -10);

    while (r.getDisplay().isOpen()) {
      e.getTransform().rotate((float) Math.toRadians(1), 0, 1, 0);
      input.update();
      r.prepare();
      r.render(e, shader, light);
      r.update();
    }

    shader.dispose();
    l.dispose();
    r.dispose();
  }
}
