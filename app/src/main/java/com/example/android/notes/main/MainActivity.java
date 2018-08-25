package com.example.android.notes.main;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.notes.R;

import detail.DetailActivity;

public class MainActivity extends AppCompatActivity implements MainView {

    private TextView emptyTextView;
    private RecyclerView notesRecyclerView;
    private FloatingActionButton addNoteFAB;
    private NotesAdapter notesAdapter;
    private MainPresenter mainPresenter;
    private boolean insertStatus;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(MainActivity.class.getSimpleName(), "Req code = " + String.valueOf(requestCode));
        Log.d(MainActivity.class.getSimpleName(), "Res code = " + String.valueOf(resultCode));
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                insertStatus = data.getBooleanExtra("INSERT_STATUS", false);
                if (insertStatus) {
                    notesAdapter.swapCursor(mainPresenter.getNotesCursor());
                    hideEmpty();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emptyTextView = findViewById(R.id.text_view_empty);
        notesRecyclerView = findViewById(R.id.recycler_view_notes);
        addNoteFAB = (FloatingActionButton) findViewById(R.id.fab_new_note);

        addNoteFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailIntent = new Intent(view.getContext(), DetailActivity.class);
                startActivityForResult(detailIntent, 1);
            }
        });

        mainPresenter = new MainPresenter(this, this);
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
