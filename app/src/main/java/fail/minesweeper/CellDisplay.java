package fail.minesweeper;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import AppLogic.GameLogic.GameConfig;


public class CellDisplay extends LinearLayout {
    private TextView textView;

    public CellDisplay(Context context) {
        super(context);
        this.setOrientation(VERTICAL);
        textView = new TextView(context);
        this.addView(textView);
        //doLayoutParams();
        textView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextSize(20);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        llp.setMargins(5,5,5,5);
        textView.setLayoutParams(llp);
        textView.setTextColor(Color.BLACK);
    }

    public void setInfo(int value, boolean isHidden, boolean isFlagged){
        String text;
        if(isHidden){
            text = "";
            textView.setBackgroundColor(Color.parseColor("#ffffff"));
            textView.setTextColor(Color.BLACK);
            setClickable(false);
        }else{
            setClickable(true);
            if(value==0){
                text="";
            }else{
                text= Integer.toString(value);
                textView.setTextColor(Color.parseColor(GameConfig.VALUE_COLORS[value-1]));
            }
            textView.setBackgroundColor(Color.LTGRAY);

        }
        if(isFlagged) {
            text = "+";
            textView.setTextColor(Color.BLACK);
        }
        textView.setText(text);
    }
}
