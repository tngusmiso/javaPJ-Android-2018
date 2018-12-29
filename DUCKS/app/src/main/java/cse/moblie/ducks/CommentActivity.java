package cse.moblie.ducks;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cse.moblie.ducks.recycler.CardAdapter;
import cse.moblie.ducks.recycler.ScheduleItem;
import cse.moblie.ducks.request.GetJson;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CommentActivity extends AppCompatActivity {

    private String num, origin, duck, title, due, writer, content, writttenDate, writttenTime;
    private TextView tvDuck, tvTitle, tvDue, tvWriter, tvContent, tvWrittenDate, tvWrittenTime;
    private RecyclerView mRecycler_comment;
    private RecyclerView.LayoutManager mLayoutManager_comment;
    ArrayList<ScheduleItem> arrayList_comment = new ArrayList<>();
    private CardAdapter commentAdapter;

    private HashMap<String, String> originInfo = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        receiveIntentData();
        setOrigin();

        // 원 게시글 정보와 댓글들 불러오기
        final GetJson httpConn = GetJson.getInstance();
        new Thread() {
            public void run() {
                httpConn.requestWebServer(getOriginCallback, "getSharingBoard.php", "NUM=" + num);

                try{
                    sleep(500);
                    httpConn.requestWebServer(getCommentsCallback, "getComments.php", "ORIGIN=" + origin);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }.start();

        //리사이클러 뷰에 댓글들 적용
        mRecycler_comment = findViewById(R.id.recyclerView);
        mRecycler_comment.setHasFixedSize(true);
        mLayoutManager_comment = new LinearLayoutManager(getApplicationContext());
        mRecycler_comment.setLayoutManager(mLayoutManager_comment);
        commentAdapter = new CardAdapter(arrayList_comment, null);
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
                upload(MainActivity.getUserInfo().get("num"),etComment.getText().toString(), originInfo.get("num"));
                etComment.setText("");
            }
        });
    }

    public void upload(final String writer, final String comment, final String origin) {
        final GetJson httpConn = GetJson.getInstance();
        new Thread() {
            public void run() {
                httpConn.requestWebServer(sendCommentCallback, "addComment.php", "WRITER="+writer, "COMMENT="+comment, "ORG_B="+origin);
            }
        }.start();
    }

    public void receiveIntentData() {
        Intent intent;
        intent = getIntent();

        num = intent.getStringExtra("Num");
        origin = intent.getStringExtra("Origin");
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

    private final Callback getOriginCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("Origin", "콜백오류:" + e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body = response.body().string();
            Log.d("Origin", "성공:" + body);

            try {
                JSONArray jsonArray = new JSONArray(body);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                originInfo.put("num", jsonObject.getString("num"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    private final Callback getCommentsCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("Comments", "콜백오류:" + e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body = response.body().string();
            Log.d("Comments", "성공:" + body);

            try {
                JSONArray jsonArray = new JSONArray(body);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    final String writer = jsonObject.getString("writer");
                    final String timestamp = jsonObject.getString("cretime");
                    final String writtenDate = timestamp.split(" ")[0];
                    final String writtenTime = timestamp.split(" ")[1];
                    final String comment = jsonObject.getString("comment");
//                    final String originNum = jsonObject.getString("bno");
                    arrayList_comment.add(new ScheduleItem(2, writer, comment, writtenDate, writtenTime, null, null, null, null, null));
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            commentAdapter.notifyDataSetChanged();
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };


    private final Callback sendCommentCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("sendComment", "콜백오류:" + e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body = response.body().string();
            Log.d("sendComment", "성공:" + body);
            try {
                JSONObject jsonObject = new JSONObject(body);
                if (jsonObject.getString("result").equals("100")) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CommentActivity.this, "업로드 성공", Toast.LENGTH_SHORT).show();
                            final GetJson httpConn = GetJson.getInstance();
                            httpConn.requestWebServer(getCommentsCallback, "getComments.php", "ORIGIN=" + num);
                            commentAdapter.notifyDataSetChanged();
                        }
                    });
                } else{
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CommentActivity.this, "업로드 실패", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}

