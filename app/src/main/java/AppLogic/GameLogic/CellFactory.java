package AppLogic.GameLogic;
public final class CellFactory{
	
	public CellFactory() {
		
	}
	
	/**
	 * Creating new Cell
	 * @param row 	The row in the matrix of the Cell
	 * @param col 	The column in the matrix of the Cell
	 * @param type 	0 - for NumberCell, 1 - BombCell
	 * @return
	 */
	public static Cell createNewCell(int row,int col,int type) {
		Cell newCell = null;
		switch (type) {
			case 0: {
				newCell = new NumberCell(row, col);
			}
			break;
			case 1: {
				newCell = new BombCell(row, col);
			}
			break;
		}
        return newCell;
	}
	
	/**
	 * Changing Cell from NumberCell to BombCell or otherwise
	 * @param c The Cell to change
	 * @param isBomb true  -> Change NumberCell to BombCell, 
	 * 				 false -> Change BombCell to NumberCell (with value 0)
	 * @return New Cell 
	 */
	public static Cell changeCell(Cell c, boolean isBomb){
		Cell newCell = null;
		
		if(!isBomb){
			newCell = new NumberCell(c.getRow(), c.getCol());
			newCell.setNeighbors(c.getNeighbors());
		}else{
			newCell = new BombCell(c.getRow(), c.getCol());
			newCell.setNeighbors(c.getNeighbors());
		}
		
		return newCell;
	}
}
