package net.macmv.engine.model;

import net.macmv.engine.model.loader.ModelLoader;
import net.macmv.engine.model.loader.OBJLoader;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

public class ModelBatch {
  private final HashMap<String, Model> models = new HashMap<>();
  private final ModelLoader loader = new ModelLoader();

  public void load(String name, String objName, String textureName) {
    models.put(name, new Model(OBJLoader.load(objName, loader), loader.loadTexture(textureName)));
  }

  public void load(String name, FloatBuffer vertices, FloatBuffer textureCords, FloatBuffer normals, IntBuffer indices, String textureName) {
    models.put(name, new Model(loader.loadModel(vertices, textureCords, normals, indices), loader.loadTexture(textureName)));
  }

  public Model get(String name) {
    return models.get(name);
  }

  public void dispose() {
    loader.dispose();
  }
}