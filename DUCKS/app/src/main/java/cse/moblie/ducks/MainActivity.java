package cse.moblie.ducks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 상단앱바 설정
        RelativeLayout topBar = findViewById(R.id.rlTopBar);
        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(R.layout.top_bar, topBar);

        // 상단메뉴 설정
        RelativeLayout menuCont = findViewById(R.id.rlTopMenu);
        inflater.inflate(R.layout.top_menu, menuCont);

        // 메뉴버튼
        Button btMenu = findViewById(R.id.btSide);
        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout rlTopMenu = findViewById(R.id.rlTopMenu);
                if (rlTopMenu.getVisibility() == View.GONE) {
                    rlTopMenu.setVisibility(View.VISIBLE);
                } else rlTopMenu.setVisibility(View.GONE);
            };
        });

        // 검색버튼
        Button btSearch = findViewById(R.id.btSearch);
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Search", Toast.LENGTH_LONG).show();
            }
        });


        // 마이덕
        Button btMyDuck = findViewById(R.id.btMyDuck);
        btMyDuck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyDuck.class);
                startActivity(intent);
            }
        });
        // 마이페이지
        Button btMyPage = findViewById(R.id.btMypage);
        btMyPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyPage.class);
                startActivity(intent);
            }
        });
        // 스케줄
        Button btSchedule = findViewById(R.id.btSchedule);
        btSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Schedule.class);
                startActivity(intent);
            }
        });
        // 양도 나눔
        Button btNanum = findViewById(R.id.btNanum);
        btNanum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Sharing.class);
                startActivity(intent);
            }
        });

    }
}