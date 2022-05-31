package detect;

import body.control.Control;
import body.measure.Measure;

public class BlockThrowDetect extends Detect {

    Control control;
    private boolean initFlag = true;
    
    public BlockThrowDetect(Measure measure,Control control) {
        super(measure);
        this.control = control;
    }
    
    @Override
    public boolean detect() {
        
        if(measure.isThrowChecker()){
            if(initFlag){
                control.setArmAngleTarget(40.0f);
                initFlag = false;
                return false;
            }else{
                initFlag = true;
                measure.setThrowChecker(false);
                return true;
            }
        }
        return false;
    }

}
