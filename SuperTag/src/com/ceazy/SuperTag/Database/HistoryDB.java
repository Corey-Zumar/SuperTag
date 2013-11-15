package com.ceazy.SuperTag.Database;

import java.util.ArrayList;
import java.util.List;

import com.ceazy.SuperTag.HistoryItem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HistoryDB {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_DATE = "date";
	public static final String KEY_HASHTAG = "hashtag";
	public static final String KEY_PKGNAME = "pkgname";
	public static final String KEY_HASHPHRASE = "hashphrase";
	public static final String KEY_ACTION = "maction";
	private static final String DATABASE_NAME = "PoundsDB";
	private static final String DATABASE_TABLE = "poundsTable";
	private static final int DATABASE_VERSION = 1;
	private database ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	private static class database extends SQLiteOpenHelper {

		public database(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
					KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					KEY_DATE + " TEXT NOT NULL, "	+
					KEY_HASHTAG + " TEXT NOT NULL, "  +
					KEY_HASHPHRASE + " TEXT NOT NULL, " +
					KEY_PKGNAME + " TEXT NOT NULL, " +
					KEY_ACTION + " TEXT NOT NULL); "

			);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}

	}

	public HistoryDB(Context c) {
		ourContext = c;

	}

	public HistoryDB open() throws SQLException {
		ourHelper = new database(ourContext);
		ourDatabase = ourHelper.getWritableDatabase(); 
		return this; 

	}

	public void close() {
		ourHelper.close();
	}

	public long createEntry(long date, String hashTag, String hashPhrase, String pkgName, String action) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_DATE, date);
		cv.put(KEY_HASHTAG, hashTag);
		cv.put(KEY_HASHPHRASE, hashPhrase);
		cv.put(KEY_PKGNAME, pkgName);
		cv.put(KEY_ACTION, action);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);

	}
	
	public List<HistoryItem> getHistoryList() {
		String[] columns = new String[]{KEY_DATE, KEY_HASHTAG, KEY_HASHPHRASE, 
				KEY_PKGNAME, KEY_ACTION};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		List<HistoryItem> itemsList = new ArrayList<HistoryItem>();
		if(c.moveToFirst()) {
		for(c.moveToLast(); !c.isBeforeFirst(); c.moveToPrevious()) {
			long date = c.getLong(0);
			String hashTag = c.getString(1);
			String hashPhrase = c.getString(2);
			String pkgName = c.getString(3);
			String action = c.getString(4);
			HistoryItem item = new HistoryItem(date, hashTag, hashPhrase, pkgName);
			item.setAction(action);
			
		}
		}
		return itemsList;
	}

	public void deleteEntry(long date) {
		ourDatabase.delete(DATABASE_TABLE, KEY_DATE + " IS "+date, null);
	}
	
	public void deleteAllEntries() {
		ourDatabase.delete(DATABASE_TABLE, KEY_DATE + " IS NOT NULL", null);
	}
}
