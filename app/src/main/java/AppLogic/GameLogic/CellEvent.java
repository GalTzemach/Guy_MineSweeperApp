package AppLogic.GameLogic;

/**
 * Created by Guy on 26/11/2016.
 */

public class CellEvent extends java.util.EventObject{
    private int row;
    private int col;
    private int value;
    private boolean isHidden;
    private boolean isFlagged;
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public CellEvent(Object source,int row,int col,int val,boolean isHidden,boolean isFlagged) {
        super(source);
        this.row = row;
        this.col = col;
        this.value = val;
        this.isFlagged = isFlagged;
        this.isHidden = isHidden;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }
}
