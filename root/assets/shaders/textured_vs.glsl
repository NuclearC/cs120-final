#version 330 
layout (location = 0) in vec2 vpos;
layout (location = 1) in vec2 tpos; 
out vec2 tPos; 
uniform mat4 pvm; 
void main() { 
    tPos = tpos; 
    gl_Position = pvm * vec4(vpos, 0.f, 1.0f); 
}
