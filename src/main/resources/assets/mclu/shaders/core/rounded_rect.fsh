#version 150

in vec2 texCoord;

uniform sampler2D Sampler0;
uniform vec4 Tint;
uniform float CornerRadius;
uniform vec2 RectSize;

out vec4 fragColor;

float roundedBoxSDF(vec2 centerPosition, vec2 size, float radius) {
    vec2 q = abs(centerPosition) - size + radius;
    return min(max(q.x, q.y), 0.0) + length(max(q, 0.0)) - radius;
}

void main(){
    vec4 texColor = texture(Sampler0, texCoord);
    vec4 tintedColor = texColor * Tint;

    if (CornerRadius > 0.0) {
        vec2 position = (texCoord - 0.5) * 2.0;
        vec2 halfSize = RectSize * 0.5;

        float distance = roundedBoxSDF(position * halfSize, halfSize, CornerRadius);
        float smoothedAlpha = 1.0 - smoothstep(-1.0, 1.0, distance);

        tintedColor.a *= smoothedAlpha;
    }

    fragColor = tintedColor;

    if (fragColor.a < 0.001) {
        discard;
    }
}