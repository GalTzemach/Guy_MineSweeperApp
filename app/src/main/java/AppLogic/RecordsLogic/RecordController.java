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

	public RecordController(Context context){
		stController = new StorageController(context);
	}

	public void addRecord(GameRecord newGameRecord){
		stController.addNewRecord(newGameRecord.getName(),
				newGameRecord.getRecordTime(),
				newGameRecord.getLevel(),
				newGameRecord.getLongitude(),
				newGameRecord.getLatitude());
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

