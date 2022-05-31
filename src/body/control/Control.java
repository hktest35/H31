package body.control;

public class Control {
    private float leftSpeed;
    private float rightSpeed;
    private float middleSpeed;
    private float forward;
    private float armAngleTarget;
    
    
    public Wheel wheel;
    public Arm arm;
    
    public Control(Wheel wheel,Arm arm){
        this.wheel = wheel;
        this.arm = arm;
    }
    
    public void run(){
        wheel.control(leftSpeed,rightSpeed);
        arm.control(armAngleTarget);
    }
    
    public float getArmAngleTarget() {
        return armAngleTarget;
    }

    public void setArmAngleTarget(float armAngleTarget) {
        this.armAngleTarget = armAngleTarget;
    }

    public boolean armInit(int tachoCount){
        if( arm.init(tachoCount)){
            return true;
        }
        return false;
    }
    
    public void setLeftSpeed(float leftSpeed) {
        this.leftSpeed = leftSpeed;
    }    
    
    public void setRightSpeed(float rightSpeed) {
        this.rightSpeed = rightSpeed;
    }
    
    public void setMiddleSpeed(float middleSpeed){
        this.middleSpeed = middleSpeed;
        arm.setMiddleSpeed(middleSpeed);
    }
    
    public void setForward(float forward){
        this.forward = forward;
    }
    public void reset(){
        wheel.leftMotor.resetTachoCount();
        wheel.rightMotor.resetTachoCount();
//        arm.middleMotor.resetTachoCount();
    } 
    
    public float getLeftSpeed() {
        return leftSpeed;
    }

    public float getRightSpeed() {
        return rightSpeed;
    }

    public float getForward() {
        return forward;
    }
    
    public int getMiddleTachoCount(){
        return arm.middleMotor.getTachoCount();
    }
    
}
