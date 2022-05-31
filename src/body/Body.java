package body;

import body.control.Arm;
import body.control.Control;
import body.control.EV3KAMOGAWARegulatedMotor;
import body.control.Wheel;
import body.measure.Course;
import body.measure.Measure;
import body.measure.Position;
import body.measure.Touch;
import game.run.Curve;
import game.run.Hybrid;
import game.run.OnOff;
import game.run.PID;
import game.Game;
import game.Game.STATUS;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3TouchSensor;

public class Body {
    
    
    private static Body instance = new Body();
    

    EV3KAMOGAWARegulatedMotor leftMotor;
    EV3KAMOGAWARegulatedMotor rightMotor;
    EV3LargeRegulatedMotor middleMotor;
    EV3TouchSensor touchSensor;
    EV3ColorSensor colorSensor;
    EV3GyroSensor gyroSensor;
    public Control control;
    public Measure measure;
    
    private boolean doneArmInit;
    
    private Body() {
        this.middleMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        this.rightMotor = new EV3KAMOGAWARegulatedMotor(MotorPort.B);
        this.leftMotor = new EV3KAMOGAWARegulatedMotor(MotorPort.C);
        this.touchSensor = new EV3TouchSensor(SensorPort.S1);
        this.colorSensor = new EV3ColorSensor(SensorPort.S2);
//        this.gyroSensor = new EV3GyroSensor(SensorPort.S4);
        this.measure = new Measure(new Course(colorSensor),
                                    new Position(leftMotor,rightMotor,gyroSensor),
                                    new Touch(touchSensor)
                                    );
        this.control = new Control(new Wheel(leftMotor,rightMotor),new Arm(middleMotor));
        
    }
    
    public boolean run(){
        switch( Game.getStatus() ){
        case ARM_INITIALIZE:
            measure.update();
            ArmInit();
            break;
        case BASIC_CALIBRATION:
            update();
            break;
        case ADVANCE_CALIBRATION:
            update();
            break;
        case WAITSTART:
            control.reset();
            control.wheel.stopLRMotor();
            control.setLeftSpeed(0);
            control.setRightSpeed(0);
            measure.update();
            break;
        case BASIC:
            break;
        case ADVANCE_READY:
            control.reset();
            control.wheel.stopLRMotor();
            control.setLeftSpeed(0);
            control.setRightSpeed(0);
            measure.update();
            break;
        case ADVANCE_BINGO:
            break;
        case END:
            break;
        default:
            break;
        }
        
        if(Game.getStatus() == STATUS.END){
            return true;
        }else{
            return false;
        } 

    }
    
    public void update(){
        measure.update();
        control.run();
    }
    
    public void idling(){
        PID pid = new PID(measure, control, "L", 0, 0, 0, 0);
        Curve curve = new Curve(measure, control, 0, 0);
        Hybrid hybrid = new Hybrid(curve, pid, "L");
        OnOff onOff = new OnOff(measure, control,"L", 0,0.0f);
        for(int i=0; i<1500; i++){
            control.setArmAngleTarget(0.0f);
            control.run();
        }
        for(int i=0; i<1500; i++){
            measure.update();
            pid.run();
            curve.run();
            hybrid.run();
            onOff.run();
            control.setLeftSpeed(0.0f);
            control.setRightSpeed(0.0f);
        }
        control.setArmAngleTarget(40.0f);
    }
    
    public static Body getInstance(){
        return instance;
    }
    
    public void ArmInit(){
        if( !doneArmInit && control.armInit(middleMotor.getTachoCount()) ){
            doneArmInit = true;
        }
    }
    
    public boolean isArmInit(){
        return doneArmInit;
    }

}
