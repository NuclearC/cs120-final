#version 330 
out vec4 fragColor; 
in vec2 tPos; 

uniform sampler2D text; 

uniform vec4 color; 

void main() { 
    vec4 textureColor = texture(text, tPos);
    fragColor = textureColor * color;
}
