package android.ankur.com.notetaker;

import android.ankur.com.notetaker.data.NoteItem;
import android.ankur.com.notetaker.data.NotesDataSource;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**MainActivity is a ListActivity...we want to display lists */
public class MainActivity extends ListActivity {

    /** Code for starting intent */
    public static final int REQUEST_CODE = 1001;

    /** Code for "Delete" item in Context Menu */
    private static final int MENU_DELETE_ID = 1002;

    /**
     * Position ID of the note to be deleted.
     * Required as we will not have it once context menu pops up */
    private int currentNoteId;

    /** Instance of dataSource */
    private NotesDataSource dataSource;

    /** List to contain notes */
    List<NoteItem> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerForContextMenu(getListView()); //Register for Context Menu on this List
        dataSource = new NotesDataSource(this);
        refreshDisplay();
    }

    public void refreshDisplay(){
        notesList = dataSource.findAll();

        ArrayAdapter<NoteItem> adapter =
                new ArrayAdapter<NoteItem>(this, R.layout.list_item_layout, notesList);

        ListView listview = (ListView) findViewById(android.R.id.list);
        listview.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        NoteItem note = notesList.get(position);
        Intent intent = new Intent(this, NoteEditorActivity.class);
        intent.putExtra("key", note.getKey());
        intent.putExtra("text", note.getText());
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
        Handle action bar item clicks here. The action bar will
        automatically handle clicks on the Home/Up button, so long
        as you specify a parent activity in AndroidManifest.xml.
        */
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_create) {
            createNote();
        }
        return super.onOptionsItemSelected(item);
    }

    private void createNote() {
        NoteItem note = NoteItem.getNew();
        Intent intent = new Intent(this, NoteEditorActivity.class);
        intent.putExtra("key", note.getKey());
        intent.putExtra("text", note.getText());
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE){
            if (resultCode == RESULT_OK) {
                NoteItem note = new NoteItem();
                note.setKey(data.getStringExtra("key"));
                note.setText(data.getStringExtra("text"));
                dataSource.update(note);
                refreshDisplay();
                Toast.makeText(getApplicationContext(), "Note Created", Toast.LENGTH_SHORT).show();
            }
            if (resultCode == RESULT_CANCELED) {
                NoteItem note = new NoteItem();
                note.setKey(data.getStringExtra("key"));
                dataSource.remove(note);
                refreshDisplay();
                Toast.makeText(getApplicationContext(), "Note can not be blank. Deleted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /** Context Menu created when it is called by the Context Menu registration*/
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        currentNoteId = (int) info.id;

        menu.add(0,MENU_DELETE_ID,0,"Delete");
    }

    private void confirmDeletion(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Add the buttons
        builder.setTitle("Delete Note");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked Yes button
                dataSource.remove(notesList.get(currentNoteId));
                refreshDisplay();
                Toast.makeText(getApplicationContext(), "Note Deleted", Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });

        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == MENU_DELETE_ID) {
            confirmDeletion();
            return true;
        }
        return false;
    }
}
