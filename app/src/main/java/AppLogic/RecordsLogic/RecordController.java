package AppLogic.RecordsLogic;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import AppLogic.GameLogic.GameConfig;
import AppLogic.StorageLogic.StorageController;


public  class RecordController{
	private StorageController stController;
	private ArrayList<GameRecord> beginnerRecordArray;
	private ArrayList<GameRecord> advencedRecordArray;
	private ArrayList<GameRecord> expertRecordArray;


	public RecordController(Context context){
        stController = new StorageController(context);
        beginnerRecordArray = new ArrayList<>();
        advencedRecordArray = new ArrayList<>();
        expertRecordArray   = new ArrayList<>();
	}

	public void addRecord(GameRecord newGameRecord){
		stController.addNewRecord(newGameRecord.getName(),
				newGameRecord.getRecordTime(),
				newGameRecord.getLevel(),
				newGameRecord.getLongitude(),
				newGameRecord.getLatitude());
		switch (newGameRecord.getLevel()){
			case GameConfig.BEGINNER_LEVEL:
				beginnerRecordArray.add(newGameRecord);
				break;
			case GameConfig.ADVANCED_LEVEL:
				advencedRecordArray.add(newGameRecord);
				break;
			case GameConfig.EXPERT_LEVEL:
				expertRecordArray.add(newGameRecord);
				break;
		}
	}


	public ArrayList<GameRecord> getRecordsArray(int level) {
		switch (level) {
			case GameConfig.BEGINNER_LEVEL:
				if(beginnerRecordArray.size() == 0)
                    this.beginnerRecordArray = stController.getRecords(GameConfig.BEGINNER_LEVEL);
                return this.beginnerRecordArray;
			case GameConfig.ADVANCED_LEVEL:
				if(advencedRecordArray.size() == 0)
                    this.advencedRecordArray = stController.getRecords(GameConfig.ADVANCED_LEVEL);
				return this.advencedRecordArray;
			case GameConfig.EXPERT_LEVEL:
				if(expertRecordArray.size() == 0)
                    this.expertRecordArray = stController.getRecords(GameConfig.EXPERT_LEVEL);
                return this.expertRecordArray;
			default:
				return null;

		}
	}
}

