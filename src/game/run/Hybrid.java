package game.run;

public class Hybrid extends Run{
    private Curve curve;
    private PID pid;  
    private float pidTurn;
    
    public Hybrid(Curve curve , PID pid,String edge) {
        super(pid.measure, pid.control, pid.forward);
        this.curve = curve;
        this.pid = pid;
        setEdge(edge);      
    }
    
    public void run() {
        pid.calcSpeed();
        curve.calcSpeed();
        
        turn = curve.getTurn();
        
        leftSpeed = (2 * forward - interval * turn ) / diameter;
        rightSpeed = (2 * forward + interval * turn ) / diameter;
        
        pidTurn = (float)Math.toRadians(pid.getTurn());
        
        leftSpeed = (float)Math.toDegrees(leftSpeed - pidTurn);
        rightSpeed = (float)Math.toDegrees(rightSpeed + pidTurn);
        
        control.setForward(forward);
        control.setLeftSpeed(leftSpeed);
        control.setRightSpeed(rightSpeed);
    };
    
    
    protected float getTurn() {

        return turn;
    }

}
