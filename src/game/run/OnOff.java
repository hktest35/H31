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
    * Šp‘¬“x‚ğŒvZ‚·‚é
    */
    public void calcSpeed(){
     // –Ú•W‹P“x’l‚ğæ“¾‚·‚é
        float target = measure.getCorrectTarget();
        
        // ˜H–Ê‚Ì‹P“x’l‚ğæ“¾‚·‚é
        float brightness = measure.getCorrectBrightness();
        
        // ù‰ñŠp‘¬“x‚ğŒvZ‚·‚é
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
