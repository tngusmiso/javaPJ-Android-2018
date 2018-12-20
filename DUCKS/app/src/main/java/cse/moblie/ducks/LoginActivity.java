package cse.moblie.ducks;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cse.moblie.ducks.request.GetJson;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    String TAG = "LOGIN";
    EditText etId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final GetJson httpConn = GetJson.getInstance();

        etId = findViewById(R.id.etID);
        final EditText etPwd = findViewById(R.id.etPwd);

        Button btRegister = findViewById(R.id.btRegister);
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        Button btLogin = findViewById(R.id.btLogin);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread() {
                    public void run() {

                        String[] param1 = {"ID=", "PWD="};
                        String[] param2 = {etId.getText().toString(), etPwd.getText().toString()};
                        List<String> list = new ArrayList<>();

                        for(int i = 0; i<param1.length;i++)
                            if(!param2[i].equals(""))
                                list.add(param1[i]+param2[i]);


                        param1 = list.toArray(new String[list.size()]);

                        httpConn.requestWebServer(callback, "login.php", param1);
                    }
                }.start();

            }
        });

    }

    private final Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d(TAG, "콜백오류:" + e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body = response.body().string();
            Log.d(TAG, "서버에서 응답한 Body:" + body);
            try {
                JSONObject jsonObject = new JSONObject(body);
                if(jsonObject.getString("result").equals("true"));

                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("loginID",etId.getText().toString());

                finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}