package cse.moblie.ducks;

import android.content.Intent;
import android.graphics.Color;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import cse.moblie.ducks.fragment.FragmentHome;
import cse.moblie.ducks.fragment.FragmentMyDuck;
import cse.moblie.ducks.fragment.FragmentMyPage;
import cse.moblie.ducks.fragment.FragmentSchedule;
import cse.moblie.ducks.fragment.FragmentSharing;
import cse.moblie.ducks.recycler.CardAdapter;
import cse.moblie.ducks.recycler.ScheduleItem;
import cse.moblie.ducks.request.GetJson;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final int FRAG_HOME = 1;
    private final int FRAG_DUCK = 2;
    private final int FRAG_MY = 3;
    private final int FRAG_SCHD = 4;
    private final int FRAG_SHAR = 5;

    public final int REQUEST_ADDSHR = 1;

    private static String loginID = "";

    // 위젯에 대한 참조
    TextView btHome;
    TextView btMyDuck;
    TextView btMyPage;
    TextView btSchedule;
    TextView btSharing;

    private Button bt_tab1, bt_tab2;

    private static HashMap<String, String> userInfo = new HashMap<>();
    private static HashMap<String, String> duckInfo = new HashMap<>();
    private static HashMap<String, String>[] interestInfo = new HashMap[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        loginID = getIntent().getStringExtra("loginID");
        final GetJson httpConn = GetJson.getInstance();
        new Thread() {
            public void run() {
                httpConn.requestWebServer(getInfoCallback, "getMyInfo.php", "ID=" + loginID);
            }
        }.start();

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
            }

            ;
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

        Button btLogout = findViewById(R.id.btLogout);
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // 임의로 액티비티 호출 시점에 어느 프레그먼트를 프레임레이아웃에 띄울 것인지를 정함
        callFragment(FRAG_HOME);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btHome:
                textColorClicked(btHome);
                textColorUnclicked(btMyDuck);
                textColorUnclicked(btMyPage);
                textColorUnclicked(btSchedule);
                textColorUnclicked(btSharing);
                callFragment(FRAG_HOME);
                break;

            case R.id.btMyDuck:
                textColorUnclicked(btHome);
                textColorClicked(btMyDuck);
                textColorUnclicked(btMyPage);
                textColorUnclicked(btSchedule);
                textColorUnclicked(btSharing);
                callFragment(FRAG_DUCK);
                break;

            case R.id.btMypage:
                textColorUnclicked(btHome);
                textColorUnclicked(btMyDuck);
                textColorClicked(btMyPage);
                textColorUnclicked(btSchedule);
                textColorUnclicked(btSharing);
                callFragment(FRAG_MY);
                break;

            case R.id.btSchedule:
                textColorUnclicked(btHome);
                textColorUnclicked(btMyDuck);
                textColorUnclicked(btMyPage);
                textColorClicked(btSchedule);
                textColorUnclicked(btSharing);
                callFragment(FRAG_SCHD);
                break;

            case R.id.btSharing:
                textColorUnclicked(btHome);
                textColorUnclicked(btMyDuck);
                textColorUnclicked(btMyPage);
                textColorUnclicked(btSchedule);
                textColorClicked(btSharing);
                callFragment(FRAG_SHAR);
                break;
        }
    }

    private void callFragment(int frament_no) {

        // 프래그먼트 사용을 위해
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (frament_no) {
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

    private void textColorClicked(TextView v) {
        v.setTextColor(Color.parseColor("#EE8255"));
    }

    private void textColorUnclicked(TextView v) {
        v.setTextColor(Color.parseColor("#999999"));
    }

    public static String getLoginID() {
        return loginID;
    }

    public static HashMap<String,String> getUserInfo(){
        return userInfo;
    }

    public static HashMap<String,String> getDuckInfo(){
        return duckInfo;
    }

    public static HashMap<String,String>[] getInterestInfo(){
        return interestInfo;
    }

    public static ArrayList<ScheduleItem> getArrayList_schedule(){
        return getArrayList_schedule();
    }

    private final Callback getInfoCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("Genre", "콜백오류:" + e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body = response.body().string();
            Log.d("MyInfo", "성공:" + body);

            try {
                JSONArray jsonArray = new JSONArray(body);
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                userInfo.put("num", jsonObject.getString("num"));
                userInfo.put("name", jsonObject.getString("name"));
                userInfo.put("duck", jsonObject.getString("duck"));
                userInfo.put("itrst1", jsonObject.getString("itrst1"));
                userInfo.put("itrst2", jsonObject.getString("itrst2"));
                userInfo.put("itrst3", jsonObject.getString("itrst3"));

                Handler handler = new Handler(Looper.getMainLooper());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        final GetJson httpConn = GetJson.getInstance();

                        String param = "";
                        for (int i = 1; i < 4; i++) {
                            param += "GENRE" + i + "=" + userInfo.get("itrst" + i) + "&";
                        }
                        httpConn.requestWebServer(getDuckCallback, "getDuckInfo.php", "NUM=" + userInfo.get("duck"));
                        httpConn.requestWebServer(getGenreCallback, "getGenre.php", param);
                        Toast.makeText(MainActivity.this,userInfo.get("name")+"님 환영합니다.",Toast.LENGTH_LONG).show();
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private final Callback getDuckCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("Duck", "콜백오류:" + e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body = response.body().string();
            Log.d("Duck", "성공:" + body);

            try {
                JSONArray jsonArray = new JSONArray(body);
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                duckInfo.put("num", jsonObject.getString("num"));
                duckInfo.put("name", jsonObject.getString("name"));
                duckInfo.put("follower", jsonObject.getString("follower"));
                duckInfo.put("link", jsonObject.getString("link"));
                duckInfo.put("type", jsonObject.getString("type"));
                duckInfo.put("parent", jsonObject.getString("parent"));
                duckInfo.put("pic", jsonObject.getString("pic"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private final Callback getGenreCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("Interest", "콜백오류:" + e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body = response.body().string();
            Log.d("Interest", "성공:" + body);

            try {
                JSONArray jsonArray = new JSONArray(body);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    interestInfo[i] = new HashMap<>();
                    interestInfo[i].put("num", jsonObject.getString("num"));
                    interestInfo[i].put("genre", jsonObject.getString("genre"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}