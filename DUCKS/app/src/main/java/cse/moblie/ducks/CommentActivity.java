package cse.moblie.ducks;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import cse.moblie.ducks.recycler.CardAdapter;

public class CommentActivity extends AppCompatActivity {

    private String duck, title, due, writer, content, writttenDate, writttenTime;
    private TextView tvDuck, tvTitle, tvDue, tvWriter, tvContent, tvWrittenDate, tvWrittenTime;
    private View header;
    private static ListView commentListView;
    private static String cnum, id;
    private static ArrayAdapter cardAdapter;
    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        init();
        receiveIntentData();
        setOrigin();
    }

    public void init() {
//        context = getApplicationContext();
//        header = getLayoutInflater().inflate(R.layout.header_notice, null);
//        commentListView = (ListView) findViewById(R.id.recyclerView);
//        commentListView.addHeaderView(header);
//        tv_title = (TextView) header.findViewById(R.id.tv_title);
//        tv_writer = (TextView) header.findViewById(R.id.tv_writer);
//        tv_date = (TextView) header.findViewById(R.id.tv_date);
//        tv_count = (TextView) header.findViewById(R.id.tv_count);
//        tv_content = (TextView) header.findViewById(R.id.tv_content);
//        iv_img1 = (ImageView) header.findViewById(R.id.iv_img1);
//        iv_img2 = (ImageView) header.findViewById(R.id.iv_img2);
//        iv_img3 = (ImageView) header.findViewById(R.id.iv_img3);
    }

    public void receiveIntentData() {
        Intent intent;
        intent = getIntent();
        title = intent.getStringExtra("Title");
        writer = "작성자 : "+intent.getStringExtra("Writer");
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

