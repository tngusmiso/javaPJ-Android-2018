package cse.moblie.ducks;

import android.support.v7.app.AlertDialog;
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
import java.util.ArrayList;
import java.util.List;

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

        final GetJson httpConn = GetJson.getInstance();
        Spinner spinner1 = findViewById(R.id.spinner1);
        Spinner spinner2 = findViewById(R.id.spinner2);
        Spinner spinner3 = findViewById(R.id.spinner3);


        Button btRegister = (Button) findViewById(R.id.btRegister);
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String pwd = etPwd.getText().toString();
                final String pwdCh = etPwdCheck.getText().toString();
                if(pwd!=pwdCh){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("비밀번호 확인")
                            .setMessage("비밀번호가 다릅니다. 비밀번호를 다시 확인해주세요.")
                            .setNegativeButton("확인",null)
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

                        for(int i = 0; i<param1.length;i++)
                            if(!param2[i].equals(""))
                                list.add(param1[i]+param2[i]);

                        param1 = list.toArray(new String[list.size()]);

                        httpConn.requestWebServer(callback, "register.php",param1);
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
            Log.d("Register", "콜백오류:" + e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body = response.body().string();
            Log.d("Register", "서버에서 응답한 Body:" + body);

        }
    };
}
