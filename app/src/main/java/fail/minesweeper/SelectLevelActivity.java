package fail.minesweeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Array;
import java.util.ArrayList;

import AppLogic.GameLogic.GameConfig;

public class SelectLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_level);
        ArrayList<Button> Buttuns = new ArrayList<>();
        Buttuns.add((Button) findViewById(R.id.button3));
        Buttuns.add((Button) findViewById(R.id.button4));
        Buttuns.add((Button) findViewById(R.id.button5));

        for(final Button b : Buttuns){
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int level = 0;
                    switch(((String)b.getText()).toLowerCase()){
                        case "begginer":    level = GameConfig.BEGINNER_LEVEL;   break;
                        case "advenced":    level = GameConfig.ADVANCED_LEVEL;   break;
                        case "expert":      level = GameConfig.EXPERT_LEVEL;     break;
                    }
                    returnToMainWithResult(level);
                }
            });
        }
    }
    private void returnToMainWithResult(int level){
        Intent resultIntent  = new Intent();
        //setResult(level, resultIntent);
        SharedPreferences gameSettings = getSharedPreferences(GameConfig.PREFERENCE_NAME,0);
        SharedPreferences.Editor editor = gameSettings.edit();
        editor.putInt(GameConfig.LEVEL_PREF,level);
        editor.commit();
        finish();
    }
}
