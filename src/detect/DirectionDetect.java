package detect;

import body.measure.Measure;


public class DirectionDetect extends Detect {

	private float checkDirection;
	
	public DirectionDetect(float checkDirection,Measure measure) {
	    super(measure);
        this.checkDirection = checkDirection;
    }

	public boolean detect() {
	       if(checkDirection >= 0 && checkDirection <= measure.getMotorDirection()){
	            return true;
	       }
           if(checkDirection <= 0 && checkDirection >= measure.getMotorDirection()){
               return true;
           }
		return false;
	}

}
