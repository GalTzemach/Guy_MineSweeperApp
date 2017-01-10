package AppLogic.GameLogic;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import AppLogic.GameExceptions.*;

public class GameController{
	public static Scanner s = new Scanner(System.in);
	private GameBoard 	game;
	private ArrayList<GameListener> gameListenerList;
	private int			level;
	private int			numOfFlags;
	private long 		startGameMs;	// when the game started ms
	private long 		endGameMs;		// when the game ended
	private boolean		finishedGame;

	/**
	 *
	 * @param level	The level of the game, please use GameConfig.java</br>
	 * {@link GameConfig.BEGINNER_LEVEL} - for beginner mod</br>
	 * {@link GameConfig.ADVENCED_LEVEL} - for advanced mod</br>
	 * {@link GameConfig.EXPERT_LEVEL}	 - for expert mod
	 * @see GameConfig
	 */
	public GameController(int level){
		this.level = level;
		this.gameListenerList = new ArrayList<>();
		this.finishedGame = false;
		this.startGameMs = takeTime();
		switch(level){
			case GameConfig.BEGINNER_LEVEL:
				this.game = new GameBoard(	GameConfig.BEGINNER_ROWS_COLS,
						GameConfig.BEGINNER_ROWS_COLS,
						GameConfig.BEGINNER_BOMBS);
				this.numOfFlags = GameConfig.BEGINNER_BOMBS;
				break;
			case GameConfig.ADVANCED_LEVEL:
				this.game = new GameBoard(	GameConfig.ADVENCED_ROWS_COLS,
						GameConfig.ADVENCED_ROWS_COLS,
						GameConfig.ADVENCED_BOMBS);
				this.numOfFlags = GameConfig.ADVENCED_BOMBS;
				break;
			case GameConfig.EXPERT_LEVEL:
				this.game = new GameBoard(	GameConfig.EXPERT_ROWS_COLS,
						GameConfig.EXPERT_ROWS_COLS,
						GameConfig.EXPERT_BOMBS);
				this.numOfFlags = GameConfig.EXPERT_BOMBS;
				break;
		}
		if(game != null){
			game.initBoard();
			game.throwSomeBombs(game.getNumberOfBombs());
		}
	}
	/**
	 * Try again the same game
	 */
	public void tryAgain(){
		game.tryAgain();
		this.setFinishedGame(false);
		startGameMs = takeTime();
	}
	/**
	 * Creating a new game
	 */
	public void newGame(){
		game.restart();
		this.setFinishedGame(false);
		startGameMs = takeTime();
	}
	/**
	 * Reveal a specific Cell, go to GameBoard.revealCell() for more information
	 * @see GameBoard#revealCell
	 * @param row	The row of the Cell
	 * @param col	the columns of the Cell
	 * @throws InvalidCellException
	 * @throws GameOverException
	 */
	public void revealCell(int row, int col){
		try {
			game.revealCell(row, col);
		}catch(GameOverException e){
			this.endGameMs = takeTime();
			this.processGameOverEvent(new GameEvent(this,this.getTotalTime(),this.getLevel()));
		} catch (InvalidCellException e) {
			e.printStackTrace();
		}
		if(checkGame()){
			this.endGameMs = takeTime();
			this.processWinGameEvent(new GameEvent(this,this.getTotalTime(),this.getLevel()));
		}
	}
	/**
	 * Returns true if the game ended in victory
	 * @return
	 */
	public boolean checkGame(){
		return game.checkGame();
	}
	/**
	 * Mark a Cell as flagged
	 * @param row	The row of the Cell
	 * @param col	the columns of the Cell
	 * @throws NoMoreFlagsException
	 * @throws InvalidCellException
	 */
	public void markFlag(int row, int col) throws NoMoreFlagsException, InvalidCellException{
		game.markFlag(row,col);
	}
	private long takeTime(){
		return System.currentTimeMillis();
	}
	public int getTotalTime(){
		return (int)(this.endGameMs-this.startGameMs);
	}
	public int getLevel(){
		return this.level;
	}
	public boolean isFinishedGame() {
		return finishedGame;
	}
	public void setFinishedGame(boolean finishedGame) {
		this.finishedGame = finishedGame;
	}
	/**
	 * Adding a GameListener to the array of listeners
	 * @param gl implements GameListener
	 */
	public void addGameListener(GameListener gl){
		this.gameListenerList.add(gl);
	}
	/**
	 * Trigger {@link GameListener#winGame(GameEvent)} to all listeners
	 * @param e
	 */
	public void processWinGameEvent(GameEvent e){
		for(GameListener gl : this.gameListenerList){
			gl.winGame(e);
		}
	}
	public void addBombWhilePlaying(){
		game.addBombWhilePlaying();
	}
	/**
	 * Trigger {@link GameListener#gameOver(GameEvent)} to all listeners
	 * @param e
	 */
	public void processGameOverEvent(GameEvent e){
		for(GameListener gl : this.gameListenerList){
			gl.gameOver(e);
		}
	}
	public void addFlagsListener(FlagsListener flagsListener){
		game.addFlagsListener(flagsListener);
	}
	public int getRows(){
		return game.getRows();
	}
	public int getcols(){
		return game.getCols();
	}
	public int getValueAtPosition(int pos) {
		return this.game.getValueAtPosition(pos);
	}
	public boolean getIsHiddenAtPosition(int pos){
		return this.game.getIsHiddenAtPosition(pos);
	}
	public boolean getIsFlaggedAtPosition(int pos){
		return this.game.getIsFlaggedAtPosition(pos);
	}
	public int getNumberOfFlags(){
		return this.numOfFlags;
	}
	public void play() throws NoMoreFlagsException, InvalidCellException, GameOverException{
		this.startGameMs = takeTime();
		while(!finishedGame) {
			System.out.println(game.toString());
			int action;
			System.out.println("Select Action: 0-Reveal 1-Flag");
			action = s.nextInt();
			switch(action){
				case 0:{
					//reveal
					System.out.println("Reveal Cell:\nplz enter rows and cols");
					int row = s.nextInt();
					int col = s.nextInt();
					this.revealCell(row, col);
					if(this.checkGame()){
						System.out.println("We have a winner!!!");
						this.endGameMs = takeTime();
						int total = (int)(this.endGameMs-this.startGameMs);
						System.out.format("Total Time: %d sec, %d ms\n",(total/1000),(total%1000));
						GameEvent e = new GameEvent(this, total, this.getLevel());
						this.processWinGameEvent(e);
						this.finishedGame = true;
					}
				}break;
				case 1:{
					//flag
					System.out.println("Mark Flag: \nplz enter rows and cols");
					int row = s.nextInt();
					int col = s.nextInt();
					this.markFlag(row,col);
				}break;
				case 2:{
					System.out.println(game.developerPrint());
				}break;
				case 3:{
					this.startGameMs = takeTime();
					this.tryAgain();
				}break;
				case 4:{
					this.startGameMs = takeTime();
					this.newGame();
				}break;
			}
		}
	}

}
