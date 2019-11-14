package net.macmv.engine.engine;

import net.macmv.engine.entity.Entity;
import net.macmv.engine.model.ModelBatch;
import net.macmv.engine.shaders.StaticShader;
import org.joml.Vector3f;

public abstract class Game {
  private final Render render;
  private final StaticShader shader;
  private final Light light;
  private final InputManager input;
  private final ModelBatch modelBatch = new ModelBatch();

  public Game() {
    render = new Render();
    shader = new StaticShader();
    input = new InputManager(render);
    light = new Light(new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
  }

  public void run() {
    init();
    while (render.getDisplay().isOpen()) {
      render.prepare();
      input.update();
      update();
      render.update();
    }
    dispose();
  }

  private void dispose() {
    modelBatch.dispose();
    render.dispose();
    shader.dispose();
  }

  public void render(Entity object) {
    render.render(object, shader, light);
  }

  public ModelBatch getModelBatch() {
    return modelBatch;
  }

  public abstract void init();

  public abstract void update();
}
