package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class NotesDbManager {
    Context context;
    public NotesDbHelper notesDbHelper;

    String[] noteTitles = {"Note 1", "Note 2", "Note 3", "Note 4", "Note 5 "};
    String[] noteContents = {"Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            "Suspendisse ut sem laoreet risus elementum congue ac et urna.",
            "Mauris ac tellus in magna fringilla vestibulum.",
            "Aliquam semper purus non turpis elementum, eget fringilla eros ullamcorper.",
            "Morbi pretium urna et risus viverra fermentum."};

    ContentValues contentValues = new ContentValues();

    public NotesDbManager(Context context) {
        this.context = context;
        notesDbHelper = new NotesDbHelper(context);
    }

    public Cursor getCursor() {
        Cursor noteCursor = notesDbHelper.getData();
        if (noteCursor != null)
            return noteCursor;
        return null;
    }

    public void insertNote() {
        contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, noteTitles[0]);
        contentValues.put(NotesContract.NotesEntry.COLUMN_CONTENT, noteContents[0]);
        notesDbHelper.insertData(NotesContract.NotesEntry.TABLE_NAME, contentValues);

        contentValues = new ContentValues();
        contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, noteTitles[1]);
        contentValues.put(NotesContract.NotesEntry.COLUMN_CONTENT, noteContents[1]);
        notesDbHelper.insertData(NotesContract.NotesEntry.TABLE_NAME, contentValues);

        contentValues = new ContentValues();
        contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, noteTitles[2]);
        contentValues.put(NotesContract.NotesEntry.COLUMN_CONTENT, noteContents[2]);
        notesDbHelper.insertData(NotesContract.NotesEntry.TABLE_NAME, contentValues);

        contentValues = new ContentValues();
        contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, noteTitles[3]);
        contentValues.put(NotesContract.NotesEntry.COLUMN_CONTENT, noteContents[3]);
        notesDbHelper.insertData(NotesContract.NotesEntry.TABLE_NAME, contentValues);

        contentValues = new ContentValues();
        contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, noteTitles[4]);
        contentValues.put(NotesContract.NotesEntry.COLUMN_CONTENT, noteContents[4]);
        notesDbHelper.insertData(NotesContract.NotesEntry.TABLE_NAME, contentValues);
    }
}
