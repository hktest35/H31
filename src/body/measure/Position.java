package body.measure;

import body.control.EV3KAMOGAWARegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;

public class Position {
    private static float diameter = 100.0f;
    private static float interval = 143.0f;
    
    private EV3KAMOGAWARegulatedMotor leftMotor;
    private EV3KAMOGAWARegulatedMotor rightMotor;
    private EV3GyroSensor gyroSensor;
    private SampleProvider sampleProvider;
    private float value[];
    
    private float leftDistance;
    private float rightDistance;
    private float motorDirection;
    private float sensorDirection =0.0f;
    
    private int leftRotationSpeed;
    private int rightRotationSpeed;

    

    public Position(EV3KAMOGAWARegulatedMotor leftMotor, EV3KAMOGAWARegulatedMotor rightMotor,EV3GyroSensor gyroSensor){
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
//        this.gyroSensor = gyroSensor;
//        sampleProvider = gyroSensor.getAngleMode();
//        value = new float[sampleProvider.sampleSize()];
    }
    
    public void update() {
        leftDistance = calculateDistance(leftMotor.getTachoCount());
        rightDistance =  calculateDistance(rightMotor.getTachoCount());
        motorDirection = calculateDirection();       
        leftRotationSpeed = leftMotor.getRotationSpeed();
        rightRotationSpeed = rightMotor.getRotationSpeed();
//        sampleProvider.fetchSample(value, 0);
//        sensorDirection = value[0]*-1;
    }
    private float calculateDistance(int TachoCount){
        return (float)(diameter * Math.PI * TachoCount / 360.0f) ;
    }
    
    public float getLeftDistance() {
        return leftDistance;
    }
    
    public static float getDiameter() {
        return diameter;
    }

    public static float getInterval() {
        return interval;
    }

    public EV3KAMOGAWARegulatedMotor getLeftMotor() {
        return leftMotor;
    }

    public float getRightDistance() {        
        return rightDistance;
    }
    
    private float calculateDirection() {
        return (float)(360.0f / (2.0f * Math.PI * interval) * (getRightDistance() - getLeftDistance()));
    }
    
    public float getDirection() {
        return sensorDirection;
    }
    
    public float getMotorDirection(){
        return motorDirection;
    }
    
    public void gyroReset(){
        gyroSensor.reset();
    }
    
    public int getLeftRotationSpeed() {
        return leftRotationSpeed;
    }

    public int getRightRotationSpeed() {
        return rightRotationSpeed;
    }
    
}
