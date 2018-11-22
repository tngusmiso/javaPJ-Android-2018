package cse.moblie.ducks;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cse.moblie.ducks.fragment.FragmentHome;
import cse.moblie.ducks.fragment.FragmentMyDuck;
import cse.moblie.ducks.fragment.FragmentMyPage;
import cse.moblie.ducks.fragment.FragmentSchedule;
import cse.moblie.ducks.fragment.FragmentSharing;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final int FRAG_HOME = 1;
    private final int FRAG_DUCK = 2;
    private final int FRAG_MY = 3;
    private final int FRAG_SCHD = 4;
    private final int FRAG_SHAR = 5;


    // 위젯에 대한 참조
    TextView btHome;
    TextView btMyDuck;
    TextView btMyPage;
    TextView btSchedule;
    TextView btSharing;

    private Button bt_tab1, bt_tab2;

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
        final ImageButton btMenu = findViewById(R.id.btSide);
        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout rlTopMenu = findViewById(R.id.rlTopMenu);
                if (rlTopMenu.getVisibility() == View.GONE) {
                    rlTopMenu.setVisibility(View.VISIBLE);
                    btMenu.setBackgroundResource(R.drawable.up);
                } else {
                    rlTopMenu.setVisibility(View.GONE);
                    btMenu.setBackgroundResource(R.drawable.down);
                }

            };
        });

        // 검색버튼
        Button btLogin = findViewById(R.id.btLogin);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        btHome = findViewById(R.id.btHome);
        btMyDuck = findViewById(R.id.btMyDuck);
        btMyPage = findViewById(R.id.btMypage);
        btSchedule = findViewById(R.id.btSchedule);
        btSharing = findViewById(R.id.btSharing);

        // 탭 버튼에 대한 리스너 연결
        btHome.setOnClickListener(this);
        btMyDuck.setOnClickListener(this);
        btMyPage.setOnClickListener(this);
        btSchedule.setOnClickListener(this);
        btSharing.setOnClickListener(this);

        // 임의로 액티비티 호출 시점에 어느 프레그먼트를 프레임레이아웃에 띄울 것인지를 정함
        callFragment(FRAG_HOME);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btHome :
                textColorClicked(btHome);
                textColorUnclicked(btMyDuck);
                textColorUnclicked(btMyPage);
                textColorUnclicked(btSchedule);
                textColorUnclicked(btSharing);
                callFragment(FRAG_HOME);
                break;

            case R.id.btMyDuck :
                textColorUnclicked(btHome);
                textColorClicked(btMyDuck);
                textColorUnclicked(btMyPage);
                textColorUnclicked(btSchedule);
                textColorUnclicked(btSharing);
                callFragment(FRAG_DUCK);
                break;

            case R.id.btMypage :
                textColorUnclicked(btHome);
                textColorUnclicked(btMyDuck);
                textColorClicked(btMyPage);
                textColorUnclicked(btSchedule);
                textColorUnclicked(btSharing);
                callFragment(FRAG_MY);
                break;

            case R.id.btSchedule :
                textColorUnclicked(btHome);
                textColorUnclicked(btMyDuck);
                textColorUnclicked(btMyPage);
                textColorClicked(btSchedule);
                textColorUnclicked(btSharing);
                callFragment(FRAG_SCHD);
                break;

            case R.id.btSharing :
                textColorUnclicked(btHome);
                textColorUnclicked(btMyDuck);
                textColorUnclicked(btMyPage);
                textColorUnclicked(btSchedule);
                textColorClicked(btSharing);
                callFragment(FRAG_SHAR);
                break;
        }
    }

    private void callFragment(int frament_no){

        // 프래그먼트 사용을 위해
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (frament_no){
            case 1:
                FragmentHome f1 = new FragmentHome();
                transaction.replace(R.id.flContainer, f1);
                transaction.commit();
                break;

            case 2:
                FragmentMyDuck f2 = new FragmentMyDuck();
                transaction.replace(R.id.flContainer, f2);
                transaction.commit();
                break;

            case 3:
                FragmentMyPage f3 = new FragmentMyPage();
                transaction.replace(R.id.flContainer, f3);
                transaction.commit();
                break;

            case 4:
                FragmentSchedule f4 = new FragmentSchedule();
                transaction.replace(R.id.flContainer, f4);
                transaction.commit();
                break;

            case 5:
                FragmentSharing f5 = new FragmentSharing();
                transaction.replace(R.id.flContainer, f5);
                transaction.commit();
                break;
        }

    }

    private void textColorClicked(TextView v){
        v.setTextColor(Color.parseColor("#EE8255"));
    }
    private void textColorUnclicked(TextView v){
        v.setTextColor(Color.parseColor("#999999"));
    }

}