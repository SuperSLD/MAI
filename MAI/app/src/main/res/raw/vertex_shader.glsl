attribute vec4 a_Position;
uniform float uWindowK;

uniform float uCenterX;
uniform float uCenterY;
uniform float uZoom;

void main() {
    //коэфициэнт первоначального увеличения
    //уможенный на uZoom
    float k = 0.005 * uZoom;

    //преобразование кординат
    float posX = a_Position.x * k + uCenterX;
    float posY = a_Position.y * uWindowK * -k - uCenterY;
    float posZ = a_Position.z;
    float posW = a_Position.w;

    gl_Position = vec4(posX, posY, posZ, posW);
}