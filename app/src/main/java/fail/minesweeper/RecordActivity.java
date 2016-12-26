package fail.minesweeper;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import AppLogic.GameLogic.GameConfig;

public class RecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        SharedPreferences gameSettings = getSharedPreferences(GameConfig.PREFERENCE_NAME,0);
        LinearLayout myMainLayout = new LinearLayout(this);
        myMainLayout.setOrientation(LinearLayout.VERTICAL);
        this.addContentView(myMainLayout,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(50 , 30 , 30 ,30);


        TextView title = new TextView(this);
        title.setText("LEADERSHIP");
        title.setTextColor(Color.parseColor("#000000"));
        title.setTextSize(48);
        title.setGravity(Gravity.CENTER);
        myMainLayout.addView(title );


        TextView beginner = new TextView(this);
        beginner.setText(GameConfig.LEVEL_RECORDS[0]);
        beginner.setTextColor(Color.parseColor("#000000"));
        beginner.setTextSize(28);

        TextView beginnerRecord = new TextView(this);
        beginnerRecord.setTextColor(Color.parseColor("#000000"));
        beginnerRecord.setText(printRecord(gameSettings.getInt(GameConfig.LEVEL_RECORDS[0],0)));
        Log.v("record" , "beginner Record: "+Integer.toString(gameSettings.getInt(GameConfig.LEVEL_RECORDS[0],0)));
        beginnerRecord.setTextSize(28);
        myMainLayout.addView(beginner , layoutParams);
        myMainLayout.addView(beginnerRecord , layoutParams);


        TextView advenced = new TextView(this);
        advenced.setText(GameConfig.LEVEL_RECORDS[1]);
        advenced.setTextColor(Color.parseColor("#000000"));
        advenced.setTextSize(28);

        TextView advancedRecord = new TextView(this);
        advancedRecord.setTextColor(Color.parseColor("#000000"));
        advancedRecord.setText(printRecord(gameSettings.getInt(GameConfig.LEVEL_RECORDS[1],0)));
        Log.v("record" , "advanced Record: "+Integer.toString(gameSettings.getInt(GameConfig.LEVEL_RECORDS[1],0)));
        advancedRecord.setTextSize(28);
        myMainLayout.addView(advenced, layoutParams);
        myMainLayout.addView(advancedRecord, layoutParams);

        TextView expert = new TextView(this);
        expert.setText(GameConfig.LEVEL_RECORDS[2]);
        expert.setTextColor(Color.parseColor("#000000"));
        expert.setTextSize(28);

        TextView expertRecord = new TextView(this);
        expertRecord.setTextColor(Color.parseColor("#000000"));
        expertRecord.setText(printRecord(gameSettings.getInt(GameConfig.LEVEL_RECORDS[2],0)));
        Log.v("record" , "expert Record: "+Integer.toString(gameSettings.getInt(GameConfig.LEVEL_RECORDS[2],0)));
        expertRecord.setTextSize(28);
        myMainLayout.addView(expert, layoutParams);
        myMainLayout.addView(expertRecord, layoutParams);
    }

    private String printRecord(int timeMs){
        if(timeMs == 0){
            return "No record";
        }
        String record;
        String minStr;
        String secStr;
        int totalSec = timeMs/1000;
        int min = totalSec/60;
        int sec = totalSec%60;
        if(min<10)  { minStr = "0"+Integer.toString(min);}
        else        { minStr = Integer.toString(min);    }
        if(sec<10)  { secStr = "0"+Integer.toString(sec);}
        else        { secStr = Integer.toString(sec);    }
        record =(""+minStr+":"+secStr+"");

        return record;
    }
}
