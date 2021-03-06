package game.run;

import body.control.Control;
import body.measure.Measure;

public class Curve extends Run  {

    public Curve (Measure measure, Control control, float forward,float turn){
        super(measure,control,forward);
        this.turn = (float)Math.toRadians(turn);
      leftSpeed = (2 * forward - interval * this.turn ) / diameter;
      rightSpeed = (2 * forward + interval * this.turn ) / diameter;
      
      leftSpeed = (float)Math.toDegrees(leftSpeed);
      rightSpeed = (float)Math.toDegrees(rightSpeed);

    }  
    
    public void run(){
//        calcSpeed();
        
        // 角速度を設定する
        control.setForward(forward);
        control.setLeftSpeed(leftSpeed);
        control.setRightSpeed(rightSpeed);
    }
    /**
     * 角速度を計算する
     */
    protected void calcSpeed(){
        
//        leftSpeed = (2 * forward - interval * turn ) / diameter;
//        rightSpeed = (2 * forward + interval * turn ) / diameter;
//        
//        leftSpeed = (float)Math.toDegrees(leftSpeed);
//        rightSpeed = (float)Math.toDegrees(rightSpeed);

    }
    
    protected float getTurn(){        
        return turn ;
    }
    
    protected float getForward() {
        return forward;
    }
}
