package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class NotesDbManager {
    private Context context;
    private NotesDbHelper notesDbHelper;

    private ContentValues contentValues;

    public NotesDbManager(Context context1) {
        this.context = context1;
        notesDbHelper = new NotesDbHelper(context);
    }

    public Cursor getCursor() {
        return notesDbHelper.getData();
    }

    public boolean insertNote(String noteTitle, String noteContent, long timestamp) {
        contentValues = new ContentValues();
        contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, noteTitle);
        contentValues.put(NotesContract.NotesEntry.COLUMN_CONTENT, noteContent);
        contentValues.put(NotesContract.NotesEntry.COLUMN_TIME, timestamp);
        return notesDbHelper.insertData(NotesContract.NotesEntry.TABLE_NAME, contentValues);
    }

    public Cursor getSingleNote(String title) {
        return notesDbHelper.getNote(title);
    }

    public int updateNote(String id, String noteTitle, String noteContent) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, noteTitle);
        contentValues.put(NotesContract.NotesEntry.COLUMN_CONTENT, noteContent);
        return notesDbHelper.updateData(NotesContract.NotesEntry.TABLE_NAME, contentValues, id);
    }
}
