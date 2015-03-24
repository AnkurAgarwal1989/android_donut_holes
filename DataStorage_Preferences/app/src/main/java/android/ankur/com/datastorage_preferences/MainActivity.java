package android.ankur.com.datastorage_preferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
    private SharedPreferences.OnSharedPreferenceChangeListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // use PreferenceManager for PreferenceActivity
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Add a listener so that we can update main activity when preferences are changed.
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                MainActivity.this.getPrefs(null);  //entire view
            }
        };

        //register listener
        prefs.registerOnSharedPreferenceChangeListener(listener);

        // use getPreferences for using preferences separately
        //prefs = getPreferences(MODE_PRIVATE);
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
            // start preference activity
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
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
