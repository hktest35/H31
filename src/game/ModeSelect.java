package game;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

public class ModeSelect {
    
    static boolean mode = false; //false‚ÌŽž‚ÍMain,true‚ÌŽž‚ÍHSBMain
    
    public ModeSelect() {
        LCD.drawString("Please Wait...  ", 0, 4);
        LCD.clear();
        Sound.beep();
        disp();
        while( !Button.ENTER.isDown() ){

            if(Button.ESCAPE.isDown()){
                return;
            }

            if( Button.LEFT.isDown() ){
                mode = true;
                disp();
            }else if( Button.RIGHT.isDown() ){
                mode = false;
                disp();
            }

        }
    }
    
    static void disp(){
        LCD.drawString(" --CourseSelect--", 0, 0 );
        LCD.drawString("LeftBtn : RightBtn", 0, 2 );
        LCD.drawString("HSBMain: RunMain", 0, 3 );
        if( mode ){
            LCD.drawString("this is  HSBMain", 0, 5);
        }else{
            LCD.drawString("this is  RunMain", 0, 5);
        }
        
    }
    
    public boolean modeSelect(){
        return mode;
    }
    
}
