package com.example.android.notes.main;

import java.util.List;

import data.Note;
import data.NotesDbManager;

public class MainPresenter {

    private MainView mainView;
    private NotesDbManager notesDbManager;

    MainPresenter(MainView mainView, NotesDbManager dbManager) {
        this.mainView = mainView;
        this.notesDbManager = dbManager;
    }

    public void onCreate() {
        if (mainView != null) {
            mainView.showNotes(getNoteList());
        }
    }

    public List<Note> getNoteList() {
        return notesDbManager.getNoteList();
    }

    public void deleteNote(String noteId) {
        notesDbManager.deleteNote(noteId);
    }
}
