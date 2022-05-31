package body.control;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
/**
* ŽÔ—Ö§ŒäƒNƒ‰ƒX
* @author Œã“¡ ‘•¶
*
*/
public class Wheel  {
    EV3KAMOGAWARegulatedMotor leftMotor;
    EV3KAMOGAWARegulatedMotor rightMotor;
    EV3KAMOGAWARegulatedMotor middleMotor;
    
    public Wheel(EV3KAMOGAWARegulatedMotor leftMotor, EV3KAMOGAWARegulatedMotor rightMotor){
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
    }
    /**
    * §Œä‚·‚é
    */
    public void control(float leftSpeed, float rightSpeed){
    //c
        leftMotor.setSpeed(leftSpeed);
        rightMotor.setSpeed(rightSpeed);
        if(leftSpeed >= 0){
            leftMotor.forward();
        }else{
            leftMotor.backward();
        }
        if(rightSpeed >= 0){
            rightMotor.forward();
        }else{
            rightMotor.backward();
        }
        
    }
    
    public void stopLRMotor(){
        leftMotor.stop(true);
        rightMotor.stop(true);
    }
    
    public void SetAcceleration(int acceleration){
        leftMotor.setAcceleration(acceleration);
        rightMotor.setAcceleration(acceleration);
    }
    
    public float getSpeed(){
        return leftMotor.getSpeed();
    }
    
    public int getAcceleration(){
        return leftMotor.getAcceleration();
    }
    
    public int getLeftTachoMotor(){
        return leftMotor.getTachoCount();
    }
    
    public int getRightTachoMotor(){
        return rightMotor.getTachoCount();
    }
}