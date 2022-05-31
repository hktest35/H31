package task;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;

import body.Body;
import detect.BlockThrowDetect;
import detect.ColorDetect;
import detect.Detect;
import detect.DirectionDetect;
import detect.DistanceDetect;
import game.Area;
import game.run.BlockThrow;
import game.run.Curve;
import game.run.Hybrid;
import game.run.OnOff;
import game.run.PID;
import game.run.Run;
import game.run.SingleTurn;
import game.run.HybridTurn;

public class AreaMakeTask extends Thread{
    
    private Body body;
    
    Area area;
    
    private String fileName;
    private LinkedList<String> listString;
    private LinkedList<Run> listRun;
    private LinkedList<Detect[]> listDetect;
    private LinkedList<Integer> listLogicDetect;
    private LinkedList<Float> listTarget;
    private LinkedList<Integer> listAcceleration;
    private LinkedList<Float> listHSB;
    private String[] data = null;
    
    public AreaMakeTask(Area area,String fileName,Body body){
        this.area = area;
        this.body = body;
        this.fileName = fileName;
        this.listRun = new LinkedList<Run>();
        this.listDetect = new LinkedList<Detect[]>();
        this.listLogicDetect = new LinkedList<Integer>();
        this.listTarget = new LinkedList<Float>();
        this.listAcceleration = new LinkedList<Integer>();
        this.listHSB = new LinkedList<Float>();
    }
    
    public AreaMakeTask(Area area,LinkedList<String> listString,Body body){
        this.area = area;
        this.body = body;
        this.listString = listString;
        this.listRun = new LinkedList<Run>();
        this.listDetect = new LinkedList<Detect[]>();
        this.listLogicDetect = new LinkedList<Integer>();
        this.listTarget = new LinkedList<Float>();
        this.listAcceleration = new LinkedList<Integer>();
        this.listHSB = new LinkedList<Float>();
    }
    
    //区間を作成する
    public void run(){
        if(fileName == null){
            HSBReader();
            listReader(listString);
        }else{
            HSBReader();
            csvReader(fileName);
        }
        area.setListRun(listRun);
        area.setListDetect(listDetect);
        area.setListLogicDetect(listLogicDetect);
        area.setListAcceleration(listAcceleration);
        area.setListHSB(listHSB);
        area.setListTarget(listTarget);
    }
    
    
    
    //走行パラメータファイルを読み込む
    private void csvReader(String fileName){
        //ファイル読み込みで使用する３つのクラス
        FileInputStream fi = null;
        InputStreamReader is = null;
        BufferedReader br = null;

        
//        //  ファイルの書き込みで使用する2つのクラス
//        FileWriter f = null;
//        PrintWriter p = null;
        
        try {
            //読み込みファイルのインスタンス生成
            //ファイル名を指定する
            fi = new FileInputStream(fileName);
            is = new InputStreamReader(fi);
            br = new BufferedReader(is);
            
//            f = new FileWriter( "copy"+fileName , false);
//            p = new PrintWriter(new BufferedWriter(f));
            //読み込み行
            String line = "";

            //読み込み行数の管理
            int i = 0;

            //1行ずつ読み込みを行う
            while ((line = br.readLine()) != null) {
                //先頭行は列名
                if (i == 0) {
//                    p.println(line);
                } else {
//                    p.println(line);
                  //カンマで分割した内容を配列に格納する
                    data = line.split(",");
                                 
                  //配列の中身を順位追加する。列数(=列名を格納した配列の要素数)分繰り返す
                    makeListRun();
                    makeListDetect();
                    makeListLogicDetect();
                    makeListTarget();
                    makeListAcceleration();
                    
                }
                i++;
            }
            
//            p.close();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            
            try {
                br.close();
            } catch (Exception e) {    
                e.printStackTrace();
            }
        }
    }
    
    private void listReader(LinkedList<String> listString){
        try {
            String line = "";
            
            //読み込み行数の管理
//            int i = 0;
            
            //1行ずつ読み込みを行う
//            while ((line = listString.pollLast()) != null) {
            for(int i = 0;i<listString.size();i++){
                //先頭行は列名
                if (i == 0) {
                    
                } else {
                    line = listString.get(i);
//                    System.out.println(line);
                    //カンマで分割した内容を配列に格納する
                    data = line.split(",");
                                                     
                    //配列の中身を順位追加する。列数(=列名を格納した配列の要素数)分繰り返す
                    makeListRun();
                    makeListDetect();
                    makeListLogicDetect();
                    makeListTarget();
                    makeListAcceleration();
                    
                }
//                i++;
            }
            
//            p.close();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
    
    
    public void HSBReader( ) {
        String path = "HSB.csv";
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(path) );
            String string = reader.readLine();
            string = reader.readLine();
            while ( string != null ){
                String[] str = string.split( "," );
                listHSB.add( Float.parseFloat( str[1] ) );
                string = reader.readLine();
            }
        }catch( FileNotFoundException e ) {
            System.out.println( "ファイルが見つかりません" );
            System.out.println( "ファイル名 :　" + path );
        }catch( IOException e2 ){
            System.out.println( "!!! ioException !!!" );
        }finally {
            try {
                if( reader != null )
                    reader.close();
            }catch( IOException e2 ) {
                // なにもしない
            }
        }
    }
    
    //走行リストを作成する
    private void makeListRun() throws Exception{
        String statusRun = data[0];
        
        switch (statusRun) {
        case "H":
            listRun.add(new Hybrid(new Curve(body.measure, body.control, Float.parseFloat(data[3]), Float.parseFloat(data[4])),
                        new PID(body.measure, body.control, data[2], Float.parseFloat(data[3]), Float.parseFloat(data[6]), Float.parseFloat(data[7]), Float.parseFloat(data[8])),
                        data[2]));

            break;
            
        case "C":
            listRun.add(new Curve(body.measure, body.control, Float.parseFloat(data[3]), Float.parseFloat(data[4])));                

            break;
            
        case "O":
            listRun.add(new OnOff(body.measure, body.control, data[2],Float.parseFloat(data[3]),Float.parseFloat(data[4])));
            
            break;
            
        case "P":
            listRun.add(new PID(body.measure, body.control, data[2], Float.parseFloat(data[3]), Float.parseFloat(data[6]), Float.parseFloat(data[7]), Float.parseFloat(data[8])));
            
            break;
            
        case "S":
            listRun.add(new SingleTurn(body.measure, body.control, Float.parseFloat(data[3]), data[10]));
            
            break;
            
        case "B":
            listRun.add(new BlockThrow(body.measure, body.control, Float.parseFloat(data[3])));
            
            break;
            
        case "HS":
            listRun.add(new HybridTurn(body.measure, body.control, Float.parseFloat(data[3]),Float.parseFloat(data[5]), data[10]));
            
            break;
            
        default:                               
            break;  
        } 
    }
    
  //目標輝度値リストを作成する
    private void makeListTarget() throws Exception{
        String statusPosition = data[1];
        
        switch (statusPosition) {
        case "C":
            listTarget.add(0.50f);
            break;
            
        case "B":
            listTarget.add(0.25f);
            break;
            
        case "B1":
            listTarget.add(0.45f);
            break;
            
        case "B2":
            listTarget.add(0.40f);
            break;
            
        case "B3":
            listTarget.add(0.35f);
            break;
            
        case "B4":
            listTarget.add(0.30f);
            break;
            
        case "BB":
            listTarget.add(0.125f);
            break;              
    
        case "W":
            listTarget.add(0.75f);
            break; 
            
        case "W1":
            listTarget.add(0.70f);
            break;
            
        case "W2":
            listTarget.add(0.65f);
            break;
            
        case "W3":
            listTarget.add(0.60f);
            break;
            
        case "W4":
            listTarget.add(0.55f);
            break;
            
        case "WW":
           listTarget.add(0.975f);
            break; 
            
        default:
            break;
        }
    }
    
    //区間条件リストを作成する
    private void makeListDetect() throws Exception{
        int detectNumber = Integer.parseInt(data[11]);
        Detect[] detects = new Detect[detectNumber];
        
        for(int i = 0;i < detectNumber; i++){
            String statusDetect = data[12+i*5];
            switch (statusDetect) {
            case "DS":
                detects[i] = new DistanceDetect(Float.parseFloat(data[13+i*5]), body.measure);
    
                break;
                            
            case "DR":
                detects[i] = new DirectionDetect(Float.parseFloat(data[14+i*5]), body.measure);
               
                break;
                
            case "C":
                detects[i] = new ColorDetect(body.measure, data[15+i*5], listHSB);
                
                break;
                
            case "B":
                detects[i] = new BlockThrowDetect(body.measure,body.control);
                
                break;
            default:                               
                break;
            }
        }
        
        listDetect.add(detects);
    }
    
    private void makeListLogicDetect() throws Exception{
        String statusLogic = data[16];
        switch (statusLogic) {
        case "OR":
            listLogicDetect.add(0);
            break;
                        
        case "AND":
            listLogicDetect.add(1);
            break;

        default:
            listLogicDetect.add(0);
            break;
        }
    }
    
    //加速度リストを作成する
    private void makeListAcceleration() throws Exception{        
        listAcceleration.add(Integer.parseInt(data[9]));
    }
    
    //走行リストを取得する
    public LinkedList<Run> getListRun(){
        return listRun;
    }
    
    //区間条件リストを取得する
    public LinkedList<Detect[]> getListDetect(){
        return listDetect;
    }
    
    //区間論理式リストを取得する
    public LinkedList<Integer> getListLogicDetect(){
        return listLogicDetect;
    }
    
    //目標輝度値リストを所得する
    public LinkedList<Float> getListTarget(){
        return listTarget;
    }
    
    //目標彩度値リストを取得する
    public LinkedList<Float> getListHSB(){
        return listHSB;
    }
    
    public LinkedList<Integer> getListAcceleration(){
        return listAcceleration;
    }

}
