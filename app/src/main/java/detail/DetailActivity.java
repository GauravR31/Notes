package detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.example.android.notes.R;

public class DetailActivity extends AppCompatActivity implements DetailView {

    private EditText titleEditText;
    private EditText contentEditText;
    private DetailPresenter detailPresenter;
    public boolean insertStatus;
    public static final String INSERT_STATUS = "INSERT_STATUS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        titleEditText = (EditText) findViewById(R.id.edit_text_note_title);
        contentEditText = (EditText) findViewById(R.id.edit_text_note_content);
        detailPresenter = new DetailPresenter(this, this);
    }

    @Override
    public void newNote() {
        String noteTitle = titleEditText.getText().toString();
        String noteContent = contentEditText.getText().toString();
        insertStatus = detailPresenter.insertNote(noteTitle, noteContent);

    }

    @Override
    public void editNote() {

    }

    @Override
    public void onBackPressed() {
        newNote();

        Intent intent = new Intent();
        intent.putExtra(INSERT_STATUS, insertStatus);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}
