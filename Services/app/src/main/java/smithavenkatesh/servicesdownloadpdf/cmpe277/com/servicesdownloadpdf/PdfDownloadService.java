package smithavenkatesh.servicesdownloadpdf.cmpe277.com.servicesdownloadpdf;



import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


public class PdfDownloadService extends Service {
    private boolean success = false;
    public static final String NOTIFICATION = "service receiver";

    private final IBinder binder = new MyBinder();

    public class MyBinder extends Binder {
        PdfDownloadService getService() {
            return PdfDownloadService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String urlPath = intent.getStringExtra("urlpath");
        String fileName = intent.getStringExtra("filename");
        new DownloadFileFromURL().execute(urlPath,fileName);
        return START_STICKY;
    }


    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("Starting download");
        }

        @Override
        protected String doInBackground(String... params) {
            int count;
            File output = new File(getExternalFilesDir(null),params[1]);

            boolean ExternalStorageAvailable = isExternalStorageWritable();
            System.out.print(ExternalStorageAvailable);


            // File output = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
            //File output = new File(getBaseContext().getFilesDir(),params[1]);
            try {
                System.out.println(params[1]);
                System.out.println("Downloading " + params[1]);
                URL url = new URL(params[0]);

                URLConnection connection = url.openConnection();
                connection.connect();
                InputStream inputStream = new BufferedInputStream(url.openStream(), 8192);

                OutputStream outputStream = new FileOutputStream(output.getPath());
                byte data[] = new byte[1024];

                long total = 0;
                while ((count = inputStream.read(data)) != -1) {
                    total += count;
                    outputStream.write(data, 0, count);

                }
                outputStream.flush();
                outputStream.close();
                inputStream.close();
                success = true;

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            if (success) setDownloadedFlag(params[1]);
            else setFailedFlag(params[1]);
            publishResults(params[1]);
            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            System.out.println("onPostExecute");
            stopSelf();
        }

        /* Checks if external storage is available for read and write */
        public boolean isExternalStorageWritable() {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                return true;
            }
            return false;
        }

        private void publishResults(String filename) {
            Intent intent = new Intent(NOTIFICATION);
            intent.putExtra("file", filename);
            sendBroadcast(intent);
        }

        private void setDownloadedFlag (String filename) {
            if (filename.equals(PdfDownload.filename1)) PdfDownload.file1Flag = 2;
            else if (filename.equals(PdfDownload.filename2)) PdfDownload.file2Flag = 2;
            else if (filename.equals(PdfDownload.filename3)) PdfDownload.file3Flag = 2;
            else if (filename.equals(PdfDownload.filename4)) PdfDownload.file4Flag = 2;
            else if (filename.equals(PdfDownload.filename5)) PdfDownload.file5Flag = 2;
        }

        private void setFailedFlag (String filename) {
            if (filename.equals(PdfDownload.filename1)) PdfDownload.file1Flag = -1;
            else if (filename.equals(PdfDownload.filename2)) PdfDownload.file2Flag = -1;
            else if (filename.equals(PdfDownload.filename3)) PdfDownload.file3Flag = -1;
            else if (filename.equals(PdfDownload.filename4)) PdfDownload.file4Flag = -1;
            else if (filename.equals(PdfDownload.filename5)) PdfDownload.file5Flag = -1;
        }

        }


    }

