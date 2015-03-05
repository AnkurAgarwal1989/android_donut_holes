package android.ankur.com.datastorage_preferences;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = "DS_PREF";
    public static final String USERNAME = "username";
    public static final String DETAIL = "detail";

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getPreferences(MODE_PRIVATE);
        getPrefs(findViewById(android.R.id.content));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getPrefs(View v){
        Log.i(TAG, "Getting prefs.");
        EditText username = (EditText) findViewById(R.id.edit_User);
        EditText detail = (EditText) findViewById(R.id.edit_Detail);
        username.setText(prefs.getString(USERNAME, "Not Found"));
        detail.setText(prefs.getString(DETAIL, "Not Found"));
    }

    public void setPrefs(View v){
        Log.i(TAG, "Setting prefs.");
        SharedPreferences.Editor editor = prefs.edit();
        EditText username = (EditText) findViewById(R.id.edit_User);
        EditText detail = (EditText) findViewById(R.id.edit_Detail);
        editor.putString(USERNAME, String.valueOf(username.getText()));
        editor.putString(DETAIL, String.valueOf(detail.getText()));
        editor.commit();
    }
}
