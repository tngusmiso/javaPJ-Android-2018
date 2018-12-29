package cse.moblie.ducks.fragment;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

import cse.moblie.ducks.MainActivity;
import cse.moblie.ducks.R;
import cse.moblie.ducks.decorator.EventDecorator;
import cse.moblie.ducks.decorator.SaturdayDecorator;
import cse.moblie.ducks.decorator.SundayDecorator;
import cse.moblie.ducks.decorator.TodayDecorator;
import cse.moblie.ducks.recycler.CardAdapter;
import cse.moblie.ducks.recycler.ScheduleItem;
import cse.moblie.ducks.request.GetJson;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FragmentSchedule extends Fragment {

    private static MaterialCalendarView materialCalendarView;

    private RecyclerView mRecycler_schedule;
    private RecyclerView.LayoutManager mLayoutManager_schedule;
    ArrayList<ScheduleItem> arrayList_schedule = new ArrayList<>();
    ArrayList<String> arrayList_days= new ArrayList<String>();
    private CardAdapter scheduleAdapter;

    public FragmentSchedule() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.schedule_fragment, container, false);
        materialCalendarView = (MaterialCalendarView) view.findViewById(R.id.calendarView);

        materialCalendarView.setSelectedDate(CalendarDay.today());
        materialCalendarView.state().edit()
                .isCacheCalendarPositionEnabled(false)
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(1980, 0, 1)) // 달력의 시작
                .setMaximumDate(CalendarDay.from(2030, 11, 31)) // 달력의 끝
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.addDecorator(new SundayDecorator());
        materialCalendarView.addDecorator(new SaturdayDecorator());
        materialCalendarView.addDecorator(new TodayDecorator());

        new ApiSimulator(arrayList_days).executeOnExecutor(Executors.newSingleThreadExecutor());

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int Year = date.getYear();
                int Month = date.getMonth() + 1;
                int Day = date.getDay();

                Log.i("Year test", Year + "");
                Log.i("Month test", Month + "");
                Log.i("Day test", Day + "");

            }
        });

        mRecycler_schedule = view.findViewById(R.id.rvSchedule);
        mRecycler_schedule.setHasFixedSize(true);

        mLayoutManager_schedule = new LinearLayoutManager(getContext());
        mRecycler_schedule.setLayoutManager(mLayoutManager_schedule);

        scheduleAdapter = new CardAdapter(arrayList_schedule,null);

        mRecycler_schedule.setAdapter(scheduleAdapter);

        scheduleAdapter.notifyDataSetChanged();

        final GetJson httpConn = GetJson.getInstance();
        new Thread() {
            public void run() {
                httpConn.requestWebServer(getScheduleCallback, "getSchedule.php", "DUCK=" + MainActivity.getDuckInfo().get("num"));
            }
        }.start();

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

                    arrayList_schedule.add(new ScheduleItem(0,startMonth, startDate, startTime,entTime,string,location,duck,like,null));
                    arrayList_days.add(start.split(" ")[0].toString());

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


    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        ArrayList<String> Time_Result;

        ApiSimulator(ArrayList<String> Time_Result){
            this.Time_Result = Time_Result;
        }

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            ArrayList<CalendarDay> dates = new ArrayList<>();

            Log.i("Time_Result", Time_Result.toString() + "");

            /*특정날짜 달력에 점표시해주는곳*/
            /*월은 0이 1월 년,일은 그대로*/
            //string 문자열인 Time_Result 을 받아와서 ,를 기준으로짜르고 string을 int 로 변환
            for(int i = 0 ; i < Time_Result.size() ; i ++){
                CalendarDay day = CalendarDay.from(calendar);
                String[] time = Time_Result.get(i).split("-");
                int year = Integer.parseInt(time[0]);
                int month = Integer.parseInt(time[1]);
                int dayy = Integer.parseInt(time[2]);

                Log.i("Year test", year + "");
                Log.i("Month test", month + "");
                Log.i("Day test", dayy + "");

                calendar.set(year,month-1,dayy);
                dates.add(CalendarDay.from(year,month-1,dayy));
            }
            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

//            if (isFinishing()) {
//                return;
//            }
            materialCalendarView.addDecorator(new EventDecorator(Color.parseColor("#C70309"), calendarDays,getActivity()));
        }
    }
}