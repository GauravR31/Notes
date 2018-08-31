package com.example.android.notes.main;

import android.content.Context;
import android.database.Cursor;

public interface MainView {

    void showEmpty();

    void hideEmpty();

    void showNotes(Cursor cursor);

    Context getContext();
}
