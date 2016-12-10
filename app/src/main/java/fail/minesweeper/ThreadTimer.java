package fail.minesweeper;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by Guy on 30/11/2016.
 */

public class ThreadTimer extends Thread implements Runnable{
    private int sec;
    private ThreadTimerListener listener;
    private volatile boolean running = true;

    public ThreadTimer(ThreadTimerListener listener){
        this.listener = listener;
        this.sec = 0;
    }

    public void run() {
        while (running){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.sec++;
            Handler mHandler = new Handler(Looper.getMainLooper());
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    listener.updateSec(sec);
                }
            });
        }
    }

    public void terminate() {
        running = false;
    }
    public void setSec(int sec){
        this.sec = sec;
    }
    public int getSec(){
        return this.sec;
    }

}
