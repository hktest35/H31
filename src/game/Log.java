package game;

import java.io.BufferedWriter; 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import body.Body;
import lejos.hardware.lcd.LCD;
import lejos.hardware.Battery;;

/**
 * ログクラス
 * インスタンスを単一にするため、Singletonパターンを採用
 * @author 後藤 聡文
 *
 */
public class Log {
    // タスクの呼び出し回数
    private int count;
        
    private static Log instance = new Log();
        
    private Game game;
    private Body body;
    
    
    private static List<LogData> logList = new ArrayList<LogData>();
        
    private Log(){
            
    }
    public static Log getInstance(){
        return instance;
    }
    public void setGame(Game game){
        this.game = game;
    }
    
    public void setBody(Body body){
        this.body = body;
    }
    
    public void disp(){
        
        switch (Game.getStatus()) { 
        case COURSE_SELECT:
            break;
        case BASIC_CALIBRATION: 
            LCD.clear();
            dispBCalibration();
            break;
        case ADVANCE_CALIBRATION: 
            LCD.clear();
            dispACalibration();
            break;
        case WAITSTART: 
            LCD.clear();
            dispWaitStart(); 
            break; 
        case BASIC:
            LCD.clear();
        case ADVANCE_READY:            
        case ADVANCE_BINGO:
            break;
        default: 
            break; 
        }
    }
    
    private void dispBCalibration(){ 
        LCD.drawString("BASIC_CALIBRATION", 0, 0); 
        LCD.drawString("White", 0, 2); 
        LCD.drawString(Float.toString(body.measure.course.getWhite()), 11, 2); 
        LCD.drawString("Black", 0, 3); 
        LCD.drawString(Float.toString(body.measure.course.getBlack()), 11, 3); 
        LCD.drawString("Target", 0, 4); 
        LCD.drawString(Float.toString(body.measure.course.getTarget()), 11, 4); 
        LCD.drawString("Brightness", 0, 5); 
        LCD.drawString(Float.toString(body.measure.getBrightness()), 11, 5); 
        LCD.drawString("Voltage", 0, 6); 
        LCD.drawString(Float.toString(Battery.getVoltage()), 11, 6); 
    }
    
    private void dispACalibration(){ 
        LCD.drawString("ADVANCE_CALIBRATION", 0, 0); 
        LCD.drawString("White", 0, 2); 
        LCD.drawString(Float.toString(body.measure.course.getWhite()), 11, 2); 
        LCD.drawString("Black", 0, 3); 
        LCD.drawString(Float.toString(body.measure.course.getBlack()), 11, 3); 
        LCD.drawString("Target", 0, 4); 
        LCD.drawString(Float.toString(body.measure.course.getTarget()), 11, 4); 
        LCD.drawString("Brightness", 0, 5); 
        LCD.drawString(Float.toString(body.measure.getBrightness()), 11, 5); 
        LCD.drawString("Voltage", 0, 6); 
        LCD.drawString(Float.toString(Battery.getVoltage()), 11, 6); 
    }
    
    private void dispWaitStart(){ 
        LCD.drawString("WAIT START", 0, 0); 
        LCD.drawString("White", 0, 2); 
        LCD.drawString(Float.toString(body.measure.course.getWhite()), 11, 2); 
        LCD.drawString("Black", 0, 3); 
        LCD.drawString(Float.toString(body.measure.course.getBlack()), 11, 3); 
        LCD.drawString("Target", 0, 4); 
        LCD.drawString(Float.toString(body.measure.course.getTarget()), 11, 4); 
        LCD.drawString("Brightness", 0, 5); 
        LCD.drawString(Float.toString(body.measure.getBrightness()), 11, 5);
        LCD.drawString("Voltage", 0, 6); 
        LCD.drawString(Float.toString(Battery.getVoltage()), 11, 6); 
    }
    
    private void dispRun(){
        LCD.drawString("RUN", 0, 0); 
        LCD.drawString("White", 0, 2); 
        LCD.drawString(Float.toString(body.measure.course.getWhite()), 11, 2); 
        LCD.drawString("Black", 0, 3); 
        LCD.drawString(Float.toString(body.measure.course.getBlack()), 11, 3); 
        LCD.drawString("Target", 0, 4); 
        LCD.drawString(Float.toString(body.measure.course.getTarget()), 11, 4); 
        LCD.drawString("Brightness", 0, 5); 
        LCD.drawString(Float.toString(body.measure.course.getBrightness()), 11, 5); 
        LCD.drawString("LeftSpeed", 0, 6); 
        LCD.drawString(Float.toString(body.control.getLeftSpeed()), 11, 6); 
        LCD.drawString("RightSpeed", 0, 7); 
        LCD.drawString(Float.toString(body.control.getRightSpeed()), 11, 7);
        //LCD.drawString("Direction", 0, 7); 
        //LCD.drawString(Float.toString(area.direction.dir()), 11, 7);
    }
        
    public void countUp(){
        count++;
    }
    
    public void add(){ 
        logList.add(new LogData(
                count,
                Game.getStatus(),
                game.area.getCurrentArea(),
                body.measure.getBrightness(),
                body.measure.getCorrectBrightness(),
                body.measure.getCorrectTarget(),
                body.control.getForward(),
                body.control.getLeftSpeed(),
                body.control.getRightSpeed(),
                body.control.wheel.getAcceleration(),
                body.measure.getLeftDistance(),
                body.measure.getRightDistance(),
                body.measure.getDirection(),
                body.measure.getMotorDirection(),
                body.control.wheel.getLeftTachoMotor(),
                body.control.wheel.getRightTachoMotor(),
                body.measure.getLeftRotationSpeed(),
                body.measure.getRightRotationSpeed(),
                Battery.getVoltage(),
//                game.getTime()
                body.measure.course.getHue(),
                body.measure.course.getSaturation()
                
                )
        );
    }
    
    /** * ファイルに保存する */ 
    public void write(){ 
        try { 
            StringBuilder sb = new StringBuilder(); 
            // ヘッダー部 
            sb.append("white,black,target,correctTarget\r\n"); 
            sb.append(Float.toString(body.measure.course.getWhite())); 
            sb.append(",");
            sb.append(Float.toString(body.measure.course.getBlack())); 
            sb.append(","); 
            sb.append(Float.toString(body.measure.course.getTarget())); 
            sb.append(","); 
            sb.append(Float.toString(body.measure.getCorrectTarget())); 
            sb.append("\r\n\r\n");
            
            sb.append("adwhite,adblack,adtarget,adcorrectTarget\r\n"); 
            sb.append(Float.toString(game.getAdWhite())); 
            sb.append(",");
            sb.append(Float.toString(game.getAdBlack())); 
            sb.append(","); 
            sb.append(Float.toString((game.getAdWhite()+game.getAdBlack())/2.0f)); 
            sb.append(",");            
            sb.append(Float.toString(body.measure.getCorrectTarget())); 
            sb.append("\r\n\r\n");
         // レコード部 
            sb.append("count,"
                    + "status,"
                    + "Area,"
                    + "brightness,"
                    + "forward,"
                    + "leftSpeed,"
                    + "rightSpeed,"
                    + "acceleration,"
                    + "correctBrightness,"
                    + "correctTarget,"
                    + "LeftDistance,"
                    + "RightDistance,"
                    + "GyroDirection,"
                    + "direction,"
                    + "leftTachoMotor,"
                    + "rightTachoMotor,"
                    + "leftRotationSpeed,"
                    + "rightRotationSpeed,"
                    + "Voltage,"
                    + "hue,"
                    + "saturation\r\n")
            ; 
            for(LogData data : logList){
                sb.append(Integer.toString(data.getCount())); 
                sb.append(","); 
                sb.append(data.getStatus().toString()); 
                sb.append(","); 
                sb.append(Integer.toString(data.getCurrentArea())); 
                sb.append(","); 
                sb.append(Float.toString(data.getBrightness())); 
                sb.append(","); 
                sb.append(Float.toString(data.getForward())); 
                sb.append(","); 
                sb.append(Float.toString(data.getLeftSpeed())); 
                sb.append(","); 
                sb.append(Float.toString(data.getRightSpeed())); 
                sb.append(",");
                sb.append(Integer.toString(data.getAcceleration())); 
                sb.append(",");
                sb.append(Float.toString(data.getCorrectBrightness()));
                sb.append(",");
                sb.append(Float.toString(data.getCorrectTarget()));
                sb.append(",");
                sb.append(Float.toString(data.getLeftDistance()));
                sb.append(",");
                sb.append(Float.toString(data.getRightDistance()));
                sb.append(",");
                sb.append(Float.toString(data.getDirection()));
                sb.append(",");
                sb.append(Float.toString(data.getMotorDirection()));
                sb.append(",");
                sb.append(Float.toString(data.getLeftTachoMotor()));
                sb.append(",");
                sb.append(Float.toString(data.getRightTachoMotor()));
                sb.append(",");
                sb.append(Float.toString(data.getLeftRotationSpeed()));
                sb.append(",");
                sb.append(Float.toString(data.getRightRotationSpeed()));
                sb.append(",");
                sb.append(Float.toString(data.getVoltage()));
                sb.append(",");     
                sb.append(Float.toString(data.getHue()));
                sb.append(",");
                sb.append(Float.toString(data.getSaturation()));
                sb.append(","); 
//                sb.append(Long.toString(data.getTime()));
                sb.append("\r\n");
            }
            
            Date date = new Date() ;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HHmmss") ;
            
            File file = new File(dateFormat.format(date) +"log.csv"); 
            OutputStreamWriter osw  = new OutputStreamWriter(new FileOutputStream(file));
            BufferedWriter bw = new BufferedWriter(osw); 
            bw.write(sb.toString()); 
            bw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}