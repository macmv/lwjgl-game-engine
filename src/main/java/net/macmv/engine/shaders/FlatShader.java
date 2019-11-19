package net.macmv.engine.shaders;

public class FlatShader extends BasicShader {
  private static final String vertexFile = "/shaders/waterVertexShader.glsl";
  private static final String fragmentFile = "/shaders/waterFragmentShader.glsl";

  public FlatShader() {
    super(vertexFile, fragmentFile);
  }
}
