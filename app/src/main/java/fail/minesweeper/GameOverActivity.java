package fail.minesweeper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import AppLogic.GameLogic.GameConfig;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over2);
        LinearLayout myMainLayout = new LinearLayout(this);
        myMainLayout.setOrientation(LinearLayout.VERTICAL);
        this.addContentView(myMainLayout,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        TextView title = new TextView(this);
        title.setTextColor(Color.parseColor("#000000"));
        title.setTextSize(28);
        title.setText("Game Over!");
        title.setGravity(Gravity.CENTER);
        myMainLayout.addView(title);

        Button tryAgainButton = new Button(this);
        tryAgainButton.setText("Try Again?");
        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent  = new Intent();
                setResult(GameConfig.TRY_AGAIN_CODE, resultIntent);
                finish();
            }
        });
        myMainLayout.addView(tryAgainButton);
        Button newGameButton = new Button(this);
        newGameButton.setText("New Game?");
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent  = new Intent();
                setResult(GameConfig.NEW_GAME_CODE, resultIntent);
                finish();
            }
        });
        myMainLayout.addView(newGameButton);

    }
}
