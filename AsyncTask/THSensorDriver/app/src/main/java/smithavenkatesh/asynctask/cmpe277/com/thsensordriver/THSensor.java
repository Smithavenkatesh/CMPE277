package smithavenkatesh.asynctask.cmpe277.com.thsensordriver;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextUtils;

import java.util.Random;


public class THSensor extends Activity {

    public EditText editText_enter_no_of_sensors;
    public int int_enter_temp, int_enter_humidity, int_enter_activity,no_of_sensors;
    public TextView textView_enter_temp, textView_enter_humidity, textView_enter_activity, textView_scrollViewOutput;
    public String string_enter_temp, string_enter_humidity,string_enter_activity,string_no_of_sensors;;


    Random random = new Random();
    WrapperTHSensor w = new WrapperTHSensor();




    @Override
    public void onCreate(Bundle savedInstanceState) {
        Button buttonConvert;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thsensor);

        buttonConvert = (Button)findViewById(R.id.Generate);
        editText_enter_no_of_sensors = (EditText) findViewById(R.id.enter_no_of_sensors);


        //Handling empty editText_dollar_amt
        buttonConvert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    string_no_of_sensors = editText_enter_no_of_sensors.getText().toString();
                    System.out.println(string_no_of_sensors);
                    no_of_sensors = Integer.parseInt(editText_enter_no_of_sensors.getText().toString());
                    if (TextUtils.isEmpty(string_no_of_sensors)) {
                        Toast.makeText(THSensor.this, "Enter all the fields", Toast.LENGTH_LONG).show();
                    } else
                        generate();
                }
                catch(Exception e) {
                    e.getStackTrace();
                }

            }
        });

    }

    private class Random_TH_Driver_Sensor_Task extends AsyncTask<Integer,Void, Void> {


        int max_temp = 220, minTemp = 20;

        @Override
        protected Void doInBackground(Integer... Integer_no_of_sensors) {

            int int_no_of_sensors= (int)Integer_no_of_sensors[0];
            w.sensor_no = int_no_of_sensors;
            System.out.println("w.sensor_no "+w.sensor_no);

            w.temp=new String[int_no_of_sensors];
            w.humidity=new String[int_no_of_sensors];
            w.activity=new String[int_no_of_sensors];

            for (int i = 0; i < int_no_of_sensors; i++) {
                //Get random numbers for temperature,humidity and activity


                int_enter_temp = random.nextInt(max_temp - minTemp + 1) + minTemp;
                int_enter_humidity = random.nextInt(101);
                int_enter_activity = random.nextInt(500);

                string_enter_temp = String.valueOf(int_enter_temp) + "F";
                string_enter_humidity = String.valueOf(int_enter_humidity) + "%";
                string_enter_activity=String.valueOf(int_enter_activity);


                w.temp[i] = string_enter_temp;
                w.humidity[i] = string_enter_humidity;
                w.activity[i] = string_enter_activity;



                try {

                    Thread.sleep(5);
                    //publishProgress(w);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                publishProgress();

                // If the AsyncTask cancelled
                if (isCancelled()) {
                    break;
                }

            }
            return null;
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

            int length = w.temp.length;
            System.out.println(length);

            textView_enter_temp = (TextView) findViewById(R.id.enter_temp);
            textView_enter_humidity = (TextView) findViewById(R.id.enter_humidity);
            textView_enter_activity = (TextView) findViewById(R.id.enter_activity);
            textView_scrollViewOutput = (TextView) findViewById(R.id.scrollViewOutput);


            for (int i = 0; i < length; i++) {

                String string_temp, string_humidity, string_activity;

                string_temp = w.temp[i];
                string_humidity = w.humidity[i];
                string_activity = w.activity[i];


                textView_enter_temp.setText(string_temp);
                textView_enter_humidity.setText(string_humidity);
                textView_enter_activity.setText(string_activity);

            }
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            String result="";
            int output_sensor_no;
            super.onPostExecute(aVoid);
            for(int j=0;j<w.sensor_no;j++)
            {
                output_sensor_no=j+1;
                //result = result + String.valueOf("Output" + j+1 + ":\nTemperature : " + w.temp[j] + "\nHumidity : " + w.humidity[j] + "\nActivity :" + w.activity[j] + "\n");
                result = result + ("Output " + output_sensor_no+ ":\nTemperature : " + w.temp[j] + "\nHumidity : " + w.humidity[j] + "\nActivity :" + w.activity[j] + "\n");
            }
            textView_scrollViewOutput.setText(result);
        }
    }

    public void generate() {
            Random_TH_Driver_Sensor_Task task = new Random_TH_Driver_Sensor_Task();
            task.execute(new Integer[]{no_of_sensors});
    }


    public void Close(View view)
    {
        THSensor.this.finish();
    }
}

