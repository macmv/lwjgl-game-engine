#version 400 core

in vec3 vertex;
in vec2 texCord;
in vec3 normal;

out vec2 pass_texCord;

uniform int time;
uniform mat4 transform;

void main() {
  vec4 worldPos = transform * vec4(vertex, 1);
  gl_Position = worldPos;

  //vec3 surfaceNormal = (transform * vec4(normal, 0)).xyz;

  //worldPos.xyz += (surfaceNormal * sin(time / 1000.0f + mod(worldPos.x * 1000, 100))) / 10f;
//  gl_Position = worldPos;

//  gl_Position = projection * view * worldPos;
//  gl_Position = worldPos;
//  pass_texCord = texCord;
}
