#version 330 
out vec4 fragColor; 
in vec2 tPos; 
uniform sampler2D text; 
uniform vec4 color; 
void main() { 
    fragColor = texture(text, tPos); 
}
