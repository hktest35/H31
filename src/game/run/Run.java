package game.run;

import body.control.Control;

import body.measure.Measure;
import body.measure.Position;

public abstract class Run {
    
    protected Measure measure;
    protected Control control;
    
    protected float forward;
    protected float turn;
    
    protected float interval;
    protected float diameter;
    
    protected float leftSpeed;
    protected float rightSpeed;

    protected int edge;
    
    public Run(Measure measure, Control control, float forward){
        this.measure = measure;
        this.control = control;
        this.forward = forward;
        this.turn = 0.0f;
        this.interval = Position.getInterval();
        this.diameter = Position.getDiameter();
    }
    
    
    abstract public void run();
    
    public void setEdge(String edge){
        switch (edge) {
        case "L":
            this.edge = 1;
            break;
        case "R":
            this.edge = -1;
            break;

        default:
            this.edge = 1;
            break;
        }
    }
}
