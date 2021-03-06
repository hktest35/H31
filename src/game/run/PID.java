package game.run;

import body.control.Control;
import body.measure.Measure;

public class PID extends Run {
    
    private float DELTA_T = 0.005f;
    private float kago[] = new float[2];
    private float integral;
    private float kp;
    private float ki;
    private float kd; 
    
    
    public PID(Measure measure, Control control,String edge, float forward,float kp ,float ki,float kd){
        super(measure,control,forward);
        setEdge(edge);
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;

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
    protected void calcSpeed(){
        float p,i,d;
        // 目標輝度値を取得する
        float target = measure.getCorrectTarget();
        
        // 路面の輝度値を取得する
        float brightness = measure.getCorrectBrightness();
        
        
        //操作量を計算する
        kago[0] = kago[1];
        kago[1] = target - brightness;
        integral += (kago[1] + kago[0])/2.0f*DELTA_T;
        
        p = kp*kago[1];
        i = ki*integral;
        d = kd*(kago[1] - kago[0])/DELTA_T;
        
        turn = (p + i + d)* edge;
        
        leftSpeed = (forward - turn);
        rightSpeed = (forward + turn);
    }
    
    protected float getTurn(){            
        return turn;
    }
    
    public float getkp() {
        return kp;
    }
    
    public float getki() {
        return ki;
    }

    public float getkd() {
        return kd;
    }

}