package com.example.android.notes.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.notes.R;

import java.util.List;

import data.Note;
import detail.DetailActivity;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private Context context;
    private List<Note> noteList;
    private int position;

    public int getPosition() {
        return position;
    }

    private void setPosition(int pos) {
        this.position = pos;
    }

    NotesAdapter(Context context, List<Note> list) {
        this.context = context;
        this.noteList = list;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.notes_item_view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = layoutInflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NotesViewHolder holder, final int position) {
        if (noteList.get(position) == null)
            return;

        Note note = noteList.get(position);
        String noteTitle = note.getNoteTitle();
        String noteContent = note.getNoteContent();
        String noteId = note.getNoteId();

        holder.noteTitleTextView.setText(noteTitle);
        holder.noteContentTextView.setText(noteContent);
        holder.noteIdTextView.setText(noteId);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                setPosition(holder.getAdapterPosition());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void swapCursor(List<Note> newList) {
        if (noteList != null)
            noteList.clear();

        noteList = newList;

        if (newList != null)
            this.notifyDataSetChanged();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        final TextView noteTitleTextView;
        final TextView noteContentTextView;
        final TextView noteIdTextView;

        NotesViewHolder(View itemView) {
            super(itemView);
            noteTitleTextView = itemView.findViewById(R.id.text_view_note_title);
            noteContentTextView = itemView.findViewById(R.id.text_view_note_content);
            noteIdTextView = itemView.findViewById(R.id.text_view_note_id);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String string = noteIdTextView.getText().toString();
                    Intent detailIntent = new Intent(context, DetailActivity.class);
                    detailIntent.putExtra("NOTE_ID", string);
                    ((Activity) context).startActivityForResult(detailIntent, 1);
                }
            });

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle(noteTitleTextView.getText().toString());
            contextMenu.add(Menu.NONE, 0, 1, R.string.delete);
            contextMenu.add(Menu.NONE, 1, 0, R.string.share);
        }
    }
}
