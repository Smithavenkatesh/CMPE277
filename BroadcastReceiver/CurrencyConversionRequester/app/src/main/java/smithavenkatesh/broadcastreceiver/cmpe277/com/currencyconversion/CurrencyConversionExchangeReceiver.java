package smithavenkatesh.broadcastreceiver.cmpe277.com.currencyconversion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;




public class CurrencyConversionExchangeReceiver extends Activity
{
   private TextView Textviewdollar_Amt_inExchangeApp_txt;
   private TextView TextViewconvert_to_inExchangeApp_txt;

   private float dlr_amt;
   private String dlr_amt_string,currency_to_be_converted_string,result_string;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_conversion_exchange_receiver);

        Textviewdollar_Amt_inExchangeApp_txt = (TextView) findViewById(R.id.dollar_Amt_inExchangeApp_txt);
        dlr_amt=getIntent().getFloatExtra("dollarAmt",0);
        dlr_amt_string = Textviewdollar_Amt_inExchangeApp_txt.getText()+String.valueOf(dlr_amt);
        Textviewdollar_Amt_inExchangeApp_txt.setText(dlr_amt_string);

        TextViewconvert_to_inExchangeApp_txt = (TextView) findViewById(R.id.convert_to_inExchangeApp_txt);
       currency_to_be_converted_string=TextViewconvert_to_inExchangeApp_txt.getText()+getIntent().getStringExtra("currency_to_be_converted");
        TextViewconvert_to_inExchangeApp_txt.setText(currency_to_be_converted_string);

        result_string = getIntent().getStringExtra("Result");

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

  public void Converted(View view)
  {
      System.out.println("Result_string_ex"+result_string);
      Intent intent = new Intent(this,CurrencyConversionRequestor.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
      intent.putExtra("Result_for_Requestor",result_string);
      startActivityIfNeeded(intent, 0);
  }

  public  void  closeCurrencyConversionExchange(View view)
  {
      CurrencyConversionExchangeReceiver.this.finish();
  }
}


