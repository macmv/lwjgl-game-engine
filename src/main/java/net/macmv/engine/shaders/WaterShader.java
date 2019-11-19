package net.macmv.engine.shaders;

public class WaterShader extends BasicShader {
  private static final String vertexFile = "/shaders/waterVertexShader.glsl";
  private static final String fragmentFile = "/shaders/waterFragmentShader.glsl";

  public WaterShader() {
    super(vertexFile, fragmentFile);
  }
}
