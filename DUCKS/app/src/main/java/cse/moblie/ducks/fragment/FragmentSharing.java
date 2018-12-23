package cse.moblie.ducks.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import cse.moblie.ducks.AddSharingActivity;
import cse.moblie.ducks.CommentActivity;
import cse.moblie.ducks.LoginActivity;
import cse.moblie.ducks.MainActivity;
import cse.moblie.ducks.R;
import cse.moblie.ducks.recycler.CardAdapter;
import cse.moblie.ducks.recycler.ScheduleItem;
import cse.moblie.ducks.request.GetJson;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

public class FragmentSharing extends Fragment {

    private final int REQUEST_ADDSHR = 1;
    private final int REUQUEST_COMMENT = 2;

    private RecyclerView mRecycler_sharing;
    private RecyclerView.LayoutManager mLayoutManager_sharing;
    ArrayList<ScheduleItem> arrayList_sharing = new ArrayList<>();
    private CardAdapter sharingAdapter;


    public FragmentSharing() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sharing_fragment, container, false);

        Button btAdd = view.findViewById(R.id.btAdd);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddSharingActivity.class);
                intent.putExtra("Writer", MainActivity.getUserInfo().get("num"));
                startActivityForResult(intent, REQUEST_ADDSHR);
            }
        });

        mRecycler_sharing = view.findViewById(R.id.rvSharing);
        mRecycler_sharing.setHasFixedSize(true);

        mLayoutManager_sharing = new LinearLayoutManager(getContext());
        //((LinearLayoutManager) mLayoutManager_schedule).setOrientation(LinearLayout.HORIZONTAL);
        mRecycler_sharing.setLayoutManager(mLayoutManager_sharing);

        sharingAdapter = new CardAdapter(arrayList_sharing,new CardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(getActivity(),CommentActivity.class);
                intent.putExtra("Duck", arrayList_sharing.get(position).getDuck());
                intent.putExtra("Title", arrayList_sharing.get(position).getTitle());
                intent.putExtra("Due", arrayList_sharing.get(position).getEndTime());
                intent.putExtra("Content", arrayList_sharing.get(position).getAddress());
                intent.putExtra("Writer", arrayList_sharing.get(position).getMonth());
                intent.putExtra("WrittenDate", arrayList_sharing.get(position).getDate());
                intent.putExtra("WrittenTime", arrayList_sharing.get(position).getStartTime());

                startActivityForResult(intent,REUQUEST_COMMENT);
            }
        });

        mRecycler_sharing.setAdapter(sharingAdapter);

        final GetJson httpConn = GetJson.getInstance();
        new Thread() {
            public void run() {
                httpConn.requestWebServer(getBoardCallback, "getSharingBoard.php", "DUCK=" + MainActivity.getDuckInfo().get("num"));
            }
        }.start();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ADDSHR:
                if (resultCode == RESULT_OK) {
                    final GetJson httpConn = GetJson.getInstance();
                    new Thread() {
                        public void run() {
                            httpConn.requestWebServer(getBoardCallback, "getSharingBoard.php", "DUCK=" + MainActivity.getDuckInfo().get("num"));
                        }
                    }.start();
                    arrayList_sharing.clear();
                    sharingAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    private final Callback getBoardCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("Board", "콜백오류:" + e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body = response.body().string();
            Log.d("Board", "성공:" + body);

            try {
                JSONArray jsonArray = new JSONArray(body);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    final String writer = jsonObject.getString("writer");
                    final String timestamp = jsonObject.getString("cretime");
                    final String writtenDate = timestamp.split(" ")[0];
                    final String writtenTime = timestamp.split(" ")[1];
                    final String dueDate = jsonObject.getString("due");
                    final String title = jsonObject.getString("title");
                    final String content = jsonObject.getString("content");
                    final String duck = MainActivity.getDuckInfo().get("name");
                    final String comments = jsonObject.getString("comment");
                    arrayList_sharing.add(new ScheduleItem(1, writer, writtenDate, writtenTime, dueDate, title, content, duck, comments));
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            sharingAdapter.notifyDataSetChanged();
                        }
                    });

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

}