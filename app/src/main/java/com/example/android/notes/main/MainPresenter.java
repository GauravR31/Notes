package com.example.android.notes.main;

import android.database.Cursor;

import data.NotesDbManager;

public class MainPresenter {

    private MainView mainView;
    private NotesDbManager notesDbManager;
    private Cursor notesCursor;

    MainPresenter(MainView mainView) {
        this.mainView = mainView;
        notesDbManager = new NotesDbManager(mainView.getContext());
    }

    public void onCreate() {
        if (mainView != null) {
            mainView.showNotes(notesCursor);
        }
    }

    public Cursor getNotesCursor() {
        notesCursor = notesDbManager.getCursor();
        return notesCursor;
    }
}
