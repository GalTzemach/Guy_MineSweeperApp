package fail.minesweeper;

import android.app.Activity;


import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import AppLogic.GameLogic.GameConfig;
import AppLogic.RecordsLogic.GameRecord;
import AppLogic.RecordsLogic.RecordController;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecordTableFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecordTableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordTableFragment extends Fragment {
    public static final String LEVEL_ARG = "level";
    private RelativeLayout mainLayout = null;
    private RecordController recCon;
    private TableLayout table;
    private Activity parent;
    private int currentLevel;

    private OnFragmentInteractionListener mListener;

    public RecordTableFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RecordTableFragment newInstance(int currentLevel) {
        RecordTableFragment fragment = new RecordTableFragment();
        Bundle args = new Bundle();
        args.putInt(LEVEL_ARG,currentLevel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.currentLevel = getArguments().getInt(LEVEL_ARG);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.parent = getActivity();
        if(this.mainLayout == null) {
            this.mainLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_record_table, container, false);
            mainLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            mainLayout.setGravity(Gravity.CENTER);
            this.table = new TableLayout(parent);
            mainLayout.addView(table);
            printTable(table , this.currentLevel);
        }

        return mainLayout;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void updateTable(int level) {
        this.currentLevel = level;
        table.removeAllViews();
        printTable(table , this.currentLevel);
    }

    public void setRecordController(RecordController recCon){
        this.recCon = recCon;
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
        TableRow tr = new TableRow(this.parent);
        TextView rankIndex = new TextView(this.parent);
        rankIndex.setTextColor(Color.parseColor("#000000"));
        rankIndex.setText(("#"+(Integer)rank).toString());
        rankIndex.setTextSize(28);
        rankIndex.setWidth(150);

        TextView nameField = new TextView(this.parent);
        nameField.setTextColor(Color.parseColor("#000000"));
        nameField.setText(record.getName());
        nameField.setTextSize(28);
        nameField.setWidth(600);

        TextView timeField = new TextView(this.parent);
        timeField.setTextColor(Color.parseColor("#000000"));
        timeField.setText(record.recordTimeToString());
        timeField.setTextSize(28);

        tr.addView(rankIndex);
        tr.addView(nameField);
        tr.addView(timeField);

        table.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
    }
}
