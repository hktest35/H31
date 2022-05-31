package task;

import body.Body;
import game.Game;
import game.Log;
import game.Game.STATUS;
/**
* ログタスク
* @author 後藤 聡文
*
*/
public class LogTask extends Thread {
    
    private Game game;
    private Body body;
    private Log log;
    
    public LogTask(Game game,Body body, Log log){
        this.game = game;
        this.body = body;
        this.log = log;
        log.setGame(game);
        log.setBody(body);
    }
    
    @Override
    public void run() {
        log.countUp();
        
        log.disp();
        if(Game.getStatus() == STATUS.BASIC ||
            Game.getStatus() == STATUS.ADVANCE_READY ||
            Game.getStatus() == STATUS.ADVANCE_BINGO ){
                log.add();
        }
    }
    
}