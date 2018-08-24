package com.example.android.notes.main;

import android.database.Cursor;

public interface MainView {

    void showEmpty();

    void hideEmpty();

    void addNote();

    void showNotes(Cursor cursor);
}
