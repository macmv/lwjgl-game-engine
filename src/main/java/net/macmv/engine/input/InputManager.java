package net.macmv.engine.input;

import net.macmv.engine.engine.Camera;
import net.macmv.engine.engine.Game;
import net.macmv.engine.engine.Render;

public abstract class InputManager {

  private final Render render;

  public InputManager(Game game) {
    this.render = game.getRender();
  }

  public abstract void process();

  public void update() {
    process();
  }

  public boolean isKeyDown(int key) {
    return render.getDisplay().isKeyDown(key);
  }

  public float getMouseX() {
    return (float) render.getDisplay().getMouseX();
  }

  public float getMouseY() {
    return (float) render.getDisplay().getMouseY();
  }

  public boolean isMouseDown() {
    return render.getDisplay().isMouseDown();
  }
}
