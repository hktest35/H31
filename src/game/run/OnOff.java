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
    * 角速度を計算する
    */
    public void calcSpeed(){
     // 目標輝度値を取得する
        float target = measure.getCorrectTarget();
        
        // 路面の輝度値を取得する
        float brightness = measure.getCorrectBrightness();
        
        // 旋回角速度を計算する
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
