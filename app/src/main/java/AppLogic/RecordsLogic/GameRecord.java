package AppLogic.RecordsLogic;
public class GameRecord {
	private int recordId;
	private int recordTime;
	private int level;
	private String name;
	
	public GameRecord(int recordId, int time, int level, String name) {
		this.recordId	= recordId;
		this.recordTime = time;
		this.level 		= level;
		this.name 		= name;
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

	
		

}
