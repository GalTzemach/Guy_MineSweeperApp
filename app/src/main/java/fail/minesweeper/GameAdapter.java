package fail.minesweeper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import AppLogic.GameLogic.GameController;

/**
 * Created by Guy on 29/11/2016.
 */
public class GameAdapter extends BaseAdapter {
    private GameController gameCont;
    private Context mContext;

    public GameAdapter(Context context, GameController gameCont){
        this.mContext = context;
        this.gameCont = gameCont;
    }
    @Override
    public int getCount() {
        return (gameCont.getcols()*gameCont.getRows());
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CellDisplay cellDisplay;
        cellDisplay = (CellDisplay)convertView;
        if(cellDisplay == null) {
            cellDisplay = new CellDisplay(mContext);
        }
        int value           = gameCont.getValueAtPosition(position);
        boolean isHidden    = gameCont.getIsHiddenAtPosition(position);
        boolean isFlagged   = gameCont.getIsFlaggedAtPosition(position);

        cellDisplay.setInfo(value,isHidden,isFlagged);

        return cellDisplay;
    }
}
