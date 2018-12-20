package cse.moblie.ducks;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cse.moblie.ducks.request.GetJson;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    boolean idCheck = false;

    ArrayList<String> genreList = new ArrayList<>();
    ArrayList<String> duckList = new ArrayList<>();
    ArrayList<String> duck2List = new ArrayList<>();

    ArrayAdapter<String> genreAdapter;
    ArrayAdapter<String> duckAdapter;
    ArrayAdapter<String> duck2Adapter;

    HashMap<String, String> genreMap = new HashMap<>();
    HashMap<String, String> duckMap = new HashMap<>();
    HashMap<String, String> duck2Map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etNickname = findViewById(R.id.etNickname);
        final EditText etID = findViewById(R.id.etID);
        final EditText etPwd = findViewById(R.id.etPwd);
        final EditText etPwdCheck = findViewById(R.id.etPwdCheck);
        final EditText etEmail = findViewById(R.id.etEmail);

        final GetJson httpConn = GetJson.getInstance();
        final Spinner spinner1 = findViewById(R.id.spinner1);
        final Spinner spinner2 = findViewById(R.id.spinner2);
        final Spinner spinner3 = findViewById(R.id.spinner3);

        genreAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_list_item_1, genreList);
        spinner1.setAdapter(genreAdapter);

        duckAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_list_item_1, duckList);
        spinner2.setAdapter(duckAdapter);

        duck2Adapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_list_item_1, duck2List);
        spinner3.setAdapter(duck2Adapter);


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String genreNum = genreMap.get(spinner1.getItemAtPosition(position));
                duckList.clear();
                duck2List.clear();
                duckList.add("선택안함");
                duck2List.add("선택안함");
                new Thread() {
                    public void run() {
                        httpConn.requestWebServer(duckCallback, "getDuck.php", "GENRE=" + genreNum);
                    }
                }.start();
                spinner2.setSelection(0);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String duckNum = duckMap.get(spinner2.getItemAtPosition(position));
                duck2List.clear();
                duck2List.add("선택안함");
                new Thread() {
                    public void run() {
                        httpConn.requestWebServer(duck2Callback, "getDuck.php", "PARENT=" + duckNum);
                    }
                }.start();
                spinner3.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        
        Button btRegister = (Button) findViewById(R.id.btRegister);
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(!idCheck){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
//                    builder.setTitle("ID 확인")
//                            .setMessage("ID 중복 확인을 먼저 해주세요.")
//                            .setNegativeButton("확인",null)
//                            .show();
//                    return;
//                }
                final String pwd = etPwd.getText().toString();
                final String pwdCh = etPwdCheck.getText().toString();

                if (!pwd.equals(pwdCh)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("비밀번호 확인")
                            .setMessage("비밀번호가 다릅니다. 비밀번호를 다시 확인해주세요.")
                            .setNegativeButton("확인", null)
                            .show();
                    return;
                }

                if(spinner2.getSelectedItemPosition()==0&&spinner3.getSelectedItemPosition()==0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("MY DUCK")
                            .setMessage("마이덕을 선택해주세요.")
                            .setNegativeButton("확인", null)
                            .show();
                    return;
                }

                final String id = etID.getText().toString();
                final String name = etNickname.getText().toString();
                final String email = etEmail.getText().toString();
                final String type = "";
                final String duck = "";
                final String inter1 = "";
                final String inter2 = "";
                final String inter3 = "";

                new Thread() {
                    public void run() {
                        String[] param1 = {"ID=", "PWD=", "NAME=", "EMAIL=", "TYPE=", "DUCK=", "I1=", "I2=", "I3="};
                        String[] param2 = {id, pwd, name, email, type, duck, inter1, inter2, inter3};
                        List<String> list = new ArrayList<>();

                        for (int i = 0; i < param1.length; i++)
                            if (!param2[i].equals(""))
                                list.add(param1[i] + param2[i]);

                        param1 = list.toArray(new String[list.size()]);

                        httpConn.requestWebServer(callback, "register.php", param1);
                    }
                }.start();
            }
        });


        final ImageButton btInterest1 = findViewById(R.id.ibInter1);
        final ImageButton btInterest2 = findViewById(R.id.ibInter2);
        final ImageButton btInterest3 = findViewById(R.id.ibInter3);

        btInterest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btInterest2.setVisibility(View.VISIBLE);
            }
        });
        btInterest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btInterest3.setVisibility(View.VISIBLE);
            }
        });


        // 장르 스피너 불러오기
        new Thread() {
            public void run() {
                httpConn.requestWebServer(genreCallback, "getGenre.php");
            }
        }.start();

    }

    private final Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("Register", "콜백오류:" + e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body = response.body().string();
            Log.d("Register", "서버에서 응답한 Body:" + body);

        }
    };

    private final Callback genreCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("Genre", "콜백오류:" + e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body = response.body().string();
            Log.d("Genre", "성공:" + body);

            try {
                JSONArray jsonArray = new JSONArray(body);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    genreList.add(jsonObject.getString("genre"));
                    genreMap.put(jsonObject.getString("genre"), jsonObject.getString("num"));
                }
                Handler handler = new Handler(Looper.getMainLooper());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        genreAdapter.notifyDataSetChanged();
                        duckAdapter.notifyDataSetChanged();
                        duck2Adapter.notifyDataSetChanged();
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    };

    private final Callback duckCallback = new Callback() {
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
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    duckList.add(jsonObject.getString("name"));
                    duckMap.put(jsonObject.getString("name"), jsonObject.getString("num"));
                }

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        duckAdapter.notifyDataSetChanged();
                        duck2Adapter.notifyDataSetChanged();
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private final Callback duck2Callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("Duck2", "콜백오류:" + e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body = response.body().string();
            Log.d("Duck2", "성공:" + body);

            try {
                JSONArray jsonArray = new JSONArray(body);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    duck2List.add(jsonObject.getString("name"));
                    duck2Map.put(jsonObject.getString("name"), jsonObject.getString("num"));
                }

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        duck2Adapter.notifyDataSetChanged();
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    };
//    private final Callback idCallback = new Callback() {
//        @Override
//        public void onFailure(Call call, IOException e) {
//            Log.d("id check", "콜백오류:" + e.getMessage());
//        }
//
//        @Override
//        public void onResponse(Call call, Response response) throws IOException {
//            String body = response.body().string();
//            Log.d("id check", "서버에서 응답한 Body:" + body);
//        }
//    };
}
