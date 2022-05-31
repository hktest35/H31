package game.run;

import body.control.Control;
import body.measure.Measure;
//import lejos.utility.Delay;

public class BlockThrow extends Run {
    
    private boolean firstFlag = true;
    private boolean secondFlag = false;
    private boolean initFlag = false;
    
    public BlockThrow(Measure measure,Control control,float forword){
        super(measure, control,forword);
    }
    
    @Override
    public void run() {
        // TODO 自動生成されたメソッド・スタブ
        control.setMiddleSpeed(super.forward);
        control.setForward(0);
        control.setLeftSpeed(0);
        control.setRightSpeed(0);
        if(secondFlag){
//            control.setArmAngleTarget(0);
            initFlag = true;
//            initFlag = control.arm.init(control.getMiddleTachoCount());
//            System.out.println("init Flag " + initFlag);
//            System.out.println("angle "+ control.getArmAngleTarget());
        }else if(firstFlag){ 
//            control.setArmAngleTarget(-180.0f);
//            control.setArmAngleTarget(20.0f);
        }
        secondFlag = true;
        if(initFlag){
            control.setArmAngleTarget(75.0f);
            firstFlag = false;
            secondFlag = false;
            initFlag = false;
//            control.arm.reset();        
            measure.setThrowChecker(true);
        }
        
    }

}
