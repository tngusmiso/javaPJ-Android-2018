package cse.moblie.ducks;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import cse.moblie.ducks.R;
import cse.moblie.ducks.fragment.FragmentSharing;
import cse.moblie.ducks.recycler.CardAdapter;
import cse.moblie.ducks.request.GetJson;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AddSharingActivity extends AppCompatActivity {

    private final int MAX_TITLE = 30;
    private final int MAX_CONTENT = 300;
    Calendar dueDate = Calendar.getInstance();

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
        setContentView(R.layout.activity_addsharing);

        final String writerNum = getIntent().getStringExtra("Writer");

        final EditText etTitle = findViewById(R.id.etTitle);
        final EditText etContent = findViewById(R.id.etContent);
        etTitle.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_TITLE)});
        etContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_CONTENT)});

        final Spinner spinner1 = findViewById(R.id.spinner1);
        final Spinner spinner2 = findViewById(R.id.spinner2);
        final Spinner spinner3 = findViewById(R.id.spinner3);

        genreAdapter = new ArrayAdapter<>(AddSharingActivity.this, android.R.layout.simple_list_item_1, genreList);
        spinner1.setAdapter(genreAdapter);

        duckAdapter = new ArrayAdapter<>(AddSharingActivity.this, android.R.layout.simple_list_item_1, duckList);
        spinner2.setAdapter(duckAdapter);

        duck2Adapter = new ArrayAdapter<>(AddSharingActivity.this, android.R.layout.simple_list_item_1, duck2List);
        spinner3.setAdapter(duck2Adapter);

        Button btCancel = findViewById(R.id.btCancel);
        Button btDone = findViewById(R.id.btDone);
        final Button btDueDate = findViewById(R.id.btDueDate);

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSharingActivity.this, MainActivity.class);
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

        btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etTitle.getText().toString()==null||etTitle.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddSharingActivity.this);
                    builder.setTitle("제목없음")
                            .setMessage("제목을 입력해주세요.")
                            .setNegativeButton("확인", null)
                            .show();
                    return;
                }

                if(spinner2.getSelectedItemPosition()==0&&spinner3.getSelectedItemPosition()==0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddSharingActivity.this);
                    builder.setTitle("분류 선택")
                            .setMessage("장르와 분류를 선택해주세요.")
                            .setNegativeButton("확인", null)
                            .show();
                    return;
                }

                if (btDueDate.getText().equals("마감일 선택")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddSharingActivity.this);
                    builder.setTitle("마감일")
                            .setMessage("마감일을 선택해주세요.")
                            .setNegativeButton("확인", null)
                            .show();
                    return;
                }

                if (etContent.getText().toString()==null||etContent.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddSharingActivity.this);
                    builder.setTitle("내용 없음")
                        .setMessage("내용이 없습니다. 그대로 업로드 하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Upload(writerNum, (spinner3.getSelectedItemPosition()==0)?duckMap.get(spinner2.getSelectedItem()+""):duck2Map.get(spinner3.getSelectedItem()+"")
                                        , etTitle.getText().toString(), etContent.getText().toString(), btDueDate.getText().toString());
                            }
                        })
                        .setNegativeButton("취소", null )
                        .show();
                    return;
                }

                else Upload(writerNum, (spinner3.getSelectedItemPosition()==0)?duckMap.get(spinner2.getSelectedItem()+""):duck2Map.get(spinner3.getSelectedItem()+"")
                        , etTitle.getText().toString(), etContent.getText().toString(), btDueDate.getText().toString());
            }
        });

        btDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int date = c.get(Calendar.DATE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddSharingActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        btDueDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                        dueDate.set(year, month, dayOfMonth);

                    }
                }, year, month, date);
                datePickerDialog.show();
            }
        });

        final GetJson httpConn = GetJson.getInstance();
        // 장르 스피너 불러오기
        new Thread() {
            public void run() {
                httpConn.requestWebServer(genreCallback, "getGenre.php");
            }
        }.start();

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
    }

    private void Upload(final String... param2){
        final GetJson httpConn = GetJson.getInstance();
        new Thread() {
            public void run() {

                String[] param1 = {"WRITER=", "DUCK=", "TITLE=", "CONTENT=", "DUE="};
                List<String> list = new ArrayList<>();

                for (int i = 0; i < param1.length; i++)
                    if (!param2[i].equals(""))
                        list.add(param1[i] + param2[i]);

                param1 = list.toArray(new String[list.size()]);

                httpConn.requestWebServer(callback, "addSharing.php", param1);
            }
        }.start();

    }

    private final Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("AddSharing", "콜백오류:" + e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body = response.body().string();
            Log.d("AddSharing", "서버에서 응답한 Body:" + body);
            try {
                JSONObject jsonObject = new JSONObject(body);
                if (jsonObject.getString("result").equals("100")) {
                    Handler handler = new Handler(Looper.getMainLooper());

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddSharingActivity.this, "업로드 성공", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(AddSharingActivity.this, MainActivity.class);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    });
                } else{
                    Handler handler = new Handler(Looper.getMainLooper());

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddSharingActivity.this, "업로드 실패", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
}
