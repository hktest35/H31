package body.measure;


//import java.awt.Color;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
/**
* 路面計測クラス
* @author 後藤 聡文
*
*/
public class Course {
    private EV3ColorSensor colorSensor;
    private SensorMode sensorMode;
    private float[] RGB;
    private float[] HSB;
    private float white;
    private float black;
    private float target;
    private float brightness;
    private float saturation;    
    private float maxSaturation;
    private float minSaturation;
    private float correctBrightness;
    private float correctSaturation;
    private float correctTarget = 0.50f;

    public Course(EV3ColorSensor colorSensor){
        this.colorSensor = colorSensor;
        sensorMode = colorSensor.getRGBMode();
        RGB = new float[sensorMode.sampleSize()];
        HSB = new float[sensorMode.sampleSize()];
    }
    
    /*private void getColor(){
        sensorMode.fetchSample(RGB, 0);
        color = new Color(RGB[0], RGB[1], RGB[2]);
    }*/
    /**
    * 更新する
    */
    public void update(){
        //b
        sensorMode.fetchSample(RGB, 0);
        RGBtoHSB();
        saturation = HSB[1];
        brightness = HSB[2];
    }
    
    /**
    * 光バンドセンサ補正値を取得する
    */
    public float getCorrectBrightness() {
        return calculateCorrect();
    }
    
    public float getCorrectSaturation(){
        return calculateSaturationCorrect();
    }
    
    private float calculateCorrect() {
        if(brightness > white){
            brightness = white;
        }else if(brightness < black){
            brightness = black;
        }
        correctBrightness =  ((brightness - black)/(white - black));
        return correctBrightness;
    }
    
    private float calculateSaturationCorrect() {
        if(saturation > maxSaturation){
            saturation = maxSaturation;
        }else if(saturation < minSaturation){
            saturation = minSaturation;
        }
        correctSaturation =  (saturation - minSaturation)/(maxSaturation - minSaturation);
        return correctSaturation;
    }
    
    private void RGBtoHSB() {
        int r = (int) (RGB[0]*255.0f+0.5f);
        int g = (int) (RGB[1]*255.0f+0.5f);
        int b = (int) (RGB[2]*255.0f+0.5f);
        
        float hue, saturation, brightness;
        if (HSB == null) {
            HSB = new float[3];
        }
        int cmax = (r > g) ? r : g;
        if (b > cmax) cmax = b;
        int cmin = (r < g) ? r : g;
        if (b < cmin) cmin = b;

        brightness = ((float) cmax) / 255.0f;
        if (cmax != 0)
            saturation = ((float) (cmax - cmin)) / ((float) cmax);
        else
            saturation = 0;
        if (saturation == 0)
            hue = 0;
        else {
            float redc = ((float) (cmax - r)) / ((float) (cmax - cmin));
            float greenc = ((float) (cmax - g)) / ((float) (cmax - cmin));
            float bluec = ((float) (cmax - b)) / ((float) (cmax - cmin));
            if (r == cmax)
                hue = bluec - greenc;
            else if (g == cmax)
                hue = 2.0f + redc - bluec;
            else
                hue = 4.0f + greenc - redc;
            hue = hue / 6.0f;
            if (hue < 0)
                hue = hue + 1.0f;
        }
        HSB[0] = hue;
        HSB[1] = saturation;
        HSB[2] = brightness;
    }
    public float[] getHSB(){
        return HSB;
    }
    
    public float getHue(){
        return HSB[0];
    }
    
    public float getSaturation(){
        return HSB[1];
    }
    
    public float getBrightness() {
        return HSB[2];
    }
    
    public float getMaxSaturation() {
        return maxSaturation;
    }

    public void setMaxSaturation(float maxSaturation) {
        this.maxSaturation = maxSaturation;
    }

    public float getMinSaturation() {
        return minSaturation;
    }

    public void setMinSaturation(float minSaturation) {
        this.minSaturation = minSaturation;
    }

    public float getCorrectTarget() {
        return correctTarget;
    }
    
    public float getWhite() {
        return white;
    }
    
    public void setWhite(float white) {
        this.white = white;
    }
    
    public float getBlack() {
        return black;
    }
    
    public void setBlack(float black) {
        this.black = black;
    }
    
    public float getTarget() {
        return target;
    }
    
    public void setCorrectTarget(float correctTarget) {
        this.correctTarget = correctTarget;
    }

    public void setTarget(float target) {
        this.target = target;
    }
    

}