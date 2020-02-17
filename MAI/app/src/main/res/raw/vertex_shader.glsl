attribute vec4 a_Position;
uniform float uWindowK;

uniform float uCenterX;
uniform float uCenterY;

void main() {
    float posX = a_Position.x  * 0.001 + uCenterX;
    float posY = a_Position.y * uWindowK * -0.001 - uCenterY;
    float posZ = a_Position.z;
    float posW = a_Position.w;

    gl_Position = vec4(posX, posY, posZ, posW);
}