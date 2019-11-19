#version 400 core

layout(triangles) in;
layout(triangle_strip, max_vertices = 3) out;

//in vec2 pass_texCord;

out vec4 pass_color;

uniform vec3 lightPos;
uniform vec3 lightColor;

uniform float roughness;
uniform float damping;

uniform sampler2D tex;

uniform mat4 projection;
uniform mat4 view;

uniform int time;

void createPoint(vec4 position, vec4 color) {
  pass_color = color;
  gl_Position = projection * view * position;
  EmitVertex();
}

vec4 calcColor(vec3 point, vec3 normal, vec2 texCord) {
  vec3 lightVector = lightPos - point;
  vec3 cameraVector = (inverse(view) * vec4(0, 0, 0, 1)).xyz - point;

  vec3 unitNormal = normalize(normal);
  vec3 unitLight = normalize(lightVector);

  float light = max(dot(unitNormal, unitLight), 0.2);
  vec3 diffuse = light * lightColor;

  vec3 unitCamera = normalize(cameraVector);
  vec3 reflectedLight = reflect(-unitLight, unitNormal);

  float specularFac = max(dot(reflectedLight, unitCamera), 0);
  specularFac = pow(specularFac, damping);
  vec3 specular = specularFac * lightColor;

  //  return vec4(diffuse, 1) * texture(tex, texCord) + vec4(specular, 1);
  return vec4(diffuse, 1) * vec4(.2, .6, 1, 1) + vec4(specular, 1);
}

vec3 calcNormal(vec4 a, vec4 b, vec4 c) {
  vec3 normal = cross(a.xyz - b.xyz, b.xyz - c.xyz);
  return normal.xyz;
}

vec4 createWater(vec4 point, vec3 normal) {
  point.xyz += (normal * sin(time / 1000.0f + mod(point.x * 1000, 100))) / 10f;
  return point;
}

void main() {
  vec3 normal = calcNormal(gl_in[0].gl_Position, gl_in[1].gl_Position, gl_in[2].gl_Position);

  vec4 point0 = createWater(gl_in[0].gl_Position, normal);
  vec4 point1 = createWater(gl_in[1].gl_Position, normal);
  vec4 point2 = createWater(gl_in[2].gl_Position, normal);

  vec3 realNormal = calcNormal(point0, point1, point2);
  vec4 color = calcColor(point0.xyz, realNormal, vec2(5.0/16 + 1.0/32, 0));
  //  vec4 color = vec4(1, 0, 0, 1);
  createPoint(point0, color);
  createPoint(point1, color);
  createPoint(point2, color);

  EndPrimitive();
}
