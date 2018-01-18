package smithavenkatesh.androiddatastorage.cmpe277.com.androiddatastorage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SQLiteActivity extends AppCompatActivity {

    public int counter=0;
    private SimpleDateFormat s=new SimpleDateFormat("MM/dd/yyyy-hh:mm a");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        counter=sharedPrefs.getInt("SQL_COUNTER", 0);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        counter=sharedPrefs.getInt("SQL_COUNTER", 0);
    }


    public void SaveSqlite(View view)
    {
        EditText editText=(EditText)findViewById(R.id.enterBlog);
        String message=editText.getText().toString();

        DataController dataController=new DataController(getBaseContext());
        dataController.open();

        long retValue= dataController.insert(message);
        dataController.close();
        if(retValue!=-1)
        {
            Context context = getApplicationContext();
            CharSequence text="Message saved successfully";
            int duration= Toast.LENGTH_LONG;
            Toast.makeText(context, text, duration).show();

            try
            {
                counter+=1;

                SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("SQL_COUNTER", counter);
                editor.commit();

                OutputStreamWriter out=new OutputStreamWriter(openFileOutput(PreferencesActivity.STORE_PREFERENCES,MODE_APPEND));
                out.write("\nSQLite "+counter+", "+s.format(new Date()));
                out.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        Intent intent=new Intent(this,DataStorage.class);
        startActivity(intent);
    }

    public void Cancelsqlite(View view)
    {
        Intent intent = new Intent(this,DataStorage.class);
        startActivity(intent);

        SQLiteActivity.this.finish();
    }
}
