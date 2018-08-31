package detail;

import android.database.Cursor;

import data.NotesContract;
import data.NotesDbManager;

public class DetailPresenter {

    private NotesDbManager notesDbManager;

    DetailPresenter(DetailView detailView1) {
        notesDbManager = new NotesDbManager(detailView1.getContext());
    }

    public boolean insertNote(String title, String content, long time) {
        return notesDbManager.insertNote(title, content, time);
    }

    public String[] getNoteDetails(String id) {
        Cursor cursor = notesDbManager.getSingleNote(id);
        if (cursor != null && cursor.moveToFirst()) {

            String noteTitle = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_TITLE));
            String noteContent = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_CONTENT));
            return new String[]{noteTitle, noteContent};
        } else {
            return null;
        }
    }

    public int updateNote(String id, String nTitle, String nContent) {
        return notesDbManager.updateNote(id, nTitle, nContent);
    }
}
