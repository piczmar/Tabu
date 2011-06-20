package pl.androservice.tabu.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TabuDbAdapter {

	// Database fields
	public static final String KEY_ROWID = "_id";
	public static final String KEY_WORD = "word";
	public static final String KEY_NOT_ALLOWED1 = "na_1";
	public static final String KEY_NOT_ALLOWED2 = "na_2";
	public static final String KEY_NOT_ALLOWED3 = "na_3";
	public static final String KEY_COUNT = "cnt";
	private static final String DATABASE_TABLE = "tabu";
	private Context context;
	private SQLiteDatabase database;
	private TabuDatabaseHelper dbHelper;

	public TabuDbAdapter(Context context) {
		this.context = context;
	}

	public TabuDbAdapter open() throws SQLException {
		dbHelper = new TabuDatabaseHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	/**
	 * Create a new tabu If the tabu is successfully created return the new
	 * rowId for that note, otherwise return a -1 to indicate failure.
	 */
	public long createTodo(String word, String na1, String na2, String na3) {
		ContentValues initialValues = createContentValues(word, na1, na2, na3);

		return database.insert(DATABASE_TABLE, null, initialValues);
	}

	/**
	 * Return a Cursor over the list of all todo in the database
	 * 
	 * @return Cursor over all notes
	 */
	public Cursor fetchAllTodos() {
		return database.query(DATABASE_TABLE, new String[] { KEY_ROWID, KEY_WORD, KEY_NOT_ALLOWED1, KEY_NOT_ALLOWED2, KEY_NOT_ALLOWED3 }, null, null, null, null, null);
	}

	/**
	 * Return a Cursor positioned at the defined todo
	 */
	public Cursor fetchTodo(long rowId) throws SQLException {
		Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] { KEY_ROWID, KEY_WORD, KEY_NOT_ALLOWED1, KEY_NOT_ALLOWED2, KEY_NOT_ALLOWED3 }, KEY_ROWID + "=" + rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	private ContentValues createContentValues(String word, String na1, String na2, String na3) {
		ContentValues values = new ContentValues();
		values.put(KEY_WORD, word);
		values.put(KEY_NOT_ALLOWED1, word);
		values.put(KEY_NOT_ALLOWED2, word);
		values.put(KEY_NOT_ALLOWED3, word);
		return values;
	}
	public void initDB(){
		createTodo("sowa", "ptak", "las", "mądry");
		createTodo("żyrafa", "zwierzęta", "szyja", "zoo");
	}
}
