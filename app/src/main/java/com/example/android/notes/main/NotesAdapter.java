package com.example.android.notes.main;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.notes.R;

import data.NotesContract;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private Context context;
    private Cursor notesCursor;

    public NotesAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.notesCursor = cursor;
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
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        if (!notesCursor.moveToPosition(position))
            return;

        String noteTitle = notesCursor.getString(notesCursor.
                getColumnIndex(NotesContract.NotesEntry.COLUMN_TITLE));
        String noteContent = notesCursor.getString(notesCursor.
                getColumnIndex(NotesContract.NotesEntry.COLUMN_CONTENT));

        holder.noteTitleTextView.setText(noteTitle);
        holder.noteContentTextView.setText(noteContent);
    }

    @Override
    public int getItemCount() {
        return notesCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (notesCursor != null)
            notesCursor.close();

        notesCursor = newCursor;

        if (newCursor != null)
            this.notifyDataSetChanged();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        public final TextView noteTitleTextView;
        public final TextView noteContentTextView;

        public NotesViewHolder(View itemView) {
            super(itemView);
            noteTitleTextView = itemView.findViewById(R.id.text_view_note_title);
            noteContentTextView = itemView.findViewById(R.id.text_view_note_content);
        }
    }
}
