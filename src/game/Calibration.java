package game;

    import java.io.FileNotFoundException;
    import java.io.FileWriter;
    import java.io.IOException;
    import java.io.PrintWriter;

    import body.control.Control;
    import body.measure.Measure;
    import lejos.hardware.Button;
    import lejos.hardware.Sound;
    import lejos.hardware.lcd.LCD;
    
public class Calibration {

    public enum STATUS { SETSATURATION, SETRED, SETGREEN, SETBLUE, SETYELLOW};
    STATUS status;
    private static final double CALIB_DISTANCE = 200.0f;
    private float saturationLowerLimit;
    private boolean buttonFlg;
    private float[] maxHue;
    private float[] minHue;
    private float maxSaturation = 0.0f;
    private float minSaturation = 1.0f;
    private float correctSaturation;
    private Measure measure;
    private Control control;
    private float[] hsb;

    public Calibration(Measure measure, Control control,float saturation ){
        this.measure=measure;
        this.control=control;
        this.saturationLowerLimit = saturation;
        this.status = STATUS.SETSATURATION;
        this.buttonFlg=false;
        this.hsb = new float[3];
        this.maxHue = new float[4];
        this.minHue = new float[4];
        for( int i=0;i<maxHue.length;i++ ){
            maxHue[i] = 0f;
        }
        for( int i=0;i<minHue.length;i++ ){
            minHue[i] = 1f;
        }
        control.setRightSpeed(0.0f);
        control.setLeftSpeed(0.0f);
        control.reset();
        LCD.clearDisplay();
        LCD.drawString( status.toString(), 2, 2);


    }

    public boolean autoCalibration(){
        measure.update();
        control.run();
        if(!buttonFlg){
            buttonFlg=isOk();
        }

        if(buttonFlg){
            if(status == STATUS.SETYELLOW){
                control.setRightSpeed(50.0f);
                control.setLeftSpeed(50.0f);
            }else{
                control.setRightSpeed(200.0f);
                control.setLeftSpeed(200.0f);
            }
            switch( status ){
            
            case SETSATURATION :

                hsb = measure.course.getHSB();
                if( measure.getSaturation() >= maxSaturation ){
                    maxSaturation = measure.getSaturation();   
                }
                if( measure.getSaturation() < minSaturation ){
                    minSaturation = measure.getSaturation();
                }
                
                if(measure.getLeftDistance() > 1000 ){
                    measure.course.setMaxSaturation(maxSaturation);
                    measure.course.setMinSaturation(minSaturation);
                    status = STATUS.SETRED;
                    initialize();
                }
                break;
            case SETRED :
                hsb = measure.course.getHSB();
                correctSaturation = measure.course.getCorrectSaturation();
                if( correctSaturation > saturationLowerLimit ){
                    if( hsb[0] > maxHue[0] && hsb[0] < 0.3f ){
                        maxHue[0] = hsb[0];
                    }
                    if( hsb[0] < minHue[0] ){
                        minHue[0] = hsb[0];
                    }
                }
                if(measure.getLeftDistance() > CALIB_DISTANCE ){
                    status = STATUS.SETGREEN;
                    initialize();
                }
                break;
            case SETGREEN :
                hsb = measure.course.getHSB();
                correctSaturation = measure.course.getCorrectSaturation();
                if( correctSaturation > saturationLowerLimit ){
                    if( hsb[0] > maxHue[1] ){
                        maxHue[1] = hsb[0];
                    }
                    if( hsb[0] < minHue[1] ){
                        minHue[1] = hsb[0];
                    }
                }

                if(measure.getLeftDistance() > CALIB_DISTANCE ){
                    status = STATUS.SETBLUE;
                    initialize();
                }
                break;
            case SETBLUE :
                hsb = measure.course.getHSB();
                correctSaturation = measure.course.getCorrectSaturation();
                if( correctSaturation > saturationLowerLimit ){
                    if( hsb[0] > maxHue[2] ){
                        maxHue[2] = hsb[0];
                    }
                    if( hsb[0] < minHue[2] ){
                        minHue[2] = hsb[0];
                    }
                }

                if(measure.getLeftDistance() > CALIB_DISTANCE ){
                    status = STATUS.SETYELLOW;
                    initialize();
                }
                break;
            case SETYELLOW :
                hsb = measure.course.getHSB();
                correctSaturation = measure.course.getCorrectSaturation();
                if( correctSaturation > saturationLowerLimit ){
                    if( hsb[0] > maxHue[3] ){
                        maxHue[3] = hsb[0];
                    }
                    if( hsb[0] < minHue[3] ){
                        minHue[3] = hsb[0];
                    }
                }

                if(measure.getLeftDistance() > CALIB_DISTANCE ){
                    initialize();
                    outputHsb();
                    return true;
                }
                break;

            }
        }
        
        
        return false;
        
    }
    

    public STATUS getStatus(){
        return this.status;
    }
    private void outputHsb(){

        PrintWriter pw = null;
        try{
            pw = new PrintWriter( new FileWriter( "HSB.csv" ) );
            pw.println( saturationLowerLimit );
            pw.println( "SAT_min" + "," + measure.course.getMinSaturation() );
            pw.println( "SAT_max" + "," + measure.course.getMaxSaturation() );
            pw.println( "RED_min" + "," + minHue[0] );
            pw.println( "RED_max" + "," + maxHue[0] );
            pw.println( "GREEN_min" + "," + minHue[1] );
            pw.println( "GREEN_max" + "," + maxHue[1] );
            pw.println( "BLUE_min" + "," + minHue[2] );
            pw.println( "BLUE_max" + "," + maxHue[2] );
            pw.println( "YELLOW_min" + "," + minHue[3] );
            pw.println( "YELLOW_max" + "," + maxHue[3] );
            pw.flush();
        }catch( FileNotFoundException e ){
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }finally{
            if( pw != null ){
                pw.close();
            }
        }
    }

    public boolean isOk(){
        return measure.isUpped() || Button.ENTER.isDown();
    }

    public boolean isBtnFlg(){
        return this.buttonFlg;
    }
    public boolean isESCAPE(){
        return Button.ESCAPE.isDown();
    }
    
    public boolean isLeft(){
        return Button.LEFT.isDown();
    }
    
    public boolean isRight(){
        return Button.RIGHT.isDown();
    }
    
    public boolean isUp(){
        return Button.UP.isDown();
    }
    
    public boolean isDown(){
        return Button.DOWN.isDown();
    }
    
    public void initialize(){
        buttonFlg = false;
        control.setRightSpeed(0.0f);
        control.setLeftSpeed(0.0f);
        control.reset();
        LCD.clearDisplay();
        LCD.drawString( status.toString(), 2, 2);
        Sound.beep();
    }



}