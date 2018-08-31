package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NotesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notes.db";

    private static final int DATABASE_VERSION = 1;

    NotesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_NOTES_TABLE =
                "CREATE TABLE " + NotesContract.NotesEntry.TABLE_NAME + " (" +
                        NotesContract.NotesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        NotesContract.NotesEntry.COLUMN_TIME + " TIMESTAMP, " +
                        NotesContract.NotesEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                        NotesContract.NotesEntry.COLUMN_CONTENT + " TEXT);";

        sqLiteDatabase.execSQL(SQL_CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NotesContract.NotesEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String table, ContentValues contentValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        long result = database.insert(table, null, contentValues);

        return result != -1;
    }

    public int updateData(String table, ContentValues contentValues, String id) {
        SQLiteDatabase database = this.getWritableDatabase();
        int res = database.update(table, contentValues,
                NotesContract.NotesEntry._ID + " = ?",
                new String[]{id});
        Log.d(NotesDbHelper.class.getSimpleName(), String.valueOf(res));
        return res;
    }

    public Cursor getData() {
        SQLiteDatabase database = this.getReadableDatabase();

        return database.query(
                NotesContract.NotesEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                NotesContract.NotesEntry.COLUMN_TIME
        );
    }

    public Cursor getNote(String title) {
        SQLiteDatabase database = this.getReadableDatabase();

        return database.query(
                NotesContract.NotesEntry.TABLE_NAME,
                new String[]{NotesContract.NotesEntry.COLUMN_TITLE,
                        NotesContract.NotesEntry.COLUMN_CONTENT},
                NotesContract.NotesEntry._ID + " = ? ",
                new String[]{title},
                null,
                null,
                null
        );
    }

    public boolean deleteData() {
        return false;
    }
}
