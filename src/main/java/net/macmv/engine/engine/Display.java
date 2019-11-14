package net.macmv.engine.engine;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

public class Display {
  private long window;

  private long variableYieldTime, lastTime;

  private IntBuffer pWidth, pHeight;
  private DoubleBuffer px, py;

  public Display() {
    if (!GLFW.glfwInit()) {
      throw new IllegalStateException("Unable to initialize GLFW");
    }

    window = GLFW.glfwCreateWindow(300, 300, "Hello World!", MemoryUtil.NULL, MemoryUtil.NULL);
    if (window == MemoryUtil.NULL) {
      throw new RuntimeException("Could not create window!");
    }

    GLFW.glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
      if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
        GLFW.glfwSetWindowShouldClose(window, true);
      }
    });

    GLFW.glfwMakeContextCurrent(window);
    GLFW.glfwShowWindow(window);
    GL.createCapabilities();

    try (MemoryStack stack = MemoryStack.stackPush()) {
      pWidth = stack.mallocInt(1); // int*
      pHeight = stack.mallocInt(1); // int*

      // Get the window size passed to glfwCreateWindow
      GLFW.glfwGetWindowSize(window, pWidth, pHeight);

      // Get the resolution of the primary monitor
      GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

      // Center the window
      GLFW.glfwSetWindowPos(
              window,
              (vidmode.width() - pWidth.get(0)) / 2,
              (vidmode.height() - pHeight.get(0)) / 2
      );
    }
    px = BufferUtils.createDoubleBuffer(1);
    py = BufferUtils.createDoubleBuffer(1);
    GLFW.glfwGetCursorPos(window, px, py);
  }

  public void update() {
    GLFW.glfwGetWindowSize(window, pWidth, pHeight);
    int width = pWidth.get(0);
    int height = pHeight.get(0);
    GLFW.glfwGetCursorPos(window, px, py);
    GL11.glViewport(0, 0, width, height);

    GLFW.glfwSwapBuffers(window);

    GLFW.glfwPollEvents();
  }

  public void sync(int fps) {
    if (fps <= 0) return;

    long sleepTime = 1000000000 / fps; // nanoseconds to sleep this frame
    // yieldTime + remainder micro & nano seconds if smaller than sleepTime
    long yieldTime = Math.min(sleepTime, variableYieldTime + sleepTime % (1000 * 1000));
    long overSleep = 0; // time the sync goes over by

    try {
      while (true) {
        long t = System.nanoTime() - lastTime;

        if (t < sleepTime - yieldTime) {
          Thread.sleep(1);
        } else if (t < sleepTime) {
          // burn the last few CPU cycles to ensure accuracy
          Thread.yield();
        } else {
          overSleep = t - sleepTime;
          break; // exit while loop
        }
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      lastTime = System.nanoTime() - Math.min(overSleep, sleepTime);

      // auto tune the time sync should yield
      if (overSleep > variableYieldTime) {
        // increase by 200 microseconds (1/5 a ms)
        variableYieldTime = Math.min(variableYieldTime + 200 * 1000, sleepTime);
      } else if (overSleep < variableYieldTime - 200 * 1000) {
        // decrease by 2 microseconds
        variableYieldTime = Math.max(variableYieldTime - 2 * 1000, 0);
      }
    }
  }

  public boolean isOpen() {
    return !GLFW.glfwWindowShouldClose(window);
  }

  public void close() {

  }

  public int getWidth() {
    return pWidth.get(0);
  }

  public int getHeight() {
    return pHeight.get(0);
  }

  public boolean isKeyDown(int key) {
    return GLFW.glfwGetKey(window, key) == 1;
  }

  public double getMouseX() {
    return px.get(0);
  }

  public double getMouseY() {
    return py.get(0);
  }

  public boolean isMouseDown() {
    return GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_1) == GLFW.GLFW_PRESS;
  }
}
