package detail;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.notes.R;

public class DetailActivity extends AppCompatActivity implements DetailView {

    private EditText titleEditText;
    private EditText contentEditText;
    private DetailPresenter detailPresenter;
    public boolean insertStatus;
    public boolean updateStatus;
    public boolean deleteStatus;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        titleEditText = (TextInputEditText) findViewById(R.id.edit_text_note_title);
        contentEditText = (EditText) findViewById(R.id.edit_text_note_content);
        detailPresenter = new DetailPresenter(this);
        MenuItem item = findViewById(R.id.action_delete);

        id = getIntent().getStringExtra("NOTE_ID");
        if (id != null) {
            editNote();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (id == null)
            menu.getItem(0).setEnabled(false);
        return super.onPrepareOptionsMenu(menu);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                final AlertDialog.Builder deleteAlert = new AlertDialog.Builder(this);
                deleteAlert.setTitle(getString(R.string.delete_alert_title));
                deleteAlert.setMessage(getString(R.string.delete_alert_message));
                deleteAlert.setPositiveButton(getText(R.string.delete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        detailPresenter.deleteNote(id);
                        Toast.makeText(getContext(), getString(R.string.deleted_toast), Toast.LENGTH_SHORT).show();
                        deleteStatus = true;
                        dialogInterface.dismiss();
                        onBackPressed();
                    }
                });

                deleteAlert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                deleteAlert.show();
                break;
            default:
                break;
        }

        return true;
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
        extras.putBoolean(getString(R.string.insert_key), insertStatus);
        extras.putBoolean(getString(R.string.update_key), updateStatus);
        extras.putBoolean(getString(R.string.delete_key), deleteStatus);
        intent.putExtras(extras);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}
