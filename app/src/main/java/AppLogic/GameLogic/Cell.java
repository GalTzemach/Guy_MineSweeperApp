package AppLogic.GameLogic;
import java.util.ArrayList;
import AppLogic.GameExceptions.*;

public abstract class Cell {
	private int row;
	private int col;
	protected boolean isHidden;			// true if the cell is hidden
	protected boolean isFlagged;		// true if the cell marked as flag
	private ArrayList<Cell> nighbors;	// Nearby cells array
	
	public Cell(int row, int col) {
		this.row 		= row;
		this.col 		= col;
		this.isHidden 	= true;
		this.isFlagged 	= false;
		this.nighbors 	= new ArrayList<>();
	}

	public ArrayList<Cell> getNeighbors() {
		return nighbors;
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

	public void setNeighbors(ArrayList<Cell> nighbors) {
		this.nighbors = nighbors;
	}

	// Add neighbor to the array
	public void addNeighbor(Cell c){
		this.getNeighbors().add(c);
	}

	/**
	 * Reveal area or a specific Cell in the Game Board matrix recursively
	 * @return The amount of revealed cells
	 * @throws GameOverException
	 */
	public int reveal() throws GameOverException{
		int temp = 0;
		if (this.isFlagged){
			return 0;
		}
		if (!this.isHidden){
			return 0;
		}
		if (this instanceof BombCell){
			throw new GameOverException();
		}
		if (this instanceof NumberCell){
			this.setHidden(false);
			temp++;
			if(((NumberCell)this).getValue() == 0)
				for(Cell neighbor : this.getNeighbors()){
					temp+= neighbor.reveal();
				}
			}else{
				return 1;
			}
		return temp;
	}

	public boolean isHidden() {
		return isHidden;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	public boolean isFlagged() {
		return isFlagged;
	}

	public void setFlagged(boolean isFlagged) {
		this.isFlagged = isFlagged;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Cell other = (Cell) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	

}
