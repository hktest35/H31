package body.control;

import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.hardware.port.TachoMotorPort;
import lejos.hardware.sensor.EV3SensorConstants;

public class EV3KAMOGAWARegulatedMotor extends BaseRegulatedMotor {

    static final float MOVE_P = 8f;
    static final float MOVE_I = 0.02f;
    static final float MOVE_D = 20f;
    static final float HOLD_P = 2f;
    static final float HOLD_I = 0.02f;
    static final float HOLD_D = 8f;
    static final int OFFSET = 0;
    
    private static final int MAX_SPEED = 175*360/60;
    
    public EV3KAMOGAWARegulatedMotor(TachoMotorPort port){
        super(port, null, EV3SensorConstants.TYPE_NEWTACHO, MOVE_P, MOVE_I, MOVE_D,
                HOLD_P, HOLD_I, HOLD_D, OFFSET, MAX_SPEED);
    }

    public EV3KAMOGAWARegulatedMotor(Port port){
        super(port, null, EV3SensorConstants.TYPE_NEWTACHO, MOVE_P, MOVE_I, MOVE_D,
                HOLD_P, HOLD_I, HOLD_D, OFFSET, MAX_SPEED);
    }

}
