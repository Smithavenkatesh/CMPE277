package smithavenkatesh.implicitintent.cmpe277.com.implicitintent;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class ImplicitIntent extends Activity {

    private EditText editTextEnterPhone,editTextEnterUrl;
    private Button buttonLaunch,buttonRing;
    private String url,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_implicit_intent);

        editTextEnterUrl = (EditText) findViewById(R.id.enter_url);
        editTextEnterPhone = (EditText)findViewById(R.id.enter_phone);

        buttonLaunch = (Button)findViewById(R.id.Launch);
        buttonRing =(Button)findViewById(R.id.Ring);

        buttonLaunch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                url = editTextEnterUrl.getText().toString();
                if (TextUtils.isEmpty(url)) {Toast.makeText(ImplicitIntent.this, "Enter URL", Toast.LENGTH_LONG).show();
                }
                else if(url.contains("http://"))
                    Launch();
                else {
                    url = "http://" + url;
                    Launch();
                }
            }
        });


        buttonRing.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    phone=PhoneNumberUtils.formatNumber(editTextEnterPhone.getText().toString(), Locale.getDefault().getCountry());
                } else {
                    phone=PhoneNumberUtils.formatNumber(editTextEnterPhone.getText().toString());//deprecated method
                }

                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(ImplicitIntent.this, "Enter Phone Number", Toast.LENGTH_LONG).show();
                }
                else {
                    phone="tel:"+phone;
                    Ring();
                }
            }
        });


    }

    public void Launch()
    {
        //Create a web page intent
        Uri webpage = Uri.parse(url);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);

        //Verify There is an App to Receive the Intent
        PackageManager packageManager = getPackageManager();
        List activities = packageManager.queryIntentActivities(webIntent, PackageManager.MATCH_DEFAULT_ONLY);
        boolean isIntentSafe = activities.size() > 0;

        // Start an activity if it's safe
        if (isIntentSafe) {
            startActivity(webIntent);
        }

    }

    public void Ring()
    {
        //Create a web page intent
        Uri phoneNumber = Uri.parse(phone);
        Intent phoneIntent = new Intent(Intent.ACTION_DIAL,phoneNumber);

        //Verify There is an App to Receive the Intent
        PackageManager packageManager = getPackageManager();
        List activities = packageManager.queryIntentActivities(phoneIntent, PackageManager.MATCH_DEFAULT_ONLY);
        boolean isIntentSafe = activities.size() > 0;

        // Start an activity if it's safe
        if (isIntentSafe) {
            startActivity(phoneIntent);
        }

    }

    public void Close(View view)
    {
        ImplicitIntent.this.finish();
    }


}
