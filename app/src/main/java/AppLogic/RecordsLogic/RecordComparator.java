package AppLogic.RecordsLogic;

import java.util.Comparator;

public class RecordComparator implements Comparator<GameRecord>{

	public int compare(GameRecord newRecord , GameRecord oldRecord ) {
		if(newRecord == oldRecord)
			return 0;
		if(newRecord.getRecordTime() > oldRecord.getRecordTime())
			return 1;
		else
			return -1;
	}


}
