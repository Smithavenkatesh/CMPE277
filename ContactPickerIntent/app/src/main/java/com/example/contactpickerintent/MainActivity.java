package com.example.contactpickerintent;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity{

	private static final int PICK_CONTACT = 85500;
	
	private TextView textView1;
	private TextView textView2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		textView1 = (TextView) findViewById(R.id.textView1);
		textView2 = (TextView) findViewById(R.id.textView2);
	}
	
	public void pickContact(View v)
	{
//		Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
//			ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
//		startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);


		try {
			Intent phonebookIntent = new Intent("intent.action.INTERACTION_TOPMENU",ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
			phonebookIntent.putExtra("additional", "phone-multi");
			startActivityForResult(phonebookIntent, PICK_CONTACT);
			// PICK_CONTACT IS JUST AN INT HOLDING SOME NUMBER OF YOUR CHOICE

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getData(String contact, int which)
	{
		return contact.split(";")[which];
	}

	public void onActivityResult(int reqCode, int resultCode, Intent data) {
		final int URI = 0;
		final int NUMBER = 1;

		if (RESULT_OK != resultCode) return;
		Bundle contactUri = data.getExtras();
		if (null == contactUri) return;

		ArrayList<String> contacts = (ArrayList<String>)contactUri.get("result");
		Toast.makeText(getApplicationContext(), getData(contacts.get(0),NUMBER), Toast.LENGTH_SHORT).show();
	}
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//            case RESULT_PICK_CONTACT:
//                contactPicked(data);
//                break;
//            }
//
//        } else {
//        	Log.e("MainActivity", "Failed to pick contact");
//        }
//    }
//
//	private void contactPicked(Intent data) {
//		Cursor cursor = null;
//        try {
//        	String phoneNo = null ;
//        	String name = null;
//        	Uri uri = data.getData();
//        	cursor = getContentResolver().query(uri, null, null, null, null);
//        	cursor.moveToFirst();
//
//        	int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
//        	int  nameIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
//
//        	phoneNo = cursor.getString(phoneIndex);
//        	name = cursor.getString(nameIndex);
//
//        	textView1.setText(name);
//        	textView2.setText(phoneNo);
//        } catch (Exception e) {
//        	e.printStackTrace();
//        }
//
//	}
}
