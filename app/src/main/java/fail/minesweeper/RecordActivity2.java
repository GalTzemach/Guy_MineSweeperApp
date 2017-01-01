package fail.minesweeper;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import AppLogic.GameLogic.GameConfig;
import AppLogic.RecordsLogic.GameRecord;
import AppLogic.RecordsLogic.RecordController;

public class RecordActivity2 extends AppCompatActivity {
    private RecordController recCon;
    private TableLayout table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        recCon = new RecordController(this);

        LinearLayout myMainLayout = new LinearLayout(this);
        myMainLayout.setOrientation(LinearLayout.VERTICAL);
        this.addContentView(myMainLayout,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));

        LinearLayout levelSelectLayout = new LinearLayout(this);
        levelSelectLayout.setOrientation(LinearLayout.HORIZONTAL);
        levelSelectLayout.setBackgroundColor(Color.WHITE);
        levelSelectLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));

        TextView title = new TextView(this);
        title.setText("LEADERSHIP");
        title.setTextColor(Color.parseColor("#000000"));
        title.setTextSize(48);
        title.setGravity(Gravity.CENTER);
        myMainLayout.addView(title);

        table = new TableLayout(this);

        Button beginnerBut = new Button(this);
        beginnerBut.setText(GameConfig.LEVEL_LABELS[0]);
        beginnerBut.setTextColor(Color.parseColor("#000000"));
        beginnerBut.setBackgroundColor(Color.WHITE);
        levelSelectLayout.addView(beginnerBut);

        Button advancedBut = new Button(this);
        advancedBut.setText(GameConfig.LEVEL_LABELS[1]);
        advancedBut.setTextColor(Color.parseColor("#000000"));
        advancedBut.setBackgroundColor(Color.WHITE);
        levelSelectLayout.addView(advancedBut);

        Button expertBut = new Button(this);
        expertBut.setText(GameConfig.LEVEL_LABELS[2]);
        expertBut.setTextColor(Color.parseColor("#000000"));
        expertBut.setBackgroundColor(Color.WHITE);
        levelSelectLayout.addView(expertBut);

        beginnerBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("orel","Enter to print table by pressing BEGINNER button");
                printTable( table , GameConfig.BEGINNER_LEVEL);
            }
        });

        advancedBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("orel","Enter to print table by pressing ADVANCED button");
                printTable( table , GameConfig.ADVANCED_LEVEL);
            }
        });

        expertBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("orel","Enter to print table by pressing EXPERT button");
                printTable( table , GameConfig.EXPERT_LEVEL);
            }
        });

        levelSelectLayout.setGravity(Gravity.CENTER);
        myMainLayout.addView(levelSelectLayout);
        myMainLayout.addView(table);

    }

    private void printTable(TableLayout table , int level ){
        Log.v("orel","Enter to print Table");
        ArrayList<GameRecord> recordsArray = recCon.getRecordsArray(level);
        Log.v("orel",""+recordsArray.size());
        for (int i=1 ; i <= recordsArray.size() ; i++)
            printRow(table , i , recordsArray.get(i-1));
    }

    private void printRow(TableLayout table , int rank , GameRecord record){
        Log.v("orel","Printing row: Rank#"+rank+" Name:"+record.getName()+" Time:"+record.getRecordTime());
        TableRow tr = new TableRow(this);
        TextView rankIndex = new TextView(this);
        rankIndex.setTextColor(Color.parseColor("#000000"));
        rankIndex.setText(("#"+(Integer)rank).toString());
        rankIndex.setTextSize(28);
        rankIndex.setWidth(150);

        TextView nameField = new TextView(this);
        nameField.setTextColor(Color.parseColor("#000000"));
        nameField.setText(record.getName());
        nameField.setTextSize(28);
        nameField.setWidth(700);

        TextView timeField = new TextView(this);
        timeField.setTextColor(Color.parseColor("#000000"));
        timeField.setText(printRecord(record.getRecordTime()));
        timeField.setTextSize(28);

        tr.addView(rankIndex);
        tr.addView(nameField);
        tr.addView(timeField);

        table.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
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
