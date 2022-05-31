package game;

import java.util.LinkedList;

import body.Body;
import detect.Detect;
import game.run.BlockThrow;
import game.run.Curve;
import game.run.Hybrid;
import game.run.OnOff;
import game.run.PID;
import game.run.Run;
import game.run.SingleTurn;
import task.AreaMakeTask;
import task.Beep;

/**
* 競技クラス
* インスタンスを単一にするため、Singletonパターンを採用
* @author 後藤 聡文
*
*/
public class Area {
    
    

    Run run;
    Detect[] detects;
    int acceleration;
    float target;
    Body body;
    int logicType;
    

    //区間作成
    AreaMakeTask areaMakeTask;
    
    //区間数
    private int areaCount;
    
    //現在の区間
    private int currentArea = 0;    
    
    private boolean[] judgements;
    
    private LinkedList<String> listString;
    private LinkedList<Run> listRun;
    private LinkedList<Detect[]> listDetect;
    private LinkedList<Integer> listLogicDetect;
    private LinkedList<Float> listTarget;
    private LinkedList<Integer> listAcceleration;
    private LinkedList<Float> listHSB;
        
    public Area(){      
        this.body = Body.getInstance();
    }
    
    
    /**
    * 実施する
    * @return 実施中はfalse、終了時はtrueを返す
    */
    public boolean run(){ 
        //センサー類を更新する
        body.measure.update();
        
        //角速度を計算する
        run.run();    
        
        //角速度を設定する
        body.control.run();
        
        //エリア区間の切り替え判定をする
        if (changeArea()) {
            return true;
        }
        return false;
    }
    
    public void idling(){
//        PID pid = new PID(body.measure, body.control, "L", 0, 0, 0, 0);
//        Curve curve = new Curve(body.measure, body.control, 0, 0);
//        Hybrid hybrid = new Hybrid(curve, pid, "L");
//        OnOff onOff = new OnOff(body.measure, body.control,"L", 0,0.0f);
//        BlockThrow blockThrow = new BlockThrow(body.measure, body.control, 0.0f);
//        SingleTurn singleTurn = new SingleTurn(body.measure, body.control,0.0f, "L"); 
//        body.idling();
        for(int i=0; i<1500; i++){
            body.measure.update();
//            pid.run();
//            curve.run();
//            hybrid.run();
//            onOff.run();
//            blockThrow.run();
//            singleTurn.run();
            body.control.setLeftSpeed(0.0f);
            body.control.setRightSpeed(0.0f);
            body.control.setArmAngleTarget(0.0f);
            body.control.run();
            changeArea();
            areaChange();
        }
        body.control.setArmAngleTarget(40.0f);
    }
    
    /**
    * 検知条件を判定する   
    * @return 実施中はfalse、終了時はtrueを返す
    */
    public boolean changeArea(){
        if(judge()){
            currentArea++;
            
            
//            Beep.ring();
            
            
            //最後のエリアかを判定する
            if(currentArea != areaCount){
              //次の区間の情報を取得する
                areaChange();
            }else{
                return true;
            }    
        }
        return false;
    }
    
    public boolean judge(){
        
        for( int i=0; i<detects.length; i++ ){
            judgements[i] = detects[i].detect();
        }
        if( detects.length> 1 ){        // 隍�蜷域擅莉ｶ縺ｮ譎�

            for( int i=0; i<judgements.length; i++ ){
                switch( logicType ){
                case 0 :
                    if( judgements[i] == true ){
                        return true;
                    }else{
                        return false;
                    }
                case 1 :
                    if( judgements[i] == false ){
                        return false;
                    }else if( i >= judgements.length-1 ){
                        return true;
                    }else{
                        
                    }
                    break;
                default :
                    System.out.println( "logic argumentation is incorrect");
                    break;
                }
            }
        }
        return judgements[0];
    }
    
    private void areaChange(){
        this.acceleration = listAcceleration.get(currentArea);
        body.control.wheel.SetAcceleration(acceleration);
        body.control.reset();
//        body.measure.gyroReset();
        this.target = listTarget.get(currentArea);
        body.measure.course.setCorrectTarget(target);
        this.run = listRun.get(currentArea);          
        this.detects = listDetect.get(currentArea);
        judgements = new boolean[detects.length];
        this.logicType = listLogicDetect.get(currentArea);
    }
    
    //現在のエリアを取得する
    public int getCurrentArea() {
        return currentArea;
    }
    
    //ファイル名から区間を作成する
    public boolean areaMake(String filename){
        //区間を作成する
        this.areaMakeTask = new AreaMakeTask(this, filename, body);
        areaMakeTask.setPriority(Thread.MAX_PRIORITY);
        areaMakeTask.start();
        
        try {
            areaMakeTask.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AreaGetReady();
        
        return true;
    }
    
    public boolean areaMake(){
        //区間を作成する
        this.areaMakeTask = new AreaMakeTask(this, listString, body);
        areaMakeTask.setPriority(Thread.MAX_PRIORITY);
        areaMakeTask.start();
        
        try {
            areaMakeTask.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Game.getInstance().setReadEnd(true);
        AreaGetReady();
        
        return true;
    }
    
    public void AreaGetReady(){
        
        //1エリア目を取得する
        this.run = listRun.get(0);
        this.detects = listDetect.get(0);
        judgements = new boolean[detects.length];
        this.logicType = listLogicDetect.get(0);
        this.target = listTarget.get(0);
        this.acceleration = listAcceleration.get(0);
        
        body.control.wheel.SetAcceleration(acceleration);
        body.measure.course.setCorrectTarget(target);
        

        //区間数を取得する
        this.areaCount = listRun.size();
        
        //現在の区間を初期化する
        this.currentArea = 0;
    }
    
    public void setParam(){
        body.control.wheel.SetAcceleration(acceleration);
        body.measure.course.setCorrectTarget(target);
    }

    public LinkedList<String> getListString() {
        return listString;
    }


    public void setListString(LinkedList<String> listString) {
        this.listString = listString;
    }


    public void setListRun(LinkedList<Run> listRun) {
        this.listRun = listRun;
    }

    public void setCurrentArea(int currentArea) {
        this.currentArea = currentArea;
    }


    public void setListDetect(LinkedList<Detect[]> listDetect) {
        this.listDetect = listDetect;
    }

    public void setListLogicDetect(LinkedList<Integer> listLogicDetect) {
        this.listLogicDetect = listLogicDetect;
    }

    
    public void setListTarget(LinkedList<Float> listTarget) {
        this.listTarget = listTarget;
    }

    public void setListAcceleration(LinkedList<Integer> listAcceleration) {
        this.listAcceleration = listAcceleration;
    }

    public void setListHSB(LinkedList<Float> listHSB) {
        this.listHSB = listHSB;
    }
    
    public LinkedList<Float> getListHSB(){
        return listHSB;
    }
    
    
}