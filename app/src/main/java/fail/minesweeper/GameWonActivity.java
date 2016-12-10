package fail.minesweeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import AppLogic.GameLogic.GameConfig;

public class GameWonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_won);
        LinearLayout myMainLayout = new LinearLayout(this);
        myMainLayout.setOrientation(LinearLayout.VERTICAL);
        this.addContentView(myMainLayout,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        TextView title = new TextView(this);
        title.setTextColor(Color.parseColor("#000000"));
        title.setTextSize(28);
        title.setText("Game Won!");
        title.setGravity(Gravity.CENTER);
        myMainLayout.addView(title);
        Button tryAgainButton = new Button(this);
        tryAgainButton.setText("New Game?");
        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent  = new Intent();
                setResult(GameConfig.NEW_GAME_CODE, resultIntent);
                finish();
            }
        });
        myMainLayout.addView(tryAgainButton);
        checkRecord();

    }

    public void checkRecord(){
        int level  = getIntent().getIntExtra("level" , 0); // should be -1
        SharedPreferences gameSettings = getSharedPreferences(GameConfig.PREFERENCE_NAME,0);
        int currentRecord = gameSettings.getInt(GameConfig.LEVEL_RECORDS[level],0);
        int newRecord = getIntent().getIntExtra("totalTime" , 0);
        Log.v("orel", "GameWonActivity check record : "+ currentRecord +" new: "+ newRecord+ "level:"+ GameConfig.LEVEL_RECORDS[level]);
        if(currentRecord > 0) {
            if (currentRecord > newRecord)
                setRecord(newRecord , level);
            Log.v("orel", "No Change currentRecord < newRecord"+ " level: "+ GameConfig.LEVEL_RECORDS[level]);
        }else setRecord(newRecord , level);
    }

    public void setRecord(int newRecord , int level){
        SharedPreferences gameSettings = getSharedPreferences(GameConfig.PREFERENCE_NAME,0);
        SharedPreferences.Editor editor = gameSettings.edit();
        editor.putInt(GameConfig.LEVEL_RECORDS[level],newRecord);
        Log.v("orel", "set new record : "+ newRecord+ " level:"+ GameConfig.LEVEL_RECORDS[level]);
        editor.commit();
    }
}
