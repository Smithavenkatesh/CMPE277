package smithavenkatesh.androiddatastorage.cmpe277.com.androiddatastorage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PreferencesActivity extends AppCompatActivity {

    EditText editTextEnterBookName,editTextEnterBookAuthor,editTextEnterDescription;

    public final static String STORE_PREFERENCES="storePrefFinal.txt";


    public int counter=0;
    private SimpleDateFormat s=new SimpleDateFormat("MM/dd/yyyy-hh:mm a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        counter=sharedPrefs.getInt("COUNTER", 0);
    }


    @Override
    public void onResume()
    {
        super.onResume();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        counter=sharedPrefs.getInt("COUNTER", 0);
    }


    public void Save(View view)
    {
        editTextEnterBookName = (EditText)findViewById(R.id.enterbookName);
        editTextEnterBookAuthor = (EditText)findViewById(R.id.enterbookAuthor);
        editTextEnterDescription = (EditText)findViewById(R.id.enterDescription);

        String bookName = editTextEnterBookName.getText().toString();
        String bookAuthor = editTextEnterBookAuthor.getText().toString();
        String desc = editTextEnterDescription.getText().toString();

        if(bookName!=null && bookAuthor!=null && desc!=null)
        {
            try{
                    counter+=1;

                    SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor editor=sharedPreferences.edit();

                    editor.putInt("COUNTER", counter);
                    editor.putString("BookName", bookName);
                    editor.putString("BookAuthor", bookAuthor);
                    editor.putString("Description",desc );
                    editor.commit();

                    OutputStreamWriter out=new OutputStreamWriter(openFileOutput(STORE_PREFERENCES,MODE_APPEND));
                    String message="\nSaved Preference "+counter+", "+s.format(new Date());
                    out.write(message);
                    out.close();
                }
            catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
            catch (IOException e)
                {
                    e.printStackTrace();
                }
        }

        //Retrieve the contents of the file in the OnResume() in DataStorage

        //Go back to the main screen
        Intent intent = new Intent(this,DataStorage.class);
        startActivity(intent);

    }

    public void Cancel(View view)
    {
        Intent intent = new Intent(this,DataStorage.class);
        startActivity(intent);
        PreferencesActivity.this.finish();

    }
}
