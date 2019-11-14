package net.macmv.engine.model.loader;

import net.macmv.engine.model.RawModel;
import org.lwjgl.opengl.*;
import org.lwjgl.stb.STBImage;
import org.lwjgl.stb.STBImageResize;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class ModelLoader {

  private final List<Integer> vaos = new ArrayList<>();
  private final List<Integer> vbos = new ArrayList<>();
  private final List<Integer> textures = new ArrayList<>();

  public RawModel loadModel(FloatBuffer positions, FloatBuffer texCords, FloatBuffer normals, IntBuffer indices) {
    int vao = createVao();
    bindIndicesBuffer(indices);
    storeVboInVao(0, 3, positions);
    storeVboInVao(1, 2, texCords);
    storeVboInVao(2, 3, normals);
    unbindVao();
    return new RawModel(vao, indices.capacity());
  }

  public int loadTexture(String fName) {
    int w, h, comp;
    ByteBuffer image;
    try (MemoryStack stack = MemoryStack.stackPush()) {
      IntBuffer wBuf = stack.mallocInt(1);
      IntBuffer hBuf = stack.mallocInt(1);
      IntBuffer compBuf = stack.mallocInt(1);
      image = STBImage.stbi_load("src/main/resources/" + fName, wBuf, hBuf, compBuf, 0);
      if (image == null) {
        throw new RuntimeException("Failed to load image: " + STBImage.stbi_failure_reason());
      }
      w = wBuf.get(0);
      h = hBuf.get(0);
      comp = compBuf.get(0);
    }

    int tex = GL11.glGenTextures();
    textures.add(tex);

    GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

    GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, w, h, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, image);

    ByteBuffer input_pixels = image;
    int input_w = w;
    int input_h = h;
    int mipmapLevel = 0;
    while (1 < input_w || 1 < input_h) {
      int output_w = Math.max(1, input_w >> 1);
      int output_h = Math.max(1, input_h >> 1);

      ByteBuffer output_pixels = MemoryUtil.memAlloc(output_w * output_h * comp);
      STBImageResize.stbir_resize_uint8_generic(
              input_pixels, input_w, input_h, input_w * comp,
              output_pixels, output_w, output_h, output_w * comp,
              comp, comp == 4 ? 3 : STBImageResize.STBIR_ALPHA_CHANNEL_NONE, STBImageResize.STBIR_FLAG_ALPHA_PREMULTIPLIED,
              STBImageResize.STBIR_EDGE_CLAMP,
              STBImageResize.STBIR_FILTER_MITCHELL,
              STBImageResize.STBIR_COLORSPACE_SRGB
      );

      if (mipmapLevel == 0) {
        STBImage.stbi_image_free(image);
      } else {
        MemoryUtil.memFree(input_pixels);
      }

      GL11.glTexImage2D(GL11.GL_TEXTURE_2D, ++mipmapLevel, GL11.GL_RGBA, output_w, output_h, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, output_pixels);

      input_pixels = output_pixels;
      input_w = output_w;
      input_h = output_h;
    }
    if (mipmapLevel == 0) {
      STBImage.stbi_image_free(image);
    } else {
      MemoryUtil.memFree(input_pixels);
    }
    return tex;
  }

  public void dispose() {
    vaos.forEach(GL30::glDeleteVertexArrays);
    vbos.forEach(GL30::glDeleteBuffers);
    textures.forEach(GL30::glDeleteTextures);
  }

  public int createVao() {
    int vao = GL30.glGenVertexArrays();
    vaos.add(vao);
    GL30.glBindVertexArray(vao);
    return vao;
  }

  public void storeVboInVao(int attrib, int size, FloatBuffer data) {
    int vbo = GL15.glGenBuffers();
    vbos.add(vbo);
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
    GL20.glVertexAttribPointer(attrib, size, GL11.GL_FLOAT, false, 0, 0);
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
  }

  public void bindIndicesBuffer(IntBuffer data) {
    int vbo = GL15.glGenBuffers();
    vbos.add(vbo);
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vbo);
    GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
  }

  private void unbindVao() {
    GL30.glBindVertexArray(0);
  }
}
