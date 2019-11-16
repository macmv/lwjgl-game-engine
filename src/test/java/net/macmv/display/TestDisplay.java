package net.macmv.display;

import net.macmv.engine.engine.Game;
import net.macmv.engine.entity.Entity;

public class TestDisplay extends Game {
  private Entity e;

  public static void main(String... args) {
    new TestDisplay().run();
  }

  @Override
  public void init() {
    getModelBatch().load("model", "/model.obj", "/modelTex.png");
    e = new Entity(getModelBatch().get("model"));
    e.getTransform().setTranslation(0, 0, -10);
  }

  @Override
  public void update() {
    e.getTransform().rotate((float) Math.toRadians(1), 0, 1, 0);
    render(e);
  }
}
