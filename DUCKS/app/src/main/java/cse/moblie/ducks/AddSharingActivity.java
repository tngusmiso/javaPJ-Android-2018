package cse.moblie.ducks;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.Calendar;

import cse.moblie.ducks.R;
import cse.moblie.ducks.fragment.FragmentSharing;

public class AddSharingActivity extends AppCompatActivity {

    private final int MAX_TITLE = 30;
    private final int MAX_CONTENT = 300;
    Calendar dueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsharing);

        Button btCancel = findViewById(R.id.btCancel);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSharingActivity.this,MainActivity.class);
                setResult(RESULT_CANCELED,intent);
                finish();
            }
        });
        Button btDone = findViewById(R.id.btDone);
        btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSharingActivity.this,MainActivity.class);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        EditText etTitle = findViewById(R.id.etTitle);
        etTitle.setFilters(new InputFilter[] { new InputFilter.LengthFilter(MAX_TITLE) });

        EditText etContent = findViewById(R.id.etContent);
        etContent.setFilters(new InputFilter[] { new InputFilter.LengthFilter(MAX_CONTENT) });

        final Button btDueDate = findViewById(R.id.btDueDate);
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
                        btDueDate.setText(year+"년 "+(month+1)+"월"+dayOfMonth+"일");
                        dueDate.set(year,month,dayOfMonth);
                    }
                },year,month,date);
                datePickerDialog.show();
            }
        });
    }
}
