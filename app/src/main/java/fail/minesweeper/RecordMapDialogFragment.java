package fail.minesweeper;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import AppLogic.GameLogic.GameConfig;
import AppLogic.RecordsLogic.GameRecord;

import static android.app.AlertDialog.*;

/**
 * Created by Guy on 08/01/2017.
 */

public class RecordMapDialogFragment extends DialogFragment {
    private GameRecord gameRecord;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(gameRecord.getName());
        TextView tv = new TextView(getActivity());
        tv.setText("Level: "+ GameConfig.LEVEL_LABELS[gameRecord.getLevel()]+ "\nTime: "+gameRecord.recordTimeToString());
        tv.setPadding(60,0,0,0);
        builder.setView(tv);
        return builder.create();
    }

    public static RecordMapDialogFragment newInstance() {
        Bundle args = new Bundle();
        RecordMapDialogFragment fragment = new RecordMapDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public void setGameRecord(GameRecord gameRecord){
        this.gameRecord = gameRecord;
    }

    public GameRecord getGameRecord() {
        return gameRecord;
    }
}
