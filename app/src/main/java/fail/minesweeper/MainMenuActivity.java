package fail.minesweeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import AppLogic.GameLogic.GameConfig;

public class MainMenuActivity extends AppCompatActivity {
    private TextView levelText;
    private int levelSelected = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        this.levelText = (TextView) findViewById(R.id.textView4);

        Button selectLevel = (Button) findViewById(R.id.button2);
        selectLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSelectLevelActivity();
            }
        });

        Button playButton = (Button) findViewById(R.id.button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGameActivity();
            }
        });
        setTextLevel();

        Button RecordsButton = (Button) findViewById(R.id.button3);
        RecordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRecordActivity();
            }
        });
    }

    private void goToRecordActivity() {
        Intent intent = new Intent(this, RecordActivity.class);
        startActivity(intent);
    }

    private void setTextLevel(){
        SharedPreferences gameSettings = getSharedPreferences(GameConfig.PREFERENCE_NAME,0);
        levelSelected = gameSettings.getInt(GameConfig.LEVEL_PREF,GameConfig.BEGINNER_LEVEL);
        levelText.setText(GameConfig.LEVEL_LABELS[levelSelected]);
    }
    private void goToSelectLevelActivity(){
        Intent intent = new Intent(this, SelectLevelActivity.class);
        startActivity(intent);
    }

    private void goToGameActivity(){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("level",this.levelSelected);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTextLevel();
    }
}
