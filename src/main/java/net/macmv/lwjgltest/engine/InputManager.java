package net.macmv.lwjgltest.engine;

import org.lwjgl.glfw.GLFW;

public class InputManager {

  private final Camera cam;
  private final Render render;

  private double oldX, oldY;

  public InputManager(Render render) {
    this.cam = render.getCam();
    this.render = render;
    oldX = render.getDisplay().getMouseX();
    oldY = render.getDisplay().getMouseY();
  }

  public void update() {
    float speed = 0.02f;
    if (render.getDisplay().isKeyDown(GLFW.GLFW_KEY_W)) {
      cam.move(0, 0, -speed);
    }
    if (render.getDisplay().isKeyDown(GLFW.GLFW_KEY_A)) {
      cam.move(-speed, 0, 0);
    }
    if (render.getDisplay().isKeyDown(GLFW.GLFW_KEY_S)) {
      cam.move(0, 0, speed);
    }
    if (render.getDisplay().isKeyDown(GLFW.GLFW_KEY_D)) {
      cam.move(speed, 0, 0);
    }
    if (render.getDisplay().isKeyDown(GLFW.GLFW_KEY_SPACE)) {
      cam.move(0, speed, 0);
    }
    if (render.getDisplay().isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) {
      cam.move(0, -speed, 0);
    }
    double newX = render.getDisplay().getMouseX();
    double newY = render.getDisplay().getMouseY();
    float dx = (float) (oldX - newX);
    float dy = (float) (oldY - newY);
    oldX = newX;
    oldY = newY;
    if (render.getDisplay().isMouseDown()) {
//      cam.rotate(dx, dy);
    }
  }
}
