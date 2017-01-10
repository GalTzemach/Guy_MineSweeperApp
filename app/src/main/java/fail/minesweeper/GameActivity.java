package fail.minesweeper;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import AppLogic.GameExceptions.InvalidCellException;
import AppLogic.GameExceptions.NoMoreFlagsException;
import AppLogic.GameLogic.AccelerometerListener;
import AppLogic.GameLogic.GameConfig;
import AppLogic.GameLogic.GameController;
import AppLogic.GameLogic.GameEvent;
import AppLogic.GameLogic.GameListener;

public class GameActivity extends AppCompatActivity implements GameListener, AccelerometerListener{
    private FlagsLeftTextView flagsText;
    private GameController gameCon;
    private GridView gameMatrix;
    private ThreadTimer threadTimer;
    private TimerTextView timerTextView;
    private ServiceConnection mConnection;
    private boolean serviceConnected = false;
    private boolean isWarned = false;
    private int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        SharedPreferences gameSettings = getSharedPreferences(GameConfig.PREFERENCE_NAME,0);
        level = gameSettings.getInt(GameConfig.LEVEL_PREF,GameConfig.BEGINNER_LEVEL);
        //Bundle info = getIntent().getExtras();
        //level = info.getInt("level");
        gameCon = new GameController(level);
        gameCon.addGameListener(this);

        LinearLayout myMainLayout = new LinearLayout(this);
        myMainLayout.setOrientation(LinearLayout.VERTICAL);
        this.addContentView(myMainLayout,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        // Defining the RelativeLayout layout parameters.
        // In this case I want to fill its parent
        TextView title = new TextView(this);
        title.setTextColor(Color.parseColor("#000000"));
        title.setTextSize(28);
        title.setText(GameConfig.LEVEL_LABELS[this.level]+" level");
        title.setGravity(Gravity.CENTER);
        myMainLayout.addView(title);

        gameMatrix = new GridView(this);
        gameMatrix.setNumColumns(gameCon.getcols());
        gameMatrix.setAdapter(new GameAdapter(getApplicationContext(),gameCon));
        LinearLayout.LayoutParams gameMatrixLLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        gameMatrixLLP.setMargins(0,280,0,0);
        gameMatrix.setLayoutParams(gameMatrixLLP);

        gameMatrix.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gameCon.revealCell(position/gameCon.getcols(),position%gameCon.getcols());
                ((GameAdapter)gameMatrix.getAdapter()).notifyDataSetChanged();
            }
        });
        gameMatrix.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    gameCon.markFlag(position/gameCon.getcols(),position%gameCon.getcols());
                } catch (NoMoreFlagsException e) {
                    e.printStackTrace();
                } catch (InvalidCellException e) {
                    e.printStackTrace();
                }
                ((GameAdapter)gameMatrix.getAdapter()).notifyDataSetChanged();
                return true;
            }
        });

        this.flagsText = new FlagsLeftTextView(this,gameCon.getNumberOfFlags());
        gameCon.addFlagsListener(this.flagsText);
        myMainLayout.addView(gameMatrix);
        myMainLayout.addView(this.flagsText);

        timerTextView = new TimerTextView(this);
        myMainLayout.addView(timerTextView);
        threadTimer = new ThreadTimer(timerTextView);
        threadTimer.start();

        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                serviceConnected = true;
                ((AccelerometerService.SensorServiceBinder)service).getService().setListener(GameActivity.this);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                serviceConnected = false;
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!serviceConnected) {
            Intent intent = new Intent(this, AccelerometerService.class);
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GameConfig.REQUEST_CODE) {
            switch (resultCode) {
                case (GameConfig.TRY_AGAIN_CODE): {
                    this.gameCon.tryAgain();
                    this.flagsText.setFlags(gameCon.getNumberOfFlags());
                    ((GameAdapter)gameMatrix.getAdapter()).notifyDataSetChanged();
                    timerTextView.updateSec(0);
                    threadTimer = new ThreadTimer(timerTextView);
                    threadTimer.start();
                }
                break;
                case (GameConfig.NEW_GAME_CODE): {
                    this.gameCon.newGame();
                    this.flagsText.setFlags(gameCon.getNumberOfFlags());
                    ((GameAdapter)gameMatrix.getAdapter()).notifyDataSetChanged();
                    timerTextView.updateSec(0);
                    threadTimer = new ThreadTimer(timerTextView);
                    threadTimer.start();
                }
                break;
            }
        }
    }
    @Override
    public void winGame(GameEvent e) {
        threadTimer.terminate();
        unbindService(mConnection);
        serviceConnected = false;
        Intent intent = new Intent(this, GameWonActivity.class);
        intent.putExtra("totalTime",gameCon.getTotalTime());
        intent.putExtra("level",gameCon.getLevel());
        Log.v("guy","time:"+gameCon.getTotalTime());
        startActivityForResult(intent,GameConfig.REQUEST_CODE);

    }
    @Override
    public void gameOver(GameEvent e){
        threadTimer.terminate();
        unbindService(mConnection);
        serviceConnected = false;
        final AnimatorSet animatorSet = new AnimatorSet();
        int count = gameMatrix.getChildCount();
        ValueAnimator[] positionAnimatorArr = new ValueAnimator[count];

        for (int i = 0; i < count; i++) {
            final View childAt = gameMatrix.getChildAt(i);
            positionAnimatorArr[i] = ValueAnimator.ofFloat(0, 500);
            positionAnimatorArr[i].addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    childAt.setY(childAt.getY()+value);
                }
            });
            ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(childAt, "rotation", 0, 180f);
            animatorSet.play(positionAnimatorArr[i]).with(rotationAnimator);
        }
        animatorSet.setDuration(2000);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                Intent intent = new Intent(GameActivity.this, GameOverActivity.class);
                startActivityForResult(intent,GameConfig.REQUEST_CODE);
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animatorSet.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences gameSettings = getSharedPreferences(GameConfig.PREFERENCE_NAME,0);
        level = gameSettings.getInt(GameConfig.LEVEL_PREF,GameConfig.BEGINNER_LEVEL);
        if(!serviceConnected) {
            Intent intent = new Intent(this, AccelerometerService.class);
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    public void accelerometerChanged() {
        if(!isWarned) {
            Toast.makeText(this, "Wooops, you are moving your phone too much!", Toast.LENGTH_SHORT).show();
            isWarned = true;
        }else{
            Toast.makeText(this, "Start adding bombs!", Toast.LENGTH_SHORT).show();
            gameCon.addBombWhilePlaying();
            ((GameAdapter)gameMatrix.getAdapter()).notifyDataSetChanged();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(serviceConnected){
            unbindService(mConnection);
            serviceConnected = false;
        }
    }
}
