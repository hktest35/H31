package detect;

import body.measure.Measure;

public abstract class Detect {
    protected Measure measure;
    
    public Detect(Measure measure){
        this.measure = measure;
    }
    
    abstract public boolean detect();
}
