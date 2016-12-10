package AppLogic.GameLogic;

public class NumberCell extends Cell{
	private int value;
	
	/**
	 * @param row 	The row in the matrix of the Cell
	 * @param col 	The column in the matrix of the Cell
	 */
	public NumberCell(int row, int col) {
		super(row,col);
		this.value = 0;
	}
	public void valueUp(){
		this.value++;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	/**
	 * Reseting the Cell </br>
	 * Value = 0, Not flagged and hidden.
	 * (Keeps the neighbors array)
	 */
	public void resetCell(){
		this.setValue(0);
		this.setFlagged(false);
		this.setHidden(true);
	}
	public String toString(){
		return "row:"+super.getRow()+" col:"+super.getCol()+" Value:"+this.value;
	}
}
