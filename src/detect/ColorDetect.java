package detect;

import java.util.LinkedList;

import body.measure.Measure;

public class ColorDetect extends Detect {
    
    private enum ColorName{
        Red,
        Green,
        Blue,
        Yellow,
        Black,
        White,
        Gray,
    };
    
    private Measure measure;
    private ColorName colorName;

    // TODO saturation 0.45fŒ³‚Ì’l
    private float saturationLowerLimit;
    private int detectCount;
    private float upperLimit;
    private float lowerLimit;
    
    public ColorDetect(Measure measure,String colorName,LinkedList<Float> HSB) {
        super(measure);
        this.measure=measure;
        this.detectCount = 0;
        
        switch( colorName ){
        case "R":
            lowerLimit = HSB.get(2);
            upperLimit = HSB.get(3);
            break;
        case "GR":
            lowerLimit = HSB.get(4);
            upperLimit = HSB.get(5);
            break;
        case "BL":
            lowerLimit = HSB.get(6);
            upperLimit = HSB.get(7);
            break;
        case "Y":
            double percent = (HSB.get(9) - HSB.get(8)) / 10.0;
            lowerLimit = (float)(HSB.get(8) - percent*5) <= 0.0 ? 0.0f : (float)(HSB.get(8) - percent*5);
            upperLimit = (float)(HSB.get(9) + percent*5);
            break;
        case "W":
            upperLimit=1.0f;
            lowerLimit=0.6f;
            break;
        case "BK":
            upperLimit=0.20f;
            lowerLimit=0.0f;
            break;
        case "GY":
            upperLimit=0.58f;
            lowerLimit=0.42f;
            break;
        case "L":
            upperLimit=0.58f;
            lowerLimit=0.0f;
            break;
        case "LW":
            upperLimit=0.75f;
            lowerLimit=0.0f;
            break;
            
        }
        
        if(colorName.equals("R")){
            this.colorName=ColorName.Red;
        }else if(colorName.equals("GR")){
            this.colorName=ColorName.Green;
        }else if(colorName.equals("BL")){
            this.colorName=ColorName.Blue;
        }else if(colorName.equals("Y")){
            this.colorName=ColorName.Yellow;
        }else if(colorName.equals("BK")){
            this.colorName=ColorName.Black;
        }else if(colorName.equals("W") ){
            this.colorName=ColorName.White;
        }else if(colorName.equals("GY") ){
            this.colorName=ColorName.Gray;
        }else if(colorName.equals("L") ){
            this.colorName=ColorName.Black;
        }else if(colorName.equals("LW") ){
            this.colorName=ColorName.Black;
        }
        
        
        saturationLowerLimit = (HSB.get(0) + HSB.get(1)) / 2.0f;
        
    }


    @Override
    public boolean detect() {
        float[] HSB = measure.course.getHSB();
        float brightness = measure.getCorrectBrightness();


        if( colorName == ColorName.White ||
            colorName == ColorName.Black ||
            colorName == ColorName.Gray  ){

            if( brightness >= lowerLimit && brightness <= upperLimit){
                detectCount++;
            }else{
                detectCount = 0;
            }

            if( detectCount >= 3 ){
                return true;
            }else{
                return false;
            }
        }else{
            if(HSB[1]>saturationLowerLimit){
                if( HSB[0] >= lowerLimit && HSB[0] <= upperLimit){
                    detectCount++;
                }else{
                    detectCount = 0;
                }

                if( detectCount >= 3 ){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }
    }

}
