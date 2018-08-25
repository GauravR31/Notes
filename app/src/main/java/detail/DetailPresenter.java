package detail;

import android.content.Context;

import data.NotesDbManager;

public class DetailPresenter {

    private DetailView detailView;
    private NotesDbManager notesDbManager;
    private Context context;

    DetailPresenter(DetailView detailView1, Context context1) {
        this.detailView = detailView1;
        this.context = context1;
        notesDbManager = new NotesDbManager(context);
    }

    public boolean insertNote(String title, String content) {
        return notesDbManager.insertNote(title, content);
    }
}
