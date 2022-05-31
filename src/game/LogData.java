package game;

import game.Game.STATUS;
/**
* ログデータ（一件分）クラス
* @author 後藤 聡文
*
*/
public class LogData {
    private int count;
    private STATUS status;
    private int currentArea;
    private float brightness;
    private float correctBrightness;
    private float correctTarget;
    private float forward;
    private float leftSpeed;
    private float rightSpeed;
    private int acceleration;
    private float leftDistance;
    private float rightDistance;
    private float direction;
    private float motorDirection;
    private int leftTachoMotor;
    private int rightTachoMotor;
    private int leftRotationSpeed;
    private int rightRotationSpeed;
    private float voltage;
    private float hue;
    private float saturation;
    
//    private long time;

    
    public LogData(int count,
                    STATUS status,
                    int currentArea,
                    float brightness,
                    float correctBrightness,
                    float correctTarget,
                    float forward,
                    float leftSpeed,
                    float rightSpeed,
                    int acceleration,
                    float leftDistance,
                    float rightDistance,
                    float direction,
                    float motorDirection,
                    int leftTachoMotor,
                    int rightTachoMotor,
                    int leftRotationSpeed,
                    int rightRotationSpeed,
                    float voltage,
                    float hue,
                    float saturation
                    ){
        this.count = count;
        this.status = status;
        this.currentArea = currentArea;
        this.brightness = brightness;
        this.correctBrightness = correctBrightness;
        this.correctTarget = correctTarget;
        this.forward = forward;
        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
        this.acceleration = acceleration;
        this.leftDistance = leftDistance;
        this.rightDistance = rightDistance;
        this.direction = direction;
        this.motorDirection = motorDirection;
        this.leftTachoMotor = leftTachoMotor;
        this.rightTachoMotor = rightTachoMotor;
        this.leftRotationSpeed = leftRotationSpeed;
        this.rightRotationSpeed = rightRotationSpeed;
        this.voltage = voltage;
        this.hue = hue;
        this.saturation = saturation;
//        this.time = time;
    }
    
    
    public float getCorrectTarget() {
        return correctTarget;
    }


    public float getCorrectBrightness() {
        return correctBrightness;
    }
    
    public float getLeftDistance(){
        return leftDistance;
    }
    
    public float getRightDistance(){
        return rightDistance;
    }
    
    public float getDirection(){
        return direction;
    }

    public float getVoltage(){
        return voltage;
    }
    
    public int getCount() {
        return count;
    }
    
    public STATUS getStatus(){
        return status;
    }
    
    public int getCurrentArea() {
        return currentArea;
    }
    
    public float getBrightness() {
        return brightness;
    }
    
    public float getForward() {
        return forward;
    }
    
    public float getLeftSpeed(){
        return leftSpeed;
    }
    
    public float getRightSpeed(){
        return rightSpeed;
    }
    
    public float getMotorDirection(){
        return motorDirection;
    }


    public int getAcceleration() {
        return acceleration;
    }

    public int getLeftTachoMotor(){
        return leftTachoMotor;
    }
    
    public int getRightTachoMotor(){
        return rightTachoMotor;
    }
    
    public int getLeftRotationSpeed() {
        return leftRotationSpeed;
    }

    public int getRightRotationSpeed() {
        return rightRotationSpeed;
    }
    
    //uchida
    public float getHue(){
        return hue;
    }
    
    public float getSaturation(){
        return saturation;
    }
    
    
    
    
//    public long getTime(){
//        return time;
//    }
}