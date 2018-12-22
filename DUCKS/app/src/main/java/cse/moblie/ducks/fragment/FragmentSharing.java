package cse.moblie.ducks.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
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

import cse.moblie.ducks.AddSharingActivity;
import cse.moblie.ducks.MainActivity;
import cse.moblie.ducks.R;
import cse.moblie.ducks.recycler.CardAdapter;
import cse.moblie.ducks.recycler.ScheduleItem;
import cse.moblie.ducks.request.GetJson;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FragmentSharing extends Fragment {

    private final int REQUEST_ADDSHR = 1;
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
                Intent intent = new Intent(getActivity(),AddSharingActivity.class);
                intent.putExtra("Writer", MainActivity.getUserInfo().get("num"));
                startActivityForResult(intent, REQUEST_ADDSHR);
            }
        });

        final GetJson httpConn = GetJson.getInstance();
        new Thread() {
            public void run() {
                httpConn.requestWebServer(getInfoCallback, "getSharingBoard.php", "ID=" + loginID);
            }
        }.start();

        arrayList_sharing.add(new ScheduleItem(1,"곰돌이", "2019.12.10","17:20","2019.01.10","크리스마스 굿즈 나눔snasnasnandfnsdf","미개봉 어쩌구 저쩌구","EXO",0));
        arrayList_sharing.add(new ScheduleItem(1,"곰돌이", "2019.12.10","17:20","2019.01.10","연말콘서트 티켓 양도","직거래 어쩌구 저쩌구","EXO",12));

        mRecycler_sharing = view.findViewById(R.id.rvSharing);
        mRecycler_sharing.setHasFixedSize(true);

        mLayoutManager_sharing = new LinearLayoutManager(getContext());
        //((LinearLayoutManager) mLayoutManager_schedule).setOrientation(LinearLayout.HORIZONTAL);
        mRecycler_sharing.setLayoutManager(mLayoutManager_sharing);

        sharingAdapter = new CardAdapter(arrayList_sharing);

        mRecycler_sharing.setAdapter(sharingAdapter);

        sharingAdapter.notifyDataSetChanged();
        return view;
    }

    private final Callback getInfoCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("Genre", "콜백오류:" + e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body = response.body().string();
            Log.d("MyInfo", "성공:" + body);

            try {
                JSONArray jsonArray = new JSONArray(body);
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                userInfo.put("num", jsonObject.getString("num"));
                userInfo.put("name", jsonObject.getString("name"));
                userInfo.put("duck", jsonObject.getString("duck"));
                userInfo.put("itrst1", jsonObject.getString("itrst1"));
                userInfo.put("itrst2", jsonObject.getString("itrst2"));
                userInfo.put("itrst3", jsonObject.getString("itrst3"));

                Handler handler = new Handler(Looper.getMainLooper());


                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        final GetJson httpConn = GetJson.getInstance();

                        String param = "";
                        for (int i = 1; i < 4; i++) {
                            param += "GENRE" + i + "=" + userInfo.get("itrst" + i) + "&";
                        }
                        httpConn.requestWebServer(getDuckCallback, "getDuckInfo.php", "NUM=" + userInfo.get("duck"));
                        httpConn.requestWebServer(getGenreCallback, "getGenre.php", param);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}