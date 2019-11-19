package net.macmv.engine.shaders;

public class StaticShader extends BasicShader {
  private static final String vertexFile = "/shaders/vertexShader.glsl";
  private static final String fragmentFile = "/shaders/fragmentShader.glsl";

  public StaticShader() {
    super(vertexFile, null, fragmentFile);
  }
}
