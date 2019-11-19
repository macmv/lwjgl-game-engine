package net.macmv.engine.model;

import net.macmv.engine.model.loader.ModelLoader;
import net.macmv.engine.model.loader.OBJLoader;
import net.macmv.engine.shaders.ShaderLoader;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

public class ModelBatch {
  private final HashMap<String, Model> models = new HashMap<>();
  private final ModelLoader modelLoader = new ModelLoader();
  private final ShaderLoader shaderLoader;

  public ModelBatch() {
    shaderLoader = new ShaderLoader();
  }

  public void load(String name, String objName, Model.Type type, String textureName) {
    models.put(name, new Model(OBJLoader.load(objName, modelLoader), shaderLoader, type, modelLoader.loadTexture(textureName)));
  }

  public void load(String name,
                   FloatBuffer vertices,
                   FloatBuffer textureCords,
                   FloatBuffer normals,
                   IntBuffer indices,
                   Model.Type type,
                   String textureName) {
    models.put(name, new Model(
            modelLoader.loadModel(vertices, textureCords, normals, indices),
            shaderLoader,
            type,
            modelLoader.loadTexture(textureName)));
  }

  public Model get(String name) {
    return models.get(name);
  }

  public void dispose() {
    modelLoader.dispose();
    shaderLoader.dispose();
  }
}
