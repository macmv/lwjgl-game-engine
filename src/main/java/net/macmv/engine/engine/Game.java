package net.macmv.engine.engine;

import net.macmv.engine.entity.Entity;
import net.macmv.engine.input.BasicInput;
import net.macmv.engine.input.InputManager;
import net.macmv.engine.model.ModelBatch;
import net.macmv.engine.shaders.FlatShader;
import org.joml.Vector3f;

public abstract class Game {
  private final Render render;
  private final Light light;
  private final ModelBatch modelBatch;
  private InputManager input;

  public Game() {
    render = new Render();
    light = new Light(new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
    input = new BasicInput(this, render.getCam());
    modelBatch = new ModelBatch();
  }

  public void run() {
    init();
    while (render.getDisplay().isOpen()) {
      input.update();
      render.prepare();
      update();
      render.update();
    }
    dispose();
  }

  private void dispose() {
    modelBatch.dispose();
    render.dispose();
  }

  public void render(Entity object) {
    render.render(object, light);
  }

  public ModelBatch getModelBatch() {
    return modelBatch;
  }

  public Render getRender() {
    return render;
  }

  public InputManager getInput() {
    return input;
  }

  public void setInput(InputManager input) {
    this.input = input;
  }

  public Camera getCamera() {
    return render.getCam();
  }

  public abstract void init();

  public abstract void update();
}
