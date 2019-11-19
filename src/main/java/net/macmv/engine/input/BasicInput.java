package net.macmv.engine.input;

import net.macmv.engine.engine.Camera;
import net.macmv.engine.engine.Game;
import org.lwjgl.glfw.GLFW;

public class BasicInput extends InputManager {
  private final Camera cam;

  private double oldX, oldY;

  public BasicInput(Game game, Camera cam) {
    super(game);
    this.cam = cam;
  }

  @Override
  public void process() {
    float speed = 0.1f;
    if (isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL)) {
      speed *= 5;
    }
    if (isKeyDown(GLFW.GLFW_KEY_W)) {
      cam.move(speed, 0, 0);
    }
    if (isKeyDown(GLFW.GLFW_KEY_A)) {
      cam.move(0, 0, speed);
    }
    if (isKeyDown(GLFW.GLFW_KEY_S)) {
      cam.move(-speed, 0, 0);
    }
    if (isKeyDown(GLFW.GLFW_KEY_D)) {
      cam.move(0, 0, -speed);
    }
    if (isKeyDown(GLFW.GLFW_KEY_SPACE)) {
      cam.move(0, speed, 0);
    }
    if (isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) {
      cam.move(0, -speed, 0);
    }
    float newX = getMouseX();
    float newY = getMouseY();
    float dx = (float) (oldX - newX);
    float dy = (float) (oldY - newY);
    oldX = newX;
    oldY = newY;
    if (isMouseDown()) {
      cam.rotate(dx, dy);
    }
  }
}
