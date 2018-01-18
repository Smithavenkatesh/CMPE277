package smithavenkatesh.broadcastreceiver.cmpe277.com.currencyconversion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class CurrencyConversionRequestor extends Activity{

    private Spinner spinner_currency_to_convert;
    private EditText editText_dollar_amt;
    private TextView textViewresults;
    private Button buttonConvert;

    //to store the float value
    private float dollar_amt;
    private String to_convert_to_currency,dollar_amt_string,result_str=null;
    private String flag="False";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_coversion_requestor);

        buttonConvert = (Button)findViewById(R.id.Convert);
        editText_dollar_amt = (EditText)findViewById(R.id.enter_dollar_amt);

        //Handling empty editText_dollar_amt
        buttonConvert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dollar_amt_string = editText_dollar_amt.getText().toString();
                if (TextUtils.isEmpty(dollar_amt_string)) {
                    Toast.makeText(CurrencyConversionRequestor.this, "Enter all the fields", Toast.LENGTH_LONG).show();
                }
                else
                    sendBroadcastForConversion();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        //Bundle extras = intent.getExtras();
        if (flag != "False") {
            textViewresults = (TextView) findViewById(R.id.results);
            result_str = intent.getStringExtra("Result_for_Requestor");
            textViewresults.setText(result_str);
        }
    }

    // broadcast a custom intent.
    public void sendBroadcastForConversion(){
        flag="True";

        //Intent currencyRequestorIntent = getPackageManager().getLaunchIntentForPackage("smithavenkatesh.currencyexchange.broadcastreceiver.cmpe277.com.currencyconversionexchangereceiver.Receiver.class");
        Intent intent=new Intent(this,Receiver.class);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

        //to get user entered dollar amount and to which currency to be converted
        editText_dollar_amt = (EditText)findViewById(R.id.enter_dollar_amt);
        dollar_amt = Float.valueOf(editText_dollar_amt.getText().toString());
        dollar_amt_string = editText_dollar_amt.getText().toString();

        spinner_currency_to_convert = (Spinner)findViewById(R.id.choose_currency);
        to_convert_to_currency = spinner_currency_to_convert.getSelectedItem().toString();



        intent.setAction("Receiver.CUSTOM_INTENT");
        intent.putExtra("DLR_AMNT_EXTRA",dollar_amt);
        intent.putExtra("CONVERT_CURRENCY_EXTRA", to_convert_to_currency);

    sendBroadcast(intent);
    }

    public  void closeCurrencyConvertorApp(View view){

        CurrencyConversionRequestor.this.finish();
    }

}
