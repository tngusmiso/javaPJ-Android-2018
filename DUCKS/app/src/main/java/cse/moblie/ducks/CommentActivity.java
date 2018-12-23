package cse.moblie.ducks;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cse.moblie.ducks.recycler.CardAdapter;
import cse.moblie.ducks.recycler.ScheduleItem;

public class CommentActivity extends AppCompatActivity {

    private String duck, title, due, writer, content, writttenDate, writttenTime;
    private TextView tvDuck, tvTitle, tvDue, tvWriter, tvContent, tvWrittenDate, tvWrittenTime;
    private RecyclerView mRecycler_comment;
    private RecyclerView.LayoutManager mLayoutManager_comment;
    ArrayList<ScheduleItem> arrayList_comment = new ArrayList<>();
    private CardAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        receiveIntentData();
        setOrigin();

        mRecycler_comment = findViewById(R.id.recyclerView);
        mRecycler_comment.setHasFixedSize(true);

        mLayoutManager_comment = new LinearLayoutManager(getApplicationContext());
        //((LinearLayoutManager) mLayoutManager_schedule).setOrientation(LinearLayout.HORIZONTAL);
        mRecycler_comment.setLayoutManager(mLayoutManager_comment);

        commentAdapter = new CardAdapter(arrayList_comment,null);

        mRecycler_comment.setAdapter(commentAdapter);

        commentAdapter.notifyDataSetChanged();
        Button btBack = findViewById(R.id.btBack);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        final EditText etComment = findViewById(R.id.etComment);
        Button btSend = findViewById(R.id.btSend);
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList_comment.add(new ScheduleItem(2,MainActivity.getUserInfo().get("name"),etComment.getText().toString(),"날짜","시간",null,null,null,null));
                commentAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),"추가되었습니다.", Toast.LENGTH_LONG).show();
                etComment.setText("");
            }
        });
    }

    public void receiveIntentData() {
        Intent intent;
        intent = getIntent();
        title = intent.getStringExtra("Title");
        writer = "작성자 : " + intent.getStringExtra("Writer");
        duck = intent.getStringExtra("Duck");
        content = intent.getStringExtra("Content");
        due = "마감 : " + intent.getStringExtra("Due");
        writttenDate = intent.getStringExtra("WrittenDate");
        writttenTime = intent.getStringExtra("WrittenTime");
    }

    public void setOrigin() {
        tvDuck = findViewById(R.id.tvWhichDuck);
        tvTitle = findViewById(R.id.tvEachTitle);
        tvDue = findViewById(R.id.tvDueTime);
        tvContent = findViewById(R.id.tvEachContent);
        tvWriter = findViewById(R.id.tvWriter);
        tvWrittenDate = findViewById(R.id.tvWrittenDate);
        tvWrittenTime = findViewById(R.id.tvWrittenTime);

        tvDuck.setText(duck);
        tvTitle.setText(title);
        tvDue.setText(due);
        tvContent.setText(content);
        tvWriter.setText(writer);
        tvWrittenDate.setText(writttenDate);
        tvWrittenTime.setText(writttenTime);
    }

}

