package android.ankur.com.notetaker;

import android.ankur.com.notetaker.data.NoteItem;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * Created by Ankur on 3/2/2015.
 */
public class NoteEditorActivity extends Activity {

    private NoteItem note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = this.getIntent();
        note = new NoteItem();
        note.setKey(intent.getStringExtra("key"));
        note.setText(intent.getStringExtra("text"));

        EditText editor = (EditText) findViewById(R.id.noteText);
        editor.setText(note.getText());
        editor.setSelection(note.getText().length());
    }

    private void saveAndFinish(){
        EditText editor = (EditText) findViewById(R.id.noteText);
        String noteText = editor.getText().toString();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("key", note.getKey());
        intent.putExtra("text", noteText);
        Log.i("NOTES", String.valueOf(noteText.length()));
        if (noteText.length() > 1)
            setResult(RESULT_OK, intent);
        else
            setResult(RESULT_CANCELED, intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            saveAndFinish();
        }
        //This event has been handled (coz of save and finish...don't do anything else
        return false;
    }

    @Override
    public void onBackPressed() {
        saveAndFinish();
    }
}

