package AppLogic.RecordsLogic;

import java.util.Scanner;
import java.util.TreeSet;

import AppLogic.GameLogic.GameConfig;
import AppLogic.GameLogic.GameEvent;
import AppLogic.GameLogic.GameListener;

public class RecordController implements GameListener{
	static Scanner s  = new Scanner(System.in);
	//public final int NUM_OF_RECORDS = 3;
	private TreeSet<GameRecord> beginner;
	private TreeSet<GameRecord> advenced;
	private TreeSet<GameRecord> expert;
	
	public RecordController(){
		this.beginner = new TreeSet<GameRecord>(new RecordComparator());
		this.advenced = new TreeSet<GameRecord>(new RecordComparator());
		this.expert = new TreeSet<GameRecord>(new RecordComparator());
	}
	
	public void addRecord(GameRecord newGameRecord){
		switch (newGameRecord.getLevel()){
			case GameConfig.BEGINNER_LEVEL :
				beginner.add(newGameRecord);
				break;	
			
			case GameConfig.ADVANCED_LEVEL :
				advenced.add(newGameRecord);
				break;
			
			case GameConfig.EXPERT_LEVEL :
				expert.add(newGameRecord);
				break;
			
		}		
	}

	public void createNewRecord(long time , int level , String name){
		addRecord(new GameRecord(time, level, name));
	}
	@Override
	public void winGame (GameEvent e){
		String name;
		System.out.println("Please enter your name:");
		name = s.nextLine();
		if(name.length() > 0){
			this.createNewRecord(e.getTime(), e.getLevel(), name);
		}
	}
	@Override
	public void gameOver(GameEvent e) {
		// TODO Auto-generated method stub
		return;
	}
	public String toString(int level){
		switch (level){
			case GameConfig.BEGINNER_LEVEL :{
				return beginner.toString();
			}
			case GameConfig.ADVANCED_LEVEL :{
				return advenced.toString();
			}
			case GameConfig.EXPERT_LEVEL :{
				return expert.toString();
			}
		}
		return "";
	}
}
