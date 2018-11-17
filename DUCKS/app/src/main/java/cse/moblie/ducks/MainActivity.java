package cse.moblie.ducks;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final int FRAG_HOME = 1;
    private final int FRAG_DUCK = 2;
    private final int FRAG_MY = 3;
    private final int FRAG_SCHD = 4;
    private final int FRAG_SHAR = 5;

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

        // 위젯에 대한 참조
        Button btHome = findViewById(R.id.btHome);
        Button btMyDuck = findViewById(R.id.btMyDuck);
        Button btMyPage = findViewById(R.id.btMypage);
        Button btSchedule = findViewById(R.id.btSchedule);
        Button btSharing = findViewById(R.id.btSharing);

        // 탭 버튼에 대한 리스너 연결
        btHome.setOnClickListener(this);
        btMyDuck.setOnClickListener(this);
        btMyPage.setOnClickListener(this);
        btSchedule.setOnClickListener(this);
        btSharing.setOnClickListener(this);

        // 임의로 액티비티 호출 시점에 어느 프레그먼트를 프레임레이아웃에 띄울 것인지를 정함
        callFragment(FRAG_MY);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btHome :
                Toast.makeText(getApplicationContext(),"HOME",Toast.LENGTH_LONG).show();
                callFragment(FRAG_HOME);
                break;

            case R.id.btMyDuck :
                callFragment(FRAG_DUCK);
                break;

            case R.id.btMypage :
                callFragment(FRAG_MY);
                break;

            case R.id.btSchedule :
                callFragment(FRAG_SCHD);
                break;

            case R.id.btSharing :
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

}