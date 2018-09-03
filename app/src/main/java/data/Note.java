package data;

public class Note {
    private String noteId;
    private String noteTitle;
    private String noteContent;

    Note(String id, String title, String content) {
        this.noteId = id;
        this.noteTitle = title;
        this.noteContent = content;
    }

    public String getNoteId() {
        return noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteId(String id) {
        this.noteId = id;
    }

    public void setNoteTitle(String title) {
        this.noteTitle = title;
    }

    public void setNoteContent(String content) {
        this.noteContent = content;
    }
}
