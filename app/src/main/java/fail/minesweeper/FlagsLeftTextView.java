package fail.minesweeper;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import AppLogic.GameLogic.FlagsListener;
import AppLogic.GameLogic.GameConfig;

/**
 * Created by Guy on 27/11/2016.
 */

public class FlagsLeftTextView extends TextView implements FlagsListener{
    private int flags;

    public FlagsLeftTextView(Context context,int flags) {
        super(context);
        this.flags = flags;
        this.setTextColor(Color.parseColor("#000000"));
        this.setTextSize(20);
        this.setGravity(Gravity.CENTER);
        this.updateText();
    }
    public void setFlags(int flags){
        this.flags = flags;
        this.updateText();
    }
    private void updateText(){
        this.setText(flags+ " Flags left");
    }
    @Override
    public void flagMarked() {
        this.flags--;
        this.updateText();
    }

    @Override
    public void flagUnmarked() {
        this.flags++;
        this.updateText();
    }
}
