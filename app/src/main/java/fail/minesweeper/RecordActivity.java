package fail.minesweeper;

import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import AppLogic.GameLogic.GameConfig;
import AppLogic.RecordsLogic.RecordController;

public class RecordActivity extends AppCompatActivity {
    public static final int MAP_FRAGMENT = 1;
    public static final int TABLE_FRAGMENT = 0;

    private RecordController recCon;
    private RecordTableFragment tableViewFragment;
    private RecordMapFragment   mapFragment;
    private FragmentTransaction ft;
    private LinearLayout myMainLayout;
    private Button switchView;
    private int currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        recCon = new RecordController(this);

        this.myMainLayout = new LinearLayout(this);
        myMainLayout.setOrientation(LinearLayout.VERTICAL);
        myMainLayout.setId(R.id.recordActivityLL);

        this.addContentView(myMainLayout,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        LinearLayout levelSelectLayout = new LinearLayout(this);
        levelSelectLayout.setOrientation(LinearLayout.HORIZONTAL);
        levelSelectLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));

        TextView title = new TextView(this);
        title.setText("LEADERBOARD");
        title.setTextColor(Color.parseColor("#000000"));
        title.setTextSize(48);
        title.setGravity(Gravity.CENTER);
        myMainLayout.addView(title);

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

        switchView = new Button(this);
        switchView.setText(R.string.MapView);
        switchView.setTextColor(Color.parseColor("#a8342a"));
        switchView.setBackgroundColor(Color.WHITE);
        levelSelectLayout.addView(switchView);

        levelSelectLayout.setGravity(Gravity.CENTER);
        myMainLayout.addView(levelSelectLayout);

        this.tableViewFragment = RecordTableFragment.newInstance(GameConfig.BEGINNER_LEVEL);
        this.tableViewFragment.setRecordController(this.recCon);
        this.mapFragment = RecordMapFragment.newInstance(GameConfig.BEGINNER_LEVEL);
        this.mapFragment.setRecordController(this.recCon);

        this.ft = getSupportFragmentManager().beginTransaction();
        ft.add(myMainLayout.getId(), tableViewFragment).commit();
        this.currentFragment = TABLE_FRAGMENT;

        beginnerBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentFragment == TABLE_FRAGMENT)
                    tableViewFragment.updateTable(GameConfig.BEGINNER_LEVEL);
                else
                    mapFragment.updateMap(GameConfig.BEGINNER_LEVEL);
            }
        });
        advancedBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentFragment == TABLE_FRAGMENT)
                    tableViewFragment.updateTable(GameConfig.ADVANCED_LEVEL);
                else
                    mapFragment.updateMap(GameConfig.ADVANCED_LEVEL);
            }
        });
        expertBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentFragment == TABLE_FRAGMENT)
                    tableViewFragment.updateTable(GameConfig.EXPERT_LEVEL);
                else
                    mapFragment.updateMap(GameConfig.EXPERT_LEVEL);
            }
        });

        switchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(currentFragment){
                    case TABLE_FRAGMENT:{
                        switchView.setText("Table View");
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(myMainLayout.getId(), mapFragment).commit();
                        currentFragment = MAP_FRAGMENT;
                    }break;
                    case MAP_FRAGMENT:{
                        switchView.setText("Map View");
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(myMainLayout.getId(), tableViewFragment).commit();
                        currentFragment = TABLE_FRAGMENT;
                    }break;
                }

            }
        });
    }
}
