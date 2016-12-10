package AppLogic.GameLogic;

public class BombCell extends Cell{
	
	public BombCell(int row, int col) {
		super(row, col);
	}
	
	// Update neighbors array that new bomb was added
	// and valueUp() each of the neighbors cells value(number)
	public void updateNeighbors(){
		for(Cell c : super.getNeighbors()){
			// Delete the new BombCell (this) 
			// from the neighbors array of each neighbor
			// c.getNeighbors().remove(this);
			if(c instanceof NumberCell){
				((NumberCell) c).valueUp();
			}
		}
	}
	public String toString(){
		return "row:"+super.getRow()+" col:"+super.getCol()+" BOOOOMMMM!!!";
	}
}
