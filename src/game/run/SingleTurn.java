package game.run;

import body.control.Control;
import body.measure.Measure;

public class SingleTurn extends Run {

    enum PIVOT{ RIGHT, LEFT };
    private PIVOT pivot;

    public SingleTurn( Measure measure, Control control, float forward, String pivot ){
        super( measure, control,forward);
        this.forward = forward;
        if( pivot.equals("L") ){
            this.pivot = PIVOT.LEFT;
        }else if( pivot.equals("R") ){
            this.pivot = PIVOT.RIGHT;
        }
    }       
    
    /**
     * ŒvŽZ
     */
    protected void calcSpeed(){
        leftSpeed = (2 * forward - interval * turn ) / diameter;
        rightSpeed = (2 * forward + interval * turn ) / diameter;
        
        leftSpeed = (float)Math.toDegrees(leftSpeed);
        rightSpeed = (float)Math.toDegrees(rightSpeed);
    }
    
    

//  @Override
//  public void calcTurn(){
//  }
    

    
    protected float getTurn(){            
        return turn;
    }
    protected float getForward() {
        return forward;
    }
    @Override   
    public void run(){
        calcSpeed();
        if( pivot == PIVOT.LEFT){
            control.setLeftSpeed(0.0f);
            control.setRightSpeed(rightSpeed);
        }else if( pivot == PIVOT.RIGHT ){
            control.setLeftSpeed(leftSpeed);
            control.setRightSpeed(0.0f);
        }
    }
}
