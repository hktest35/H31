package body.control;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
/**
* 鬮ｴ�ｿｽ鬯伜∞�ｽｽ�ｽｼ�ｿｽ�ｽｽ�ｽｪ髯具ｽｻ�ｿｽ�ｽｽ�ｽｶ髯溷桁�ｽｽ�ｽ｡驛｢�ｽｧ�ｿｽ�ｽｽ�ｽｯ驛｢譎｢�ｽｽ�ｽｩ驛｢�ｽｧ�ｿｽ�ｽｽ�ｽｹ
* @author 髯滓�暮惧鬮ｯ�ｽｸ 鬮｢�ｽｨ�ｿｽ�ｽｽ�ｽ｡髫ｴ�ｿｽ�ｽｿ�ｽｽ
*
*/
public class Arm  {
    EV3LargeRegulatedMotor middleMotor;
    private int oldStatus;
    private boolean firstFlag;
    private int count = 0;
    private float initTarget;
    
    public Arm( EV3LargeRegulatedMotor middleMotor){
        this.middleMotor = middleMotor;
        this.initTarget = -180.0f;
        this.firstFlag = true;
        this.oldStatus = -1;
    }
    /**
    * 髯具ｽｻ�ｿｽ�ｽｽ�ｽｶ髯溷桁�ｽｽ�ｽ｡驍ｵ�ｽｺ陷ｷ�ｽｶ�ｿｽ�ｽｽ�ｿｽ
    */
    public void control(float angleTarget){
        middleMotor.rotateTo((int) angleTarget);
    }
    
    /**
     * 驛｢�ｽｧ�ｿｽ�ｽｽ�ｽ｢驛｢譎｢�ｽｽ�ｽｼ驛｢譎｢�ｽｿ�ｽｽ驛｢譎｢�ｽｽ�ｽ｢驛｢譎｢�ｽｽ�ｽｼ驛｢�ｽｧ�ｿｽ�ｽｽ�ｽｿ驛｢�ｽｧ髮区ｩｸ�ｽｿ�ｽｽ隴趣ｿｽ隰費ｿｽ髯具ｽｹ隰費ｽｶ隨假ｿｽ驛｢�ｽｧ�ｿｽ�ｽｿ�ｽｽ
     * @retval true     髯具ｽｻ隴趣ｿｽ隰費ｿｽ髯具ｽｹ闕ｵ貊ゑｽｽ�ｽｵ郢ｧ�ｿｽ�ｿｽ�ｽｽ�ｽｺ�ｿｽ�ｽｿ�ｽｽ
     * @retval false    髯具ｽｻ隴趣ｿｽ隰費ｿｽ髯具ｽｹ驍丞�ｷ�ｽｽ�ｽｸ�ｿｽ�ｽｽ�ｽｭ
     */
    public boolean init( int nowStatus ) {

        if( firstFlag ) {
            reset();
            firstFlag = false;
            control(initTarget);
        }

        // 髯晢ｿｽ�ｿｽ�ｽｽ�ｽｻ髯晢ｿｽ�ｿｽ�ｽｽ�ｽｾ驛｢�ｽｧ髮区ｫ∝樺驍ｵ�ｽｺ闕ｵ譏ｶ�ｿｽ
//      armAngle -= 0.2;


        
        if( oldStatus == nowStatus ) {
            count++;
        } else {
            count = 0;
        }

        oldStatus = nowStatus;

        if( count >= 75  ) {
            reset();
            count = 0;
            firstFlag = true;
            return true;
        } else {
            return false;
        }
        
        
    }

    public void reset() {
        middleMotor.stop(true);
        middleMotor.resetTachoCount();   
    }
    
    public void setMiddleSpeed(float middleSpeed){
        middleMotor.setSpeed(middleSpeed);
    }
}