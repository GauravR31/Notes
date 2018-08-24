package com.example.android.notes.main;

import android.content.Context;
import android.database.Cursor;

import data.NotesDbManager;

public class MainPresenter {

    private MainView mainView;
    private NotesDbManager notesDbManager;
    private Cursor notesCursor;

    public MainPresenter(MainView mainView, Context context) {
        this.mainView = mainView;
        notesDbManager = new NotesDbManager(context);
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

    public void insertNote() {
        notesDbManager.insertNote();
    }
}
