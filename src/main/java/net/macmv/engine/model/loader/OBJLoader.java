package net.macmv.engine.model.loader;

import de.javagl.obj.Obj;
import de.javagl.obj.ObjData;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;
import net.macmv.engine.model.RawModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class OBJLoader {

  public static RawModel load(String filename, ModelLoader loader) {
    Obj obj;
    try {
      InputStream inputStream = new FileInputStream("src/main/resources/" + filename);
      obj = ObjUtils.convertToRenderable(ObjReader.read(inputStream));
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }

    IntBuffer indices = ObjData.getFaceVertexIndices(obj);
    FloatBuffer vertices = ObjData.getVertices(obj);
    FloatBuffer texCoords = ObjData.getTexCoords(obj, 2);
    FloatBuffer normals = ObjData.getNormals(obj);
    return loader.loadModel(vertices, texCoords, normals, indices);
  }

}