package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notes.db";

    private static final int DATABASE_VERSION = 4;

    public NotesDbHelper(Context context) {
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

    public int updateData(String table, ContentValues contentValues, String title) {
        SQLiteDatabase database = this.getWritableDatabase();
        return database.update(table, contentValues,
                NotesContract.NotesEntry.COLUMN_TITLE + " = ",
                new String[]{title});
    }

    public Cursor getData() {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor notesCursor = database.query(
                NotesContract.NotesEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (notesCursor != null && notesCursor.moveToFirst()) {
            return notesCursor;
        }

        return null;
    }

    public boolean deleteData() {
        return false;
    }
}
