package AppLogic.GameLogic;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import AppLogic.GameExceptions.*;

public class GameBoard {
	private int numberOfBombs;
	private int numberOfFlags;
	private int numberOfHiddenCells;
	private Cell[][] board;
	private int rows;
	private int cols;
	private FlagsListener flagsListener;
	/**
	 * Please use the static configurations at GameConfig.java
	 * @param rows				The amount of rows for the game
	 * @param cols				The amount of columns for the game
	 * @param numberOfBombs		The amount of bombs for the game
	 * @see GameConfig
	 */
	public GameBoard(int rows, int cols, int numberOfBombs) {
		this.rows 				 = rows;
		this.cols 				 = cols;
		this.numberOfBombs 		 = numberOfBombs;
		this.numberOfFlags 		 = numberOfBombs;
		this.board 				 = new Cell[rows][cols];
		this.numberOfHiddenCells = rows*cols;
	}
	/**
	 *	Initial Board matrix with new NumberCell objects
	 *	@see CellFactory
	 */
	public void initBoard(){
		// Fill matrix with new NumberCell
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				this.board[i][j] = (Cell)CellFactory.createNewCell(i, j, GameConfig.NUMBER_CELL);
			}
		}
		createCellsNeighbors();
	}
	private void createCellsNeighbors(){
		// Update neighbors for each cell:
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				//each cell add the 8 cells around it.
				for (int dRow = -1; dRow <= 1; dRow++) {
					for (int dCol = -1; dCol <= 1; dCol++) {
						if(dRow==dCol && dCol == 0)//don't need to add itself
							continue;
						if(this.board[i][j] instanceof BombCell)
							continue;
						updateNeighbor(this.board[i][j], dRow, dCol);
					}
				}
			}
		}
	}
	private void updateNeighbor(Cell c, int dx, int dy){
		if(c.getCol()+dx > cols-1 || c.getCol()+dx < 0)
			return;
		if(c.getRow()+dy > rows-1 || c.getRow()+dy < 0)
			return;
		Cell temp = board[c.getRow()+dy][c.getCol()+dx];
		c.addNeighbor(temp);
	}
	/**
	 *	Adding BombCell objects to the game matrix, by changing NumberCell to BombCell objects numberOfBombs times.
	 *	Each new BombCell, created by CellFactory, using the BombCell.updateNeighbors() to increase the value of each 
	 *	NumberCell who's a neighbor by 1
	 *	@see CellFactory
	 *	@see BombCell
	 *	@see NumberCell
	 */
	public void throwSomeBombs(){
		int x,y;
		for(int i=0; i<numberOfBombs; i++){
			do{
				y = ThreadLocalRandom.current().nextInt(0, cols);
				x = ThreadLocalRandom.current().nextInt(0, rows);
			}while(board[x][y] instanceof BombCell);
			board[x][y] = (Cell)CellFactory.changeCell(board[x][y], true);
			((BombCell) board[x][y]).updateNeighbors();
		}
	}
	/**
	 * Reveal a Cell change the Cell.isHidden attribute to true </br>
	 * First this method checks if the Cell's index is valid, if not throws InvalidCellException</br>
	 * Then using the Cell.reveal() method
	 * @see Cell#reveal()
	 * @param row	The row of the Cell
	 * @param col	the columns of the Cell
	 * @throws InvalidCellException
	 * @throws GameOverException
	 */
	public void revealCell(int row , int col) throws InvalidCellException, GameOverException{
		int count;
		checkVaildRowCol(row,col);
		count = board[row][col].reveal();
		this.numberOfHiddenCells -= count;
	}
	// Mark flag in the map
	public void markFlag(int row, int col) throws NoMoreFlagsException, InvalidCellException {
		checkVaildRowCol(row,col);
		if(this.numberOfFlags>-1){
				if(board[row][col].isFlagged()){
					board[row][col].setFlagged(false);
					this.numberOfFlags++;
					flagsListener.flagUnmarked();
				}else{
					if(board[row][col].isHidden() && this.numberOfFlags > 0){
						board[row][col].setFlagged(true);
						this.numberOfFlags--;
						flagsListener.flagMarked();
					}else{
						throw new NoMoreFlagsException();
					}
				}
		}else{
			throw new NoMoreFlagsException();
		}
	}
	/**
	 * Returns true if the game ended in victory
	 * @return
	 */
	public boolean checkGame(){
		if(this.numberOfHiddenCells == this.numberOfBombs)
			return true;
		return false;
	}
	/**
	 * Creating a new game
	 */
	public void restart(){
		for(Cell[] row: this.getBoard()){
			for(Cell c: row){
				if(c instanceof NumberCell){
					((NumberCell) c).resetCell();
					c.setNeighbors(new ArrayList<Cell>());
				}
				if(c instanceof BombCell){
					board[c.getRow()][c.getCol()] = CellFactory.changeCell(c, false);
					board[c.getRow()][c.getCol()].setNeighbors(new ArrayList<Cell>());
				}
			}
		}
		this.createCellsNeighbors();
		this.throwSomeBombs();
		this.setNumberOfFlags(this.getNumberOfBombs());
		this.setNumberOfHiddenCells(this.getRows()*this.getRows());
	}
	public Cell[][] getBoard(){
		return this.board;
	}
	/**
	 * Same game, try again
	 */
	public void tryAgain(){
		for(Cell[] row: this.getBoard()){
			for(Cell c: row){
				c.setFlagged(false);
				c.setHidden(true);
			}
		}
		this.setNumberOfFlags(this.getNumberOfBombs());
		this.setNumberOfHiddenCells(this.getRows()*this.getRows());
	}
	private void checkVaildRowCol(int row, int col) throws InvalidCellException{
		if((row > rows-1) || (row<0) || (col<0) || (col > cols)){
			throw new InvalidCellException();
		}
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getCols() {
		return cols;
	}
	public void setCols(int cols) {
		this.cols = cols;
	}
	public int getNumberOfBombs() {
		return numberOfBombs;
	}
	public void setNumberOfBombs(int numberOfBombs) {
		this.numberOfBombs = numberOfBombs;
	}
	public int getNumberOfFlags() {
		return numberOfFlags;
	}
	public void setNumberOfFlags(int numberOfFlags) {
		this.numberOfFlags = numberOfFlags;
	}
	public int getNumberOfHiddenCells() {
		return numberOfHiddenCells;
	}
	public void setNumberOfHiddenCells(int numberOfHiddenCells) {
		this.numberOfHiddenCells = numberOfHiddenCells;
	}
	public int getValueAtPosition(int pos){
        Cell c = board[pos/this.getCols()][pos%this.getCols()];
        if(c instanceof NumberCell) {
            return ((NumberCell) c).getValue();
        }else{
            return -1;
        }
    }
    public boolean getIsHiddenAtPosition(int pos){
        Cell c = board[pos/this.getCols()][pos%this.getCols()];
        return c.isHidden();
    }
    public boolean getIsFlaggedAtPosition(int pos){
        Cell c = board[pos/this.getCols()][pos%this.getCols()];
        return c.isFlagged();
    }
	/**
	 * For developer's use @return String with game solution
	 */
	public String developerPrint() {
		StringBuffer sb = new StringBuffer();
		sb.append("    0  1  2  3  4  5  6  7  8  9 \n");
		for (int i = 0; i < rows; i++) {
			sb.append(" "+(i)+" ");
			for (int j = 0; j < cols; j++) {
				if(board[i][j] instanceof NumberCell)
					sb.append("["+((NumberCell) board[i][j]).getValue()+"]");
				else
					sb.append("[*]");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("    0  1  2  3  4  5  6  7  8  9 \n");
		for (int i = 0; i < rows; i++) {
			sb.append(" " + (i) + " ");
			for (int j = 0; j < cols; j++) {
				if (board[i][j] instanceof NumberCell)
					if (board[i][j].isHidden)
						if (board[i][j].isFlagged())
							sb.append("[+]");
						else
							sb.append("[-]");
					else
						sb.append("[" + ((NumberCell) board[i][j]).getValue() + "]");
				else if (board[i][j].isFlagged())
					sb.append("[+]");
				else
					sb.append("[-]");
				//sb.append("[*]");
			}
			sb.append("\n");
		}
		sb.append("Number of hidden cells:" + this.numberOfHiddenCells + "\n");
		sb.append("Number of flags left:" + this.numberOfFlags + "\n");
		return sb.toString();
	}

	public void addFlagsListener(FlagsListener fl) {
		this.flagsListener = fl;
	}
	public void revealAll() {
		for(Cell[] row: this.getBoard()){
			for(Cell c: row){
				c.setHidden(false);
			}
		}
	}

}
