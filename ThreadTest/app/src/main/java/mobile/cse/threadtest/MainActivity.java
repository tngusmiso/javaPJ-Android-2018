package mobile.cse.threadtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static  final String TAG = "ThreadUITest";
    boolean mRunning = false;

    TextView mTVCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTVCount = findViewById(R.id.tvCount);
//        Thread thCounter = new Thread(new CounterThread());
//
//        mRunning = true;
//        thCounter.start();
    }

    @Override
    protected void onStart(){
        super.onStart();

        Thread thCounter = new Thread(new CounterThread());

        mRunning = true;
        thCounter.start();
    }

    @Override
    protected void onStop(){
        super.onStop();
        mRunning = false;
    }

    private  class CounterThread implements  Runnable{
        @Override
        public void run(){
            for(int i = 0; i<10 && mRunning; i++){
                Log.i(TAG,"["+Thread.currentThread().getName()+"] Count: "+i);
                //mTVCount.setText("["+Thread.currentThread().getName()+"] Count: "+i);
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
