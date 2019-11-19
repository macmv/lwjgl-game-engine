#version 400 core

in vec3 vertex;
in vec2 texCord;
in vec3 normal;

flat out vec4 pass_color;

uniform sampler2D tex;

uniform vec3 lightColor;
uniform float roughness;
uniform float damping;

uniform mat4 transform;
uniform mat4 projection;
uniform mat4 view;

uniform vec3 lightPos;

void main() {
  vec4 worldPos = transform * vec4(vertex, 1);
  gl_Position = projection * view * worldPos;

  vec3 surfaceNormal = (transform * vec4(normal, 0)).xyz;
  vec3 lightVector = lightPos - worldPos.xyz;
  vec3 cameraVector = (inverse(view) * vec4(0, 0, 0, 1)).xyz - worldPos.xyz;

  vec3 unitNormal = normalize(surfaceNormal);
  vec3 unitLight = normalize(lightVector);

  float light = max(dot(unitNormal, unitLight), 0.2);
  vec3 diffuse = light * lightColor;

  vec3 unitCamera = normalize(cameraVector);
  vec3 reflectedLight = reflect(-unitLight, unitNormal);

  float specularFac = max(dot(reflectedLight, unitCamera), 0);
  specularFac = pow(specularFac, damping);
  vec3 specular = specularFac * lightColor * (-roughness + 1);

  pass_color = vec4(diffuse, 1) * texture(tex, texCord) + vec4(specular, 1);
}