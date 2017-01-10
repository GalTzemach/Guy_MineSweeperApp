package AppLogic.RecordsLogic;
public class GameRecord {
	private int recordId;
	private int recordTime;
	private int level;
	private String name;
	private double longitude;
	private double latitude;
	
	public GameRecord(int recordId, int time, int level, String name, double longitude, double latitude) {
		this.recordId	= recordId;
		this.recordTime = time;
		this.level 		= level;
		this.name 		= name;
		this.longitude  = longitude;
		this.latitude   = latitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public int getRecordId() {
		return recordId;
	}

	public int getLevel() {
		return level;
	}

	public int getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(int recordTime) {
		this.recordTime = recordTime;
	}

	public String getName() {
		return name;
	}

	public String recordTimeToString(){
		int timeMs = this.getRecordTime();
		if(timeMs == 0){
			return "No record";
		}
		String record;
		String minStr;
		String secStr;
		int totalSec = timeMs/1000;
		int min = totalSec/60;
		int sec = totalSec%60;
		if(min<10)  { minStr = "0"+Integer.toString(min);}
		else        { minStr = Integer.toString(min);    }
		if(sec<10)  { secStr = "0"+Integer.toString(sec);}
		else        { secStr = Integer.toString(sec);    }
		record =(""+minStr+":"+secStr+"");

		return record;
	}

	public String toString(){
		return "GameRecord: user:"+this.getName()+" Level:"+this.getLevel()+" time:"+ this.recordTimeToString();
	}

}
