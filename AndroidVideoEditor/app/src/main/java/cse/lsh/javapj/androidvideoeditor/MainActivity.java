package cse.lsh.javapj.androidvideoeditor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void goToWorkingSpace(View v){
        Intent intent = new Intent(MainActivity.this, WorkingSpace.class);
        startActivity(intent);
        finish();   //다음 액티비티로 넘어가면 현재 액티비티 소멸
    }
}
