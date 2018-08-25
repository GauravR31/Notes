package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class NotesDbManager {
    private Context context;
    private NotesDbHelper notesDbHelper;

    private ContentValues contentValues;

    public NotesDbManager(Context context) {
        this.context = context;
        notesDbHelper = new NotesDbHelper(context);
    }

    public Cursor getCursor() {
        return notesDbHelper.getData();
    }

    public boolean insertNote(String noteTitle, String noteContent) {
        contentValues = new ContentValues();
        contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, noteTitle);
        contentValues.put(NotesContract.NotesEntry.COLUMN_CONTENT, noteContent);
        return notesDbHelper.insertData(NotesContract.NotesEntry.TABLE_NAME, contentValues);
    }
}
