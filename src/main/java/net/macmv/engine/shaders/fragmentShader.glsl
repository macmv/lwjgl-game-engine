#version 400 core

in vec2 uv;
in vec3 surfaceNormal;
in vec3 lightVector;
in vec3 cameraVector;

out vec4 out_color;

uniform sampler2D tex;

uniform vec3 lightColor;
uniform float roughness;
uniform float damping;

void main() {
  vec3 unitNormal = normalize(surfaceNormal);
  vec3 unitLight = normalize(lightVector);

  float light = max(dot(unitNormal, unitLight), 0.2);
  vec3 diffuse = light * lightColor;

  vec3 unitCamera = normalize(cameraVector);
  vec3 reflectedLight = reflect(-unitLight, unitNormal);

  float specularFac = max(dot(reflectedLight, unitCamera), 0);
  specularFac = pow(specularFac, damping);
  vec3 specular = specularFac * lightColor * (-roughness + 2);

  out_color = vec4(diffuse, 1) * texture(tex, uv) + vec4(specular, 1);
}