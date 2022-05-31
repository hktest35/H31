package game;


import java.util.LinkedList;

import game.Game.STATUS;
import game.run.Curve;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import processing.Client;
import task.Beep;

/**
* 競技クラス
* インスタンスを単一にするため、Singletonパターンを採用
* @author 後藤 聡文
*
*/
public class Game{
    
    private int count;
    
//    private long time;
    
    private float white = 0.0f;
    private float black = 1.0f;
    private float adWhite = 0.0f;
    private float adBlack = 1.0f;
   
    
    private static Game instance = new Game();
    
    public enum STATUS { MODE_SELECT,COURSE_SELECT,ARM_INITIALIZE,BASIC_CALIBRATION, ADVANCE_CALIBRATION, HSB_CALIBRATION, WAITSTART, BASIC, BASIC_STOP, CSV, ADVANCE_READY, ADVANCE_BINGO ,END };
        
    private static STATUS status;
    Area idlingArea;
    Area basicArea;
    Area advanceArea;
    Area parkingArea;
    Area area;
    ModeSelect modeSelect;
    FlieSelect fileSelect;
    Client client;
    Calibration calibration;
    
    private float oldDistance = 0.0f;
    private float currentDistance = 0.0f;
    private float stopCount;
    private boolean csvEnd = false;
    private boolean readEnd = false;
    private boolean connectError = false;
    
    private Game() {
        status = STATUS.MODE_SELECT;
        client = new Client("10.0.1.10");
        try{
            this.idlingArea = new Area();
            idlingArea.areaMake("IDLE.csv");
            idlingArea.idling();
            this.basicArea = new Area();
            this.advanceArea = new Area();
            this.advanceArea.setListString(new LinkedList<String>());
        }catch(Exception e){
            System.out.println("fail out reading");
            status = STATUS.END;
        }
    }
        

    
    public boolean run(){ 
        switch (status) {
        case MODE_SELECT:
            modeSelect = new ModeSelect();
            if(modeSelect.modeSelect()){
                this.area = idlingArea;
                area.setParam();
                this.calibration = new Calibration(area.body.measure,area.body.control,0.5f);
                status = STATUS.ARM_INITIALIZE;
            }else{
                status = STATUS.COURSE_SELECT;
            }
            break;
        case COURSE_SELECT:
            fileSelect = new FlieSelect();
            try{
                if(basicArea.areaMake(fileSelect.getFileName())){
                    this.area = basicArea;
                    area.setParam();
                    status = STATUS.ARM_INITIALIZE;
                }
                
            }catch (Exception e) {
                System.out.println("error");
                status = STATUS.END;
            }
            break;
            
        case HSB_CALIBRATION:
            
            if( calibration.autoCalibration() ){
                Sound.beep();
                status = STATUS.END;
            } 
            break;
            
        case ARM_INITIALIZE :
            if( area.body.isArmInit() || area.body.run()){
                Sound.beep();
                if(modeSelect.modeSelect()){
                    status = STATUS.HSB_CALIBRATION;
                }else{
//                    status = STATUS.BASIC_CALIBRATION;
                    status = STATUS.ADVANCE_CALIBRATION;
                }
            } 
            break;
            

        case ADVANCE_CALIBRATION:
            area.body.update();
            area.body.measure.course.setMinSaturation(
                    (area.getListHSB()).get(0)
                    );
            area.body.measure.course.setMaxSaturation(
                    (area.getListHSB()).get(1)
                    );
            Curve adstraight = new Curve(area.body.measure, area.body.control, 50.0f , 0.0f);
            if(area.body.measure.isUpped()){
                while (area.body.measure.getLeftDistance() <= 150) {
                    area.body.update();
                    adstraight.run();
                    area.body.control.run();
                    if(area.body.measure.getBrightness() >= adWhite){
                        adWhite = area.body.measure.getBrightness();
                    }
                    if(area.body.measure.getBrightness() < adBlack){
                        adBlack = area.body.measure.getBrightness();
                    }
                }
                
                Beep.ring();                 
                area.body.control.wheel.stopLRMotor();
                area.body.control.setLeftSpeed(0);
                area.body.control.setRightSpeed(0);
                area.body.control.reset();
                area.body.update();
                status = STATUS.BASIC_CALIBRATION;
            }
            break;
        //キャリブレーションをする
        case BASIC_CALIBRATION:
            area.body.update();
            area.body.measure.course.setMinSaturation(
                    (area.getListHSB()).get(0)
                    );
            area.body.measure.course.setMaxSaturation(
                    (area.getListHSB()).get(1)
                    );
            Curve straight = new Curve(area.body.measure, area.body.control, 50.0f , 0.0f);
            if(area.body.measure.isUpped()){
                while (area.body.measure.getLeftDistance() <= 150) {
                    area.body.update();
                    straight.run();
                    area.body.control.run();
                    if(area.body.measure.getBrightness() >= white){
                        white = area.body.measure.getBrightness();
                        adWhite = white;
                        area.body.measure.course.setWhite(white);
                    }
                    if(area.body.measure.getBrightness() < black){
                        black = area.body.measure.getBrightness();
                        adBlack = black;
                        area.body.measure.course.setBlack(black);
                    }
                }
                
                Beep.ring(); 
                area.body.measure.course.setTarget((white + black)/2.0f);
                area.body.control.wheel.stopLRMotor();
                area.body.control.setLeftSpeed(0);
                area.body.control.setRightSpeed(0);
                area.body.control.reset();
                area.body.update();
                status = STATUS.WAITSTART;
            }
            break;
            

        //待機する
        case WAITSTART:
            area.body.control.reset();
//            area.body.measure.gyroReset();
            area.body.measure.update();
            if( area.body.measure.getCorrectBrightness() < area.body.measure.getCorrectTarget() - 0.08 ||
                    area.body.measure.getCorrectBrightness() > area.body.measure.getCorrectTarget() + 0.08 ){
                new Thread(new Runnable(){
                    public void run(){
                        Sound.playTone( 600, 150, 10 );
                    }
                }).start();
            }
            if(area.body.measure.isUpped()){
                Beep.ring();
                new Thread(new Runnable(){
                    public void run(){
                        client.sendMessage("start\n");
                    }
                }).start();
                status = STATUS.BASIC;
            }
            break;
            
        //走行する
        case BASIC:
            if(area.run()){
                area.body.control.wheel.stopLRMotor();
                area.body.control.setLeftSpeed(0);
                area.body.control.setRightSpeed(0);
//                area.body.measure.gyroReset();
                area.body.control.reset();
                area.body.measure.update();
                status = STATUS.BASIC_STOP;
            }         
            break;
            
        case BASIC_STOP:
            area.body.measure.update();
            currentDistance = area.body.measure.getLeftDistance();
            if(currentDistance == oldDistance){
                stopCount++;
            }
            oldDistance = currentDistance;
            if(stopCount >= 10){
                if(!(connectError)){
                    new Thread(new Runnable(){
                        public void run(){
                            client.sendMessage(advanceArea.getListString());
                        }
                    }).start();
                }
                    status = STATUS.CSV;
            }
            break;
            
        case CSV:
            if(connectError){
                advanceArea.areaMake("garage"+fileSelect.getCourse()+".csv");
                readEnd = true;
                status = STATUS.ADVANCE_READY;
            }
            if(csvEnd){
                advanceArea.areaMake();
                status = STATUS.ADVANCE_READY;
            }
            break;
            
        case ADVANCE_READY:
            try{
                if(readEnd){
                    this.area = advanceArea;
                    area.setParam();
                    area.body.measure.course.setWhite(adWhite);
                    area.body.measure.course.setBlack(adBlack);
                    area.body.measure.course.setTarget((adWhite + adBlack)/2.0f);
                    status = STATUS.ADVANCE_BINGO;
                }
                
            }catch (Exception e) {
                System.out.println("error");
               status = STATUS.END;
            }
            break;

            
        case ADVANCE_BINGO:
            if(area.run()){
                area.body.control.wheel.stopLRMotor();
                area.body.control.setLeftSpeed(0);
                area.body.control.setRightSpeed(0);
//                area.body.measure.gyroReset();
                area.body.control.reset();
                area.body.measure.update();
                try{                        
                    status = STATUS.END;

                }catch (Exception e) {
                    System.out.println("error");
                    status = STATUS.END;
                }
            }
            break;
            
        default:
            break;
        }
//        long end = System.nanoTime();
//        time = end - start;
        
        if(status != STATUS.COURSE_SELECT && Button.DOWN.isDown() == true){
            area.body.control.wheel.stopLRMotor();
            area.body.control.setLeftSpeed(0);
            area.body.control.setRightSpeed(0);
//            area.body.measure.gyroReset();
            area.body.control.reset();
            area.body.measure.update();
            area.AreaGetReady();
            status  = STATUS.WAITSTART;
        }

        if(status != STATUS.COURSE_SELECT && Button.UP.isDown() == true){
            status  = STATUS.MODE_SELECT;
        }
        
        //止まる
        if(status == STATUS.END){
            area.body.control.wheel.stopLRMotor();
            return true;
        }else{
            return false;
        } 

        
    }
    
    public void setConnectError(boolean connectError) {
        this.connectError = connectError;
    }

    public static Game getInstance(){
        return instance;
    }
    
    public static STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        Game.status = status;
    }
    
    public void countUp(){
        count++;
    }

    public boolean isCsvEnd() {
        return csvEnd;
    }

    public void setCsvEnd(boolean csvEnd) {
        this.csvEnd = csvEnd;
    }
    
    public void setReadEnd(boolean readEnd) {
        this.readEnd = readEnd;
    }
//    public long getTime(){
//        return time;
//    }

    public float getWhite() {
        return white;
    }

    public float getBlack() {
        return black;
    }

    public float getAdWhite() {
        return adWhite;
    }

    public float getAdBlack() {
        return adBlack;
    }
}