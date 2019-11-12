package net.macmv.lwjgltest.shaders;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

public abstract class ShaderProgram {

  private static FloatBuffer matBuf = BufferUtils.createFloatBuffer(16);
  private int program;
  private int vertexShader;
  private int fragmentShader;

  public ShaderProgram(String vertexFile, String fragmentFile) {
    vertexShader = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
    fragmentShader = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
    program = GL20.glCreateProgram();
    GL20.glAttachShader(program, vertexShader);
    GL20.glAttachShader(program, fragmentShader);
    bindAttributes();
    GL20.glLinkProgram(program);
    GL20.glValidateProgram(program);
    getAllUniformLocations();
  }

  private static int loadShader(String file, int type) {
    StringBuilder source = new StringBuilder();
    try {
      BufferedReader r = new BufferedReader(new FileReader(file));
      String line;
      while ((line = r.readLine()) != null) {
        source.append(line).append("\n");
      }
    } catch (IOException e) {
      System.err.println("Could not read file!");
      e.printStackTrace();
    }
    int shader = GL20.glCreateShader(type);
    GL20.glShaderSource(shader, source);
    GL20.glCompileShader(shader);
    if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
      System.out.println(GL20.glGetShaderInfoLog(shader, 500));
      System.err.println("Could not compile shader");
      System.exit(-1);
    }
    return shader;
  }

  protected abstract void getAllUniformLocations();

  protected int getUniformLocation(String uniformName) {
    return GL20.glGetUniformLocation(program, uniformName);
  }

  public void start() {
    GL20.glUseProgram(program);
  }

  public void stop() {
    GL20.glUseProgram(0);
  }

  public void dispose() {
    stop();
    GL20.glDetachShader(program, vertexShader);
    GL20.glDetachShader(program, fragmentShader);
    GL20.glDeleteShader(vertexShader);
    GL20.glDeleteShader(fragmentShader);
    GL20.glDeleteProgram(program);
  }

  protected abstract void bindAttributes();

  protected void bindAttribute(int attrib, String varName) {
    GL20.glBindAttribLocation(program, attrib, varName);
  }

  protected void loadFloat(int loc, float value) {
    GL20.glUniform1f(loc, value);
  }

  protected void loadVector(int loc, Vector3f value) {
    GL20.glUniform3f(loc, value.x, value.y, value.z);
  }

  protected void loadBool(int loc, boolean value) {
    GL20.glUniform1f(loc, value ? 1 : 0);
  }

  protected void loadMatrix(int loc, Matrix4f value) {
    value.get(matBuf);
    GL20.glUniformMatrix4fv(loc, false, matBuf);
  }
}
