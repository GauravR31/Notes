package com.example.android.notes.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.notes.R;

import java.util.List;

import data.Note;
import data.NotesDbManager;
import detail.DetailActivity;

public class MainActivity extends AppCompatActivity implements MainView {

    private TextView emptyTextView, creditsTextView;
    private RecyclerView notesRecyclerView;
    private NotesAdapter notesAdapter;
    private MainPresenter mainPresenter;
    private ImageView emptyImageView;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                boolean insertStatus = false;
                boolean updateStatus = false;
                boolean deleteStatus = false;
                if (extras != null) {
                    insertStatus = extras.getBoolean(getString(R.string.insert_key), false);
                    updateStatus = extras.getBoolean(getString(R.string.update_key), false);
                    deleteStatus = extras.getBoolean(getString(R.string.delete_key), false);
                }
                if (insertStatus || updateStatus || deleteStatus) {
                    notesAdapter.swapCursor(mainPresenter.getNoteList());
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
        creditsTextView = (TextView) findViewById(R.id.text_view_credits);
        emptyImageView = (ImageView) findViewById(R.id.image_view_empty);
        NotesDbManager notesDbManager = new NotesDbManager(this);
        FloatingActionButton addNoteFAB = (FloatingActionButton) findViewById(R.id.fab_new_note);

        addNoteFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailIntent = new Intent(view.getContext(), DetailActivity.class);
                startActivityForResult(detailIntent, 1);
            }
        });

        mainPresenter = new MainPresenter(this, notesDbManager);
        mainPresenter.onCreate();

        registerForContextMenu(notesRecyclerView);
    }

    @Override
    public void showEmpty() {
        creditsTextView.setVisibility(View.VISIBLE);
        emptyImageView.setVisibility(View.VISIBLE);
        emptyTextView.setVisibility(View.VISIBLE);
        notesRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideEmpty() {
        creditsTextView.setVisibility(View.GONE);
        emptyImageView.setVisibility(View.GONE);
        emptyTextView.setVisibility(View.GONE);
        notesRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNotes(List<Note> noteList) {
        noteList = mainPresenter.getNoteList();
        notesAdapter = new NotesAdapter(this, noteList);

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

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final NotesAdapter.NotesViewHolder notesViewHolder;
        int position;
        try {
            position = ((NotesAdapter) notesRecyclerView.getAdapter()).getPosition();
            notesViewHolder = (NotesAdapter.NotesViewHolder)
                    notesRecyclerView.findViewHolderForAdapterPosition(position);
        } catch (Exception e) {
            return super.onContextItemSelected(item);
        }

        switch (item.getItemId()) {
            case 0:
                final AlertDialog.Builder deleteAlert = new AlertDialog.Builder(this);
                deleteAlert.setTitle(getString(R.string.delete_alert_title));
                deleteAlert.setMessage(getString(R.string.delete_alert_message));
                deleteAlert.setPositiveButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String id = notesViewHolder.noteIdTextView.getText().toString();
                        mainPresenter.deleteNote(id);
                        Toast.makeText(getContext(), getString(R.string.deleted_toast), Toast.LENGTH_SHORT).show();
                        notesAdapter.swapCursor(mainPresenter.getNoteList());
                        if (notesAdapter.getItemCount() == 0)
                            showEmpty();
                        dialogInterface.dismiss();
                    }
                });

                deleteAlert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                deleteAlert.show();

                return true;
            case 1:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");

                shareIntent.putExtra(Intent.EXTRA_SUBJECT, notesViewHolder.noteTitleTextView.getText().toString());
                shareIntent.putExtra(Intent.EXTRA_TEXT, notesViewHolder.noteContentTextView.getText().toString());

                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_intent_title)));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}
