package AppLogic.RecordsLogic;
public class GameRecord {
	private int sec;
	private int mSec;
	private int level;
	private String name;
	
	public GameRecord(long time, int level, String name) {
		this.sec 	= (int)time/1000;
		this.mSec 	= (int)time%1000;
		this.level 	= level;
		this.name 	= name;
	}
	
	public void setRecord(int time, String newName){
		this.sec 	= time/1000;
		this.mSec 	= time%1000;
		this.name 	= newName;
	}
	
	
	
	public int getLevel() {
		return level;
	}
	
	public int getmSec() {
		return mSec;
	}
	
	public int getSec() {
		return sec;
	}

	public String getName() {
		return name;
	}

	public String toString(){
		return ("time: "+sec+":"+mSec+" name: "+name);
	}
	
		

}
