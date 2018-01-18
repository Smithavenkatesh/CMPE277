package smithavenkatesh.broadcastreceiver.cmpe277.com.currencyconversion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Receiver extends BroadcastReceiver {

    public float dollar_amt;
    public   float result_amt;
    public   String currency_to_be_converted,result_string;


    @Override
    public void onReceive(Context context, Intent intent) {

            Bundle extras = intent.getExtras();

            dollar_amt= (Float) extras.get("DLR_AMNT_EXTRA");
            currency_to_be_converted = (String) extras.get("CONVERT_CURRENCY_EXTRA");
            System.out.println("currency_to_be_converted is"+ currency_to_be_converted);



            if(currency_to_be_converted.equals("Euro"))
            {
                result_amt = (float) (dollar_amt*0.84);
                System.out.println(result_amt);
            }

            else if(currency_to_be_converted.equals("Indian Rupee"))
            {
                result_amt = (float) (dollar_amt*64.91);
            }
            else if(currency_to_be_converted.equals("British Pound"))
            {
                result_amt = (float) (dollar_amt*0.74);
            }
            else
                result_amt =0;


            result_string = "Dollar amount $"+dollar_amt+" converted to "+result_amt+" "+currency_to_be_converted;

            Intent i = new Intent(context,CurrencyConversionExchangeReceiver.class);
            i.putExtra("dollarAmt",dollar_amt);
            i.putExtra("currency_to_be_converted",currency_to_be_converted);
            i.putExtra("Result",result_string);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(i);

        }
    }
