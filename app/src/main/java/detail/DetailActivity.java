package detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.notes.R;

public class DetailActivity extends AppCompatActivity implements DetailView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    @Override
    public void newNote() {

    }

    @Override
    public void editNote() {

    }
}
