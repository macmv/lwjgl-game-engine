#version 400 core

in vec3 vertex;
in vec2 texCord;
in vec3 normal;

out vec2 uv;
out vec3 surfaceNormal;
out vec3 lightVector;
out vec3 cameraVector;

uniform mat4 transform;
uniform mat4 projection;
uniform mat4 view;

uniform vec3 lightPos;

void main() {
  vec4 worldPos = transform * vec4(vertex, 1);
  gl_Position = projection * view * worldPos;
  uv = texCord;

  surfaceNormal = (transform * vec4(normal, 0)).xyz;
  lightVector = lightPos - worldPos.xyz;
  cameraVector = (inverse(view) * vec4(0, 0, 0, 1)).xyz - worldPos.xyz;
}