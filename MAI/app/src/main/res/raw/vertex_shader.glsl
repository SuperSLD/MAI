attribute vec4 a_Position;
uniform float uWindowK;

void main() {
    float posX = a_Position.x;
    float posY = a_Position.y * uWindowK;
    float posZ = a_Position.z;
    float posW = a_Position.w;

    gl_Position = vec4(posX, posY, posZ, posW);
}