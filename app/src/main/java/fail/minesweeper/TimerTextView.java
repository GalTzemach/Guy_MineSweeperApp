package fail.minesweeper;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by Guy on 30/11/2016.
 */

public class TimerTextView extends TextView implements ThreadTimerListener{
    public TimerTextView(Context context) {
        super(context);
        this.setTextColor(Color.parseColor("#000000"));
        this.setTextSize(20);
        this.setGravity(Gravity.CENTER);
        this.setText("00:00");
    }
    public void updateSec(int totalSec){
        String minStr;
        String secStr;
        int min = totalSec/60;
        int sec = totalSec%60;
        if(min<10)  { minStr = "0"+Integer.toString(min);}
        else        { minStr = Integer.toString(min);    }
        if(sec<10)  { secStr = "0"+Integer.toString(sec);}
        else        { secStr = Integer.toString(sec);    }
        this.setText(""+minStr+":"+secStr+"");
    }

}
