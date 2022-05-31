package detect;

import body.measure.Measure;


public class DistanceDetect extends Detect{

	private float checkDistance;

	public DistanceDetect(float checkDistance, Measure measure) {
	    super(measure);
        this.checkDistance = checkDistance;
    }
	
	public boolean detect() {
	    if(checkDistance>=0 && checkDistance < (measure.getLeftDistance() + measure.getRightDistance()) / 2 ){
	        return true;
	    }
	    if(checkDistance<=0 && checkDistance > (measure.getLeftDistance() + measure.getRightDistance()) / 2 ){
            return true;
        }
		return false;
	}

}
