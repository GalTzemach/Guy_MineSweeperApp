package AppLogic.RecordsLogic;

import android.content.Context;
import java.util.ArrayList;
import AppLogic.GameLogic.GameConfig;
import AppLogic.StorageLogic.StorageController;


public  class RecordController{
	private StorageController stController;

	public RecordController(Context context){
		stController = new StorageController(context);
	}

	public void addRecord(GameRecord newGameRecord){
		stController.addNewRecord(newGameRecord.getName(),newGameRecord.getRecordTime(),newGameRecord.getLevel());
	}


	public ArrayList<GameRecord> getRecordsArray(int level) {
		switch (level) {
			case GameConfig.BEGINNER_LEVEL:
				return stController.getRecords(GameConfig.BEGINNER_LEVEL);

			case GameConfig.ADVANCED_LEVEL:
				return stController.getRecords(GameConfig.ADVANCED_LEVEL);

			case GameConfig.EXPERT_LEVEL:
				return stController.getRecords(GameConfig.EXPERT_LEVEL);

			default:
				return null;

		}
	}
}

