package cse.moblie.ducks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText etNickname = findViewById(R.id.etNickname);
        EditText etID = findViewById(R.id.etID);
        EditText etPwd = findViewById(R.id.etPwd);
        EditText etPwdCheck = findViewById(R.id.etPwdCheck);
        EditText etEmail = findViewById(R.id.etEmail);
        Spinner spinner1 = findViewById(R.id.spinner1);
        Spinner spinner2 = findViewById(R.id.spinner2);
        Spinner spinner3 = findViewById(R.id.spinner3);

        Button btRegister = findViewById(R.id.btRegister);
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"가입되었습니다.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
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
}
