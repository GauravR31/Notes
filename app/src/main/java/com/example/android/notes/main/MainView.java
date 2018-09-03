package com.example.android.notes.main;

import android.content.Context;

import java.util.List;

import data.Note;

public interface MainView {

    void showEmpty();

    void hideEmpty();

    void showNotes(List<Note> noteList);

    Context getContext();
}
