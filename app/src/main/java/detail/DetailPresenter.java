package detail;

import data.NotesDbManager;

public class DetailPresenter {

    private NotesDbManager notesDbManager;

    DetailPresenter(NotesDbManager dbManager) {
        notesDbManager = dbManager;
    }

    public boolean insertNote(String title, String content, long time) {
        return notesDbManager.insertNote(title, content, time);
    }

    public String[] getNoteDetails(String id) {
        return notesDbManager.getSingleNote(id);
    }

    public int updateNote(String id, String nTitle, String nContent) {
        return notesDbManager.updateNote(id, nTitle, nContent);
    }

    public boolean deleteNote(String noteId) {
        return notesDbManager.deleteNote(noteId);
    }
}
