package smithavenkatesh.androiddatastorage.cmpe277.com.androiddatastorage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DataStorage extends AppCompatActivity {

    TextView savedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_storage);

        System.out.println("Hello");
    }

    @Override
    protected void onResume() {
        super.onResume();
        try
        {
            InputStream in=openFileInput(PreferencesActivity.STORE_PREFERENCES);
            if(in!=null)
            {
                InputStreamReader tmp=new InputStreamReader(in);
                BufferedReader reader=new BufferedReader(tmp);
                String str;
                StringBuilder buf=new StringBuilder();
                while((str=reader.readLine())!=null)
                {
                    buf.append(str +"\n");
                }
                in.close();
                savedPref=(TextView)findViewById(R.id.storageDetails);
                savedPref.setText(buf.toString());
            }


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void preferencesDataStorage(View view)
    {
        Intent intent=new Intent(this,PreferencesActivity.class);
        startActivity(intent);
    }

    public void sqliteDataStorage(View view)
    {
        Intent intent=new Intent(this,SQLiteActivity.class);
        startActivity(intent);
    }

    public void close(View view)
    {
        DataStorage.this.finish();
    }
}
