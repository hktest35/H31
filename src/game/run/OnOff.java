package game.run;

import body.control.Control;
import body.measure.Measure;

public class OnOff extends Run {
    
    float INITIAL_TURN = 0.0f;
    
    public OnOff(Measure measure, Control control, String edge,float forward,float turn){
        super(measure,control,forward);
        this.INITIAL_TURN = turn;
        setEdge(edge);
    }
    
    public void run(){
        calcSpeed();
        
        control.setForward(forward);
        control.setLeftSpeed(leftSpeed);
        control.setRightSpeed(rightSpeed);
    }
    
    /**
    * �p���x���v�Z����
    */
    public void calcSpeed(){
     // �ڕW�P�x�l���擾����
        float target = measure.getCorrectTarget();
        
        // �H�ʂ̋P�x�l���擾����
        float brightness = measure.getCorrectBrightness();
        
        // ����p���x���v�Z����
        if(brightness < target){
            turn = INITIAL_TURN;
        }else{
            turn = -INITIAL_TURN;
        }
        
        turn = (float)Math.toRadians(turn) *edge;
                
        leftSpeed = (2 * forward - interval * turn ) / diameter;
        rightSpeed = (2 * forward + interval * turn ) / diameter;
        
        leftSpeed = (float)Math.toDegrees(leftSpeed);
        rightSpeed = (float)Math.toDegrees(rightSpeed);
    }
    
    protected float getTurn(){
        return turn;
    }
}
