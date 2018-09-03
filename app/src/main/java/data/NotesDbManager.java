package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class NotesDbManager {
    private NotesDbHelper notesDbHelper;

    public NotesDbManager(Context context1) {
        notesDbHelper = new NotesDbHelper(context1);
    }

    public List<Note> getNoteList() {
        Cursor cursor = notesDbHelper.getData();
        List<Note> noteList = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                String noteId = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry._ID));
                String noteTitle = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_TITLE));
                String noteContent = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_CONTENT));

                Note note = new Note(noteId, noteTitle, noteContent);
                noteList.add(note);
            }
        }

        return noteList;
    }

    public boolean insertNote(String noteTitle, String noteContent, long timestamp) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, noteTitle);
        contentValues.put(NotesContract.NotesEntry.COLUMN_CONTENT, noteContent);
        contentValues.put(NotesContract.NotesEntry.COLUMN_TIME, timestamp);
        return notesDbHelper.insertData(NotesContract.NotesEntry.TABLE_NAME, contentValues);
    }

    public String[] getSingleNote(String title) {
        Cursor cursor = notesDbHelper.getNote(title);
        if (cursor != null && cursor.moveToFirst()) {
            String noteTitle = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_TITLE));
            String noteContent = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_CONTENT));

            return new String[]{noteTitle, noteContent};
        } else
            return null;
    }

    public int updateNote(String id, String noteTitle, String noteContent) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, noteTitle);
        contentValues.put(NotesContract.NotesEntry.COLUMN_CONTENT, noteContent);
        return notesDbHelper.updateData(NotesContract.NotesEntry.TABLE_NAME, contentValues, id);
    }

    public boolean deleteNote(String noteId) {
        return notesDbHelper.deleteData(noteId);
    }
}
