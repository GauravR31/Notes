package detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.notes.R;

public class DetailActivity extends AppCompatActivity implements DetailView {

    private EditText titleEditText;
    private EditText contentEditText;
    private DetailPresenter detailPresenter;
    public boolean insertStatus;
    public boolean updateStatus;
    public static final String INSERT_STATUS = "INSERT_STATUS";
    public static final String UPDATE_STATUS = "UPDATE_STATUS";
    private String id;
    public static final String LOG_TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        titleEditText = (TextInputEditText) findViewById(R.id.edit_text_note_title);
        contentEditText = (EditText) findViewById(R.id.edit_text_note_content);
        detailPresenter = new DetailPresenter(this);
        id = getIntent().getStringExtra("NOTE_TITLE");
        if (id != null) {
            editNote();
        }
    }

    @Override
    public void newNote() {
        String noteTitle = titleEditText.getText().toString();
        String noteContent = contentEditText.getText().toString();
        long timestamp = System.currentTimeMillis();
        if (!noteTitle.equals("") && !noteContent.equals("")) {
            insertStatus = detailPresenter.insertNote(noteTitle, noteContent, timestamp);
        } else {
            Toast.makeText(this, "Empty title or content!", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void editNote() {
        String[] noteDetails = detailPresenter.getNoteDetails(id);
        titleEditText.setText(noteDetails[0]);
        contentEditText.setText(noteDetails[1]);
    }

    public void updateNote() {
        String noteTitle = titleEditText.getText().toString();
        String noteContent = contentEditText.getText().toString();

        if (detailPresenter.updateNote(id, noteTitle, noteContent) != 0) {
            updateStatus = true;
        }

        id = null;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onBackPressed() {
        if (id == null)
            newNote();
        else {
            updateNote();
        }

        Bundle extras = new Bundle();

        Intent intent = new Intent();
        extras.putBoolean(INSERT_STATUS, insertStatus);
        extras.putBoolean(UPDATE_STATUS, updateStatus);
        intent.putExtras(extras);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}
