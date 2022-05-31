package task;

import java.util.concurrent.CountDownLatch;

import game.Game;
import lejos.hardware.Button;

/**
* ã£ãZÉ^ÉXÉN
* @author å„ì° ëèï∂
*
*/
public class GameTask extends Thread {
    
    private CountDownLatch countDownLatch;
    
    Game game;
    
    public GameTask(CountDownLatch countDownLatch, Game game){
        this.countDownLatch = countDownLatch;
        this.game = game;
    }
    
    @Override
    public void run() {
        if(Button.ESCAPE.isDown() || game.run()){
            countDownLatch.countDown();
            Beep.ring();
        }
        game.countUp();
    }
    
}