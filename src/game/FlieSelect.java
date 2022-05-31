package game;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

public class FlieSelect {
    
    static boolean course = false; //falseの時はLコース,trueの時はRコース
    static int index = 0;
    String fileName = "IDLE";
    boolean upButtonOldStatus = false;
    boolean downButtonOldStatus = false;
    
    int dispLength;
    
    
    ArrayList<String> runListR = new ArrayList<String>();
    ArrayList<String> runListL = new ArrayList<String>();
    ArrayList<String> runList = new ArrayList<String>();
    
    public FlieSelect() {
        
        // 一覧を取得する対象の親フォルダ
        String targetFolderPath = "/home/lejos/programs";
        File targetFolder = new File(targetFolderPath);
        
        runList = runListL;
        // フォルダ中の子フォルダ・子ファイル一覧を取得し、ループする
        for (String name : targetFolder.list()) {
          // 親フォルダと、子フォルダ・子ファイル名を結合し、絶対パスとする
          String path = Paths.get(targetFolderPath, name).toString();
          
          if(name.endsWith(".csv")){
             if(name.startsWith("L")){
                 runListL.add(name);
             }else if (name.startsWith("R")) {
                 runListR.add(name);
             }
          }
        }
        Collections.sort(runListR);
        Collections.sort(runListL);
        
        
        
        LCD.drawString("Please Wait...  ", 0, 4);
        LCD.clear();
        Sound.beep();
        disp();
        while( !Button.ENTER.isDown() ){
            if(Button.ESCAPE.isDown()){
                return;
            }
            
            if(isUpButtonUpped()){
                if(index  == 0){
                    
                }else{
                    index--;
                }
                disp();
                    
            }else if(isDownButtonUpped()){
                if(index  == runList.size()-1){
                    
                }else{
                    index++;
                }
                disp();
            }
            
            
            if( Button.LEFT.isDown() ){
                course = true;
                runList = runListR;
                index = 0;
                disp();
            }else if( Button.RIGHT.isDown() ){
                course = false;
                runList = runListL;
                index = 0;
                disp();
            }
            
        }
        fileName = runList.get(index);
    }
    
    public String getFileName() {
        return fileName;
    }

    void disp(){
        LCD.clear();
        if( course ){
            LCD.drawString("this is R Course", 0, 0);
        }else{
            LCD.drawString("this is L Course", 0, 0);
        }
        LCD.drawString(runList.get(index), 3, 3);
        LCD.drawString(">", 0, 3);
    }
    
    public boolean isUpButtonUpped(){
        boolean upButtonStatus = Button.UP.isDown();
        
        if(upButtonOldStatus == true && upButtonStatus == false){
            upButtonOldStatus = upButtonStatus;
            return true;
        }else{
            upButtonOldStatus = upButtonStatus;
            return false;
        }
    }
    
    public boolean isDownButtonUpped(){
        boolean downButtonStatus = Button.DOWN.isDown();
        
        if(downButtonOldStatus == true && downButtonStatus == false){
            downButtonOldStatus = downButtonStatus;
            return true;
        }else{
            downButtonOldStatus = downButtonStatus;
            return false;
        }
    }
    
    public String getCourse(){
        if(course){
            return "R";
        }else{
            return "L";
        }
    }

}
