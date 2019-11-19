package net.macmv.engine.shaders;

public class FlatShader extends BasicShader {
  private static final String vertexFile = "/shaders/flatVertexShader.glsl";
  private static final String fragmentFile = "/shaders/flatFragmentShader.glsl";

  public FlatShader() {
    super(vertexFile, fragmentFile);
  }
}
