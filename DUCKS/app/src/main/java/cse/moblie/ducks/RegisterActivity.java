package cse.moblie.ducks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import java.io.IOException;
import cse.moblie.ducks.request.GetJson;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etNickname = findViewById(R.id.etNickname);
        final EditText etID = findViewById(R.id.etID);
        final EditText etPwd = findViewById(R.id.etPwd);
        final EditText etPwdCheck = findViewById(R.id.etPwdCheck);
        final EditText etEmail = findViewById(R.id.etEmail);

        final GetJson json = GetJson.getInstance();
        Spinner spinner1 = findViewById(R.id.spinner1);
        Spinner spinner2 = findViewById(R.id.spinner2);
        Spinner spinner3 = findViewById(R.id.spinner3);

        Button btRegister = (Button) findViewById(R.id.btRegister);
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = etID.getText().toString();
                String pwd = etPwd.getText().toString();
                String name = etNickname.getText().toString();
                String email = etEmail.getText().toString();

                new Thread() {
                    public void run() {
                        json.requestWebServer("u_id", callback);
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


    }

    private final Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("", "콜백오류:" + e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body = response.body().string();
            Log.d("", "서버에서 응답한 Body:" + body);

        }
    };
}
