package com.example.android.notes.main;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.notes.R;

public class MainActivity extends AppCompatActivity implements MainView {

    private TextView emptyTextView;
    private RecyclerView notesRecyclerView;
    private FloatingActionButton addNoteFAB;
    private NotesAdapter notesAdapter;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emptyTextView = findViewById(R.id.text_view_empty);
        notesRecyclerView = findViewById(R.id.recycler_view_notes);
        mainPresenter = new MainPresenter(this, this);
        mainPresenter.insertNote();
        mainPresenter.onCreate();

    }

    @Override
    public void showEmpty() {
        emptyTextView.setVisibility(View.VISIBLE);
        notesRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideEmpty() {
        emptyTextView.setVisibility(View.GONE);
        notesRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void addNote() {
        addNoteFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void showNotes(Cursor cursor) {
        cursor = mainPresenter.getNotesCursor();
        notesAdapter = new NotesAdapter(this, cursor);
        if (notesAdapter.getItemCount() == 0) {
            showEmpty();
        } else {
            hideEmpty();
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        notesRecyclerView.setLayoutManager(layoutManager);
        notesRecyclerView.setHasFixedSize(true);
        notesRecyclerView.setAdapter(notesAdapter);
    }
}
