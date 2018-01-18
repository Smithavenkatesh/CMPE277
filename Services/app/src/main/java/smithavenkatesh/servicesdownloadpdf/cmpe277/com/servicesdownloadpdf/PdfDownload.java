package smithavenkatesh.servicesdownloadpdf.cmpe277.com.servicesdownloadpdf;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;


public class PdfDownload extends AppCompatActivity {
    EditText editTextPDF1, editTextPDF2, editTextPDF3, editTextPDF4, editTextPDF5;
    Button buttonDownload;
    String url1, url2, url3, url4, url5;
    public static String filename1,filename2,filename3,filename4,filename5,currFileName;

    /*
    0   => not started
    1   => downloading
    -1  => failed
    2   => downloaded
    */

    public static int file1Flag = 0;
    public static int file2Flag = 0;
    public static int file3Flag = 0;
    public static int file4Flag = 0;
    public static int file5Flag = 0;
    static int currFileFlag;

    static String downloadPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_download);

        editTextPDF1 = (EditText) findViewById(R.id.enterpdf1);
        editTextPDF2 = (EditText) findViewById(R.id.enterpdf2);
        editTextPDF3 = (EditText) findViewById(R.id.enterpdf3);
        editTextPDF4 = (EditText) findViewById(R.id.enterpdf4);
        editTextPDF5 = (EditText) findViewById(R.id.enterpdf5);

        buttonDownload = (Button) findViewById(R.id.StartDownload);

        // DownloadPath for PDF's
        //downloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        downloadPath =   getExternalFilesDir(null).toString();

        registerReceiver(receiver, new IntentFilter(
                PdfDownloadService.NOTIFICATION));


        buttonDownload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                url1 = editTextPDF1.getText().toString();
                url2 = editTextPDF2.getText().toString();
                url3 = editTextPDF3.getText().toString();
                url4 = editTextPDF4.getText().toString();
                url5 = editTextPDF5.getText().toString();


                filename1 = url1.substring(url1.lastIndexOf('/')+1);
                filename2 = url2.substring(url2.lastIndexOf('/')+1);
                filename3 = url3.substring(url3.lastIndexOf('/')+1);
                filename4 = url4.substring(url4.lastIndexOf('/')+1);
                filename5 = url5.substring(url5.lastIndexOf('/')+1);

                if (TextUtils.isEmpty(url1) && TextUtils.isEmpty(url2) && TextUtils.isEmpty(url3) && TextUtils.isEmpty(url4) && TextUtils.isEmpty(url5)) {
                    Toast.makeText(PdfDownload.this, "Enter at least in one fields", Toast.LENGTH_LONG).show();
                } else
                    startDownload(view);
            }
        });


    }


    public void startDownload(View view) {

        Toast.makeText(this, "Starting all downloads !", Toast.LENGTH_SHORT).show();

        startDownloadService(url1,filename1);
        file1Flag = 1;

        startDownloadService(url2,filename2);
        file2Flag = 1;

        startDownloadService(url3,filename3);
        file3Flag = 1;

        startDownloadService(url4,filename4);
        file4Flag = 1;

        startDownloadService(url5,filename5);
        file5Flag = 1;
    }


    private void startDownloadService(String link, String filename) {
        Intent intent = new Intent(getBaseContext(), PdfDownloadService.class);
        intent.putExtra("urlpath", link);
        intent.putExtra("filename",filename);
        startService(intent);
    }

    private void refreshTextView (int x) {
        System.out.print("In refreshview");
        if (x > 5 || x < 1) {
            System.out.println("refreshTextView wrong parameter, returning");
            return;
        }

        if (x == 1) {
            currFileFlag = file1Flag;
//            currTextView = textView1;
            currFileName = filename1;
//            currDownloadLink = downloadLink1;
        } else if (x == 2) {
            currFileFlag = file2Flag;
//            currTextView = textView2;
            currFileName = filename2;
//            currDownloadLink = downloadLink2;
        } else if (x == 3) {
            currFileFlag = file3Flag;
//            currTextView = textView3;
            currFileName = filename3;
//            currDownloadLink = downloadLink3;
        } else if (x == 4) {
            currFileFlag = file4Flag;
//            currTextView = textView4;
            currFileName = filename4;
//            currDownloadLink = downloadLink4;
        } else {
            currFileFlag = file5Flag;
//            currTextView = textView5;
            currFileName = filename5;
//            currDownloadLink = downloadLink5;
        }
        System.out.println("Hello");
        if (currFileFlag == 0)  Toast.makeText(this, "Starting the download !", Toast.LENGTH_SHORT).show();
        else if (currFileFlag == 1)     Toast.makeText(this, currFileName+" is downloading...", Toast.LENGTH_SHORT).show();
        else if (currFileFlag == 2) {    Toast.makeText(this, currFileName + " downloaded at " + downloadPath, Toast.LENGTH_SHORT).show();
        System.out.println("File download complete");}
        else if (currFileFlag == -1) Toast.makeText(this, currFileName + " downloaded failed!", Toast.LENGTH_SHORT).show();


//        if (currFileFlag == 0) currTextView.setText("PDF" + x + " File Location: " + currDownloadLink);
//        else if (currFileFlag == 1) currTextView.setText(currFileName + " is downloading...");
//        else if (currFileFlag == 2) currTextView.setText(currFileName + " downloaded at " + downloadPath);
//        else if (currFileFlag == -1) currTextView.setText(currFileName + " downloaded failed!");
//        else currTextView.setText("unknown internal error");
    }

    private void refreshAllTextViews () {
        refreshTextView(1);
        refreshTextView(2);
        refreshTextView(3);
        refreshTextView(4);
        refreshTextView(5);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            int x;
            if (bundle != null) {
                String filename = bundle.getString("file");
                System.out.println("BroadcastReceiver -> onReceive -> Filename -> " + filename);
                if (filename.equals(filename1)) x=1;
                else if (filename.equals(filename2)) x=2;
                else if (filename.equals(filename3)) x=3;
                else if (filename.equals(filename4)) x=4;
                else x=5;
                refreshTextView(x);
            }

        }


    };
}



