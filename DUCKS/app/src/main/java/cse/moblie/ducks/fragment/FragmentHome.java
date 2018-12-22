package cse.moblie.ducks.fragment;

import android.content.Context;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cse.moblie.ducks.MainActivity;
import cse.moblie.ducks.R;
import cse.moblie.ducks.recycler.CardAdapter;
import cse.moblie.ducks.recycler.ScheduleItem;
import cse.moblie.ducks.request.GetJson;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FragmentHome extends Fragment {


    private RecyclerView mRecycler_schedule;
    private RecyclerView.LayoutManager mLayoutManager_schedule;
    private ArrayList<ScheduleItem> arrayList_schedule = new ArrayList<>();
    private static CardAdapter scheduleAdapter;

    public FragmentHome() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment, container, false);

        final GetJson httpConn = GetJson.getInstance();
        new Thread() {
            public void run() {
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                httpConn.requestWebServer(getScheduleCallback, "getSchedule.php", "DUCK=" + MainActivity.getUserInfo().get("duck"));
            }
        }.start();

        mRecycler_schedule = view.findViewById(R.id.home_schedule_cardview);
        mRecycler_schedule.setHasFixedSize(true);

        mLayoutManager_schedule = new LinearLayoutManager(getContext());
        mRecycler_schedule.setLayoutManager(mLayoutManager_schedule);

        scheduleAdapter = new CardAdapter(arrayList_schedule);
        mRecycler_schedule.setAdapter(scheduleAdapter);

        scheduleAdapter.notifyDataSetChanged();

        return view;
    }

    private final Callback getScheduleCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("Schedule", "콜백오류:" + e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body = response.body().string();
            Log.d("Schedule", "성공:" + body);

            try {
                JSONArray jsonArray = new JSONArray(body);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    final String writer = jsonObject.getString("writer");
                    final String timestamp = jsonObject.getString("cretime");
                    final String start = jsonObject.getString("start");
                    String startMonth = null;
                    switch (start.split(" ")[0].split("-")[1]){
                        case "1": startMonth = "JAN"; break;
                        case "2": startMonth = "FEB"; break;
                        case "3": startMonth = "MAR"; break;
                        case "4": startMonth = "APR"; break;
                        case "5": startMonth = "MAY"; break;
                        case "6": startMonth = "JUN"; break;
                        case "7": startMonth = "JUL"; break;
                        case "8": startMonth = "AUG"; break;
                        case "9": startMonth = "SEP"; break;
                        case "10": startMonth = "OCT"; break;
                        case "11": startMonth = "NOV"; break;
                        case "12": startMonth = "DEC"; break;
                        default: startMonth = "UNKNOWN"; break;
                    }
                    final String startDate= start.split(" ")[0].split("-")[2];
                    final String startTime = start.split(" ")[1].split(":")[0]+":"+start.split(" ")[1].split(":")[1];
                    final String end = jsonObject.getString("end");
                    final String endMonth = end.split(" ")[0].split("-")[1];
                    final String endDate = end.split(" ")[0].split("-")[2];
                    final String entTime = end.split(" ")[1].split(":")[0]+":"+end.split(" ")[1].split(":")[1];
                    final String string = jsonObject.getString("string");
                    final String location = MainActivity.getDuckInfo().get("location");
                    final String duck = MainActivity.getDuckInfo().get("duck");
                    final String like = jsonObject.getString("like");

                    arrayList_schedule.add(new ScheduleItem(0,startMonth, startDate, startTime,entTime,string,location,duck,like));

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            scheduleAdapter.notifyDataSetChanged();
                        }
                    });

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}