package smithavenkatesh.cmpe277.com.activitylifecycle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;


import smithavenkatesh.cmpe277.com.activitylifecycle.util.StatusTracker;
import smithavenkatesh.cmpe277.com.activitylifecycle.util.Utils;

import static android.content.ContentValues.TAG;

public class ActivityA extends Activity {

    private String mActivityName;
    private TextView mStatusView;
    private TextView mStatusAllView;
    private  int threadCounter=0;
    private TextView threadCounterView;
    private StatusTracker mStatusTracker = StatusTracker.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);


//        mActivityName = getString(R.string.activity_a);
//        mStatusView = (TextView)findViewById(R.id.status_view_a);
//        mStatusAllView = (TextView)findViewById(R.id.status_view_all_a);
//        mStatusTracker.setStatus(mActivityName, getString(R.string.on_create));
//        Utils.printStatus(mStatusView, mStatusAllView);

        threadCounter = threadCounter+1;
        threadCounterView = (TextView)findViewById(R.id.threadCounter);
        String vi = String.format("%04d",threadCounter);
        threadCounterView.setText("Thread Counter: "+vi);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mStatusTracker.setStatus(mActivityName, getString(R.string.on_start));
//        Utils.printStatus(mStatusView, mStatusAllView);
//        threadCounter = threadCounter+1;
//        threadCounterView = (TextView)findViewById(R.id.threadCounter);
//        threadCounterView.setText("Thread Counter: "+threadCounter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        mStatusTracker.setStatus(mActivityName, getString(R.string.on_restart));
//        Utils.printStatus(mStatusView, mStatusAllView);
       //threadCounter = threadCounter+getIntent().getIntExtra("result",threadCounter);
        threadCounter = threadCounter+1;
        threadCounterView = (TextView)findViewById(R.id.threadCounter);
        String vi = String.format("%04d",threadCounter);
        threadCounterView.setText("Thread Counter: "+vi);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mStatusTracker.setStatus(mActivityName, getString(R.string.on_resume));
//        Utils.printStatus(mStatusView, mStatusAllView);
//        threadCounter = threadCounter+1;
//        threadCounterView = (TextView)findViewById(R.id.threadCounter);
//        threadCounterView.setText("Thread Counter: "+threadCounter);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mStatusTracker.setStatus(mActivityName, getString(R.string.on_pause));
//        Utils.printStatus(mStatusView, mStatusAllView);
//        threadCounter = threadCounter+1;
//        threadCounterView = (TextView)findViewById(R.id.threadCounter);
//        threadCounterView.setText("Thread Counter: "+threadCounter);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mStatusTracker.setStatus(mActivityName, getString(R.string.on_stop));
//        threadCounter = threadCounter+1;
//        threadCounterView = (TextView)findViewById(R.id.threadCounter);
//        threadCounterView.setText("Thread Counter: "+threadCounter);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case 0:
//                if (resultCode == 0) {
//                    threadCounter = threadCounter+data.getIntExtra("result",0);
//                    threadCounterView.setText("Thread Counter: "+threadCounter);
//                }
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mStatusTracker.setStatus(mActivityName, getString(R.string.on_destroy));
//        mStatusTracker.clear();

    }

    public void startActivityB(View v) {
        Intent intent = new Intent(ActivityA.this, ActivityB.class);
//        intent.putExtra("THREAD_COUNTER",threadCounter);
        startActivityForResult(intent,0);
    }

    public  void startDialog(View v)
    {
        Intent intent = new Intent(ActivityA.this, DialogActivity.class);
        startActivity(intent);
    }

    public void finishActivityA(View v)
    {
        threadCounter = 0;
        ActivityA.this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onDestroy();
    }
}
