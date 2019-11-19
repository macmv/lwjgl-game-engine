package net.macmv.engine.shaders;

public class WaterShader extends BasicShader {
  private static final String vertexFile = "/shaders/flatVertexShader.glsl";
  private static final String fragmentFile = "/shaders/flatFragmentShader.glsl";

  public WaterShader() {
    super(vertexFile, fragmentFile);
  }
}
