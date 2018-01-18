package smithavenkatesh.cmpe277.com.activitylifecycle;


        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

        import smithavenkatesh.cmpe277.com.activitylifecycle.util.StatusTracker;
        import smithavenkatesh.cmpe277.com.activitylifecycle.util.Utils;

        import static android.content.ContentValues.TAG;

/**
 * Example Activity to demonstrate the lifecycle callback methods.
 */
public class ActivityB extends Activity {

    private String mActivityName;
    private TextView mStatusView;
    private TextView mStatusAllView;
    private static int threadCounter=0;
    private  int threadCounterb = 0;
    Intent returnIntent = new Intent();
    private StatusTracker mStatusTracker = StatusTracker.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
//        mActivityName = getString(R.string.activity_b_label);
//        mStatusView = (TextView)findViewById(R.id.status_view_b);
//        mStatusAllView = (TextView)findViewById(R.id.status_view_all_b);
//        mStatusTracker.setStatus(mActivityName, getString(R.string.on_create));
//        Utils.printStatus(mStatusView, mStatusAllView);
//        //threadCounter = getIntent().getIntExtra("THREAD_COUNTER",threadCounterb);
//        String msg = "Total thread counter in B is"+ String.valueOf(threadCounter);
//        Log.v(TAG,msg);
//        threadCounter = threadCounter+1;
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mStatusTracker.setStatus(mActivityName, getString(R.string.on_start));
//        Utils.printStatus(mStatusView, mStatusAllView);
//        threadCounter = threadCounter+1;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        mStatusTracker.setStatus(mActivityName, getString(R.string.on_restart));
//        Utils.printStatus(mStatusView, mStatusAllView);
//        threadCounter = threadCounter+1;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mStatusTracker.setStatus(mActivityName, getString(R.string.on_resume));
//        Utils.printStatus(mStatusView, mStatusAllView);
//        threadCounter = threadCounter+1;
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mStatusTracker.setStatus(mActivityName, getString(R.string.on_pause));
//        Utils.printStatus(mStatusView, mStatusAllView);
//        threadCounter = threadCounter+1;
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mStatusTracker.setStatus(mActivityName, getString(R.string.on_stop));
//        threadCounter = threadCounter+1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mStatusTracker.setStatus(mActivityName, getString(R.string.on_destroy));

    }

    public void finishActivityB(View v) {
        threadCounter = threadCounter+3;
//        Intent returnIntent = new Intent();
//        returnIntent.putExtra("result",threadCounter);
//        setResult(0,returnIntent);
        ActivityB.this.finish();
    }
}
