package net.macmv.engine.shaders;

import net.macmv.engine.model.Model;

import java.util.HashMap;

public class ShaderLoader {
  private final HashMap<Model.Type, BasicShader> shaders = new HashMap<>();

  public ShaderLoader() {
    shaders.put(Model.Type.STATIC, new StaticShader());
    shaders.put(Model.Type.FLAT, new FlatShader());
    shaders.put(Model.Type.WATER, new WaterShader());
  }

  public BasicShader get(Model.Type type) {
    return shaders.get(type);
  }

  public void dispose() {
    shaders.forEach((t, s) -> s.dispose());
  }
}
