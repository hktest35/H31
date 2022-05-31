package task;

import java.util.concurrent.CountDownLatch;

import body.Body;
import lejos.hardware.Button;

public class BodyTask extends Thread {
    
    private CountDownLatch countDownLatch;
    
    Body body;
    
    public BodyTask(CountDownLatch countDownLatch, Body body){
        this.countDownLatch = countDownLatch;
        this.body = body;
    }
    
    @Override
    public void run() {
        if(Button.ESCAPE.isDown()){
            countDownLatch.countDown();
            Beep.ring();
        }
        
    }
    
}