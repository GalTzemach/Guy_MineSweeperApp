package fail.minesweeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import AppLogic.GameLogic.GameConfig;
import AppLogic.RecordsLogic.GameRecord;
import AppLogic.RecordsLogic.RecordController;

public  class GameWonActivity extends AppCompatActivity {
    private RecordController recCon;
    private String playerName;

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

        recCon = new RecordController(this);
        Button tryAgainButton = new Button(this);
        final EditText nameField = new EditText(this);
        Button setButton = new Button(this);

        tryAgainButton.setText("New Game?");
        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent  = new Intent();
                setResult(GameConfig.NEW_GAME_CODE, resultIntent);
                finish();
            }
        });
        setButton.setText("BOOM");

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerName = nameField.getText().toString();
                Log.v("orel", "Try to set new Record with the name: "+playerName);
                checkRecordInStorage();
                finish();
            }
        });


        myMainLayout.addView(tryAgainButton);
        myMainLayout.addView(nameField);
        myMainLayout.addView(setButton);

        //checkRecord();
        //checkRecordInStorage();

    }

    public void checkRecord(){
        int level  = getIntent().getIntExtra("level" , 0); // should be -1
        SharedPreferences gameSettings = getSharedPreferences(GameConfig.PREFERENCE_NAME,0);
        int currentRecord = gameSettings.getInt(GameConfig.LEVEL_RECORDS[level],0);
        int newRecord = getIntent().getIntExtra("totalTime",20);
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

    public void checkRecordInStorage(){
        int level  = getIntent().getIntExtra("level" , 0); // should be -1
        int newRecord = getIntent().getIntExtra("totalTime" , 0);
        Log.v("orel", "GameWonActivity check record : "+ newRecord+ "level:"+ GameConfig.LEVEL_RECORDS[level]);
        //if(currentRecord > 0) {
        //    if (currentRecord > newRecord)
        //        setRecord(newRecord , level);
        //    Log.v("orel", "No Change currentRecord < newRecord"+ " level: "+ GameConfig.LEVEL_RECORDS[level]);
        //}else setRecord(newRecord , level);
        setRecordToStorage(newRecord , level);
    }

    public void setRecordToStorage(int newRecord , int level){
        Log.v("orel", "send new Record to the Record Controller ");
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude  = location.getLatitude();
        recCon.addRecord(new GameRecord(0 , newRecord , level , playerName, longitude, latitude));
    }

}
