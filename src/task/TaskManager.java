package task;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import body.Body;
import game.Game;
import game.Log;
import lejos.hardware.lcd.LCD;

/**
* �^�X�N�Ǘ��N���X
* @author �㓡 ����
*
*/
public class TaskManager {
    
    // ���Z�^�X�N
    private GameTask gameTask;
    // ���O�^�X�N
    private LogTask logTask;
    //���s�̃^�X�N
//    private BodyTask bodyTask;
    //�X�P�W���[��
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> futureGame;
    private ScheduledFuture<?> futureLog;
//    private ScheduledFuture<?> futureBody;
    private CountDownLatch countDownLatch;
    
    public TaskManager(){
        //�^�X�N������ �J�n
        LCD.drawString("Initialize", 0, 0);
        
        //�X�P�W���[������
        scheduler = Executors.newScheduledThreadPool(2);
        countDownLatch = new CountDownLatch(1);
        
        //�^�X�N����
        gameTask = new GameTask(countDownLatch, Game.getInstance());
        gameTask.setPriority(Thread.MAX_PRIORITY);
//        bodyTask = new BodyTask(countDownLatch, Body.getInstance());
//        bodyTask.setPriority(Thread.MAX_PRIORITY);
        logTask = new LogTask(Game.getInstance(), Body.getInstance(), Log.getInstance());
        logTask.setPriority(Thread.NORM_PRIORITY);
        
        //�^�X�N�������I��
        LCD.clear();
        Beep.ring();
    }
    
    /**
    * �^�X�N�̃X�P�W���[�����O
    */
    public void schedule(){
        futureGame = scheduler.scheduleAtFixedRate(gameTask, 0, 5, TimeUnit.MILLISECONDS);
//        futureBody = scheduler.scheduleAtFixedRate(bodyTask, 0, 5, TimeUnit.MILLISECONDS);
        futureLog = scheduler.scheduleAtFixedRate(logTask, 0, 100, TimeUnit.MILLISECONDS);
    }
    /**
    * ���Z�^�X�N���I������܂ő҂�
    */
    public void await(){
        try{
            countDownLatch.await();
        }catch(InterruptedException e){
            
        }
    }
    /**
    * �^�X�N�̎��s�̎������ƃX�P�W���[���̃V���b�g�_�E��
    */
    public void shutdown(){
        if(futureLog != null){
            futureLog.cancel(true);
        }
        if(futureGame != null){
            futureGame.cancel(true);
        }
//        if(futureBody != null){
//            futureBody.cancel(true);
//        }
        scheduler.shutdownNow();
        Log.getInstance().write();
    }
}