package net.macmv.lwjgltest.testEngine;

import net.macmv.lwjgltest.engine.InputManager;
import net.macmv.lwjgltest.engine.ModelLoader;
import net.macmv.lwjgltest.engine.OBJLoader;
import net.macmv.lwjgltest.engine.Render;
import net.macmv.lwjgltest.entity.Entity;
import net.macmv.lwjgltest.model.RawModel;
import net.macmv.lwjgltest.model.TextureModel;
import net.macmv.lwjgltest.shaders.StaticShader;
import org.joml.Matrix4f;

public class TestDisplay {

  public static void main(String... args) {
    ModelLoader l = new ModelLoader();
    Render r = new Render();
    InputManager input = new InputManager(r);
    StaticShader shader = new StaticShader();

    RawModel rawModel = OBJLoader.load("model.obj", l);
    int tex = l.loadTexture("modelTex.png");
    TextureModel model = new TextureModel(rawModel, tex);
    Matrix4f transform = new Matrix4f();
    Entity e = new Entity(model, transform);

    transform.setTranslation(0, 0, -10);
    transform.rotate((float) Math.toRadians(90), 0, 1, 0);

    while (r.getDisplay().isOpen()) {
      input.update();
      r.prepare();
      r.render(e, shader);
      r.update();
    }

    shader.dispose();
    l.dispose();
    r.dispose();
  }
}
