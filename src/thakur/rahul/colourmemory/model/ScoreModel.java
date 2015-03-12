
package thakur.rahul.colourmemory.model;

import java.util.ArrayList;
import java.util.List;

import thakur.rahul.colourmemory.MainActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Handles all transaction to and from High Score Database.
 *
 * @author rahulthakur
 */
public final class ScoreModel {

	private static volatile Integer currentMinimumScoreInDB;

	private ScoreModel() {

	}

	/**
	 * Returns List of all rows present in the database.
	 */
	public static List<ScorePojo> getScoresList() {

		boolean closeOnExit = true;
		if (DatabaseHandler.getHandler().getWritableDatabase().isOpen())
			closeOnExit = false;
		SQLiteDatabase db = DatabaseHandler.getHandler().getWritableDatabase();
		List<ScorePojo> scoresList = new ArrayList<ScorePojo>();
		Cursor cursor = db.rawQuery(DatabaseHandler.QUERY_SELECT_ALL, null);
		if (cursor.moveToFirst())
			do {
				ScorePojo scoreObject = new ScorePojo();
				scoreObject.setName(cursor.getString(1));
				int score = cursor.getInt(2);
				if (currentMinimumScoreInDB == null || score < currentMinimumScoreInDB.intValue())
					currentMinimumScoreInDB = score;
				scoreObject.setScore(score);
				scoresList.add(scoreObject);
			} while (cursor.moveToNext());
		if (closeOnExit)
			db.close();
		return scoresList;
	}

	/**
	 * Returns current lowest high score present in the database.
	 */
	public static int getCurrentMinimumScoreInDB() {

		if (currentMinimumScoreInDB == null)
			getScoresList();
		return currentMinimumScoreInDB.intValue();
	}

	/**
	 * Adds new high score to the database.
	 *
	 * @return <b>true</b> when successful, <b>false</b> when unsuccessful
	 */
	public static boolean addScore(String name, int score) {

		SQLiteDatabase db = DatabaseHandler.getHandler().getWritableDatabase();
		boolean isSuccessful = false;
		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.KEY_NAME, name);
		values.put(DatabaseHandler.KEY_SCORE, score);
		if (db.insert(DatabaseHandler.TABLE_NAME, null, values) != -1)
			if (getScoresList().size() > 10) {
				if (db.rawQuery(DatabaseHandler.QUERY_UPDATE_TABLE, null).moveToFirst())
					isSuccessful = true;
			} else
				isSuccessful = true;
		db.close();
		return isSuccessful;
	}

	/**
	 * Creates and maintains High Score Database.
	 *
	 * @author rahulthakur
	 */
	private static final class DatabaseHandler extends SQLiteOpenHelper {

		private static final int DATABASE_VERSION = 1;
		private static final String DATABASE_NAME = "colour_memory";
		private static final String TABLE_NAME = "score_manager";
		private static final String KEY_ID = "_id";
		private static final String KEY_NAME = "name";
		private static final String KEY_SCORE = "score";
		private static final String QUERY_CREATE_TABLE = "CREATE TABLE "
		                                                 + TABLE_NAME
		                                                 + "("
		                                                 + KEY_ID
		                                                 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
		                                                 + KEY_NAME
		                                                 + " VARCHAR(15),"
		                                                 + KEY_SCORE
		                                                 + " INTEGER)";
		private static final String QUERY_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
		private static final String QUERY_UPDATE_TABLE = "DELETE FROM "
		                                                 + TABLE_NAME
		                                                 + " WHERE "
		                                                 + KEY_ID
		                                                 + " NOT IN(SELECT "
		                                                 + KEY_ID
		                                                 + " FROM "
		                                                 + TABLE_NAME
		                                                 + " ORDER BY "
		                                                 + KEY_SCORE
		                                                 + " DESC, "
		                                                 + KEY_ID
		                                                 + " DESC LIMIT 10)";
		private static final String QUERY_SELECT_ALL = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " + KEY_SCORE + " DESC, " + KEY_ID + " DESC";
		private static volatile DatabaseHandler UNIQUE_INSTANCE;

		/**
		 * Returns DatabaseHandler Singleton.
		 */
		public static DatabaseHandler getHandler() {

			if (UNIQUE_INSTANCE == null)
				synchronized (DatabaseHandler.class) {
					if (UNIQUE_INSTANCE == null)
						UNIQUE_INSTANCE = new DatabaseHandler();
				}
			return UNIQUE_INSTANCE;
		}

		private DatabaseHandler() {

			super(MainActivity.getAppContext(), DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL(QUERY_CREATE_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			db.execSQL(QUERY_DROP_TABLE);
			onCreate(db);
		}
	}
}
