package cse.moblie.ducks.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Calendar;

import cse.moblie.ducks.CardviewAdapter;
import cse.moblie.ducks.R;
import cse.moblie.ducks.decorator.SaturdayDecorator;
import cse.moblie.ducks.decorator.SundayDecorator;
import cse.moblie.ducks.decorator.TodayDecorator;

public class FragmentSchedule extends Fragment {


    private static MaterialCalendarView materialCalendarView;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // 아이템 리스트
    //private String[] myDataset;
    private static ArrayList<item> itemArrayList;

    public FragmentSchedule() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.schedule_fragment, container, false);
        materialCalendarView = (MaterialCalendarView) v.findViewById(R.id.calendarView);

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


        //ArrayList 생성
        itemArrayList = new ArrayList<>();
        //ArrayList에 값 추가하기
        itemArrayList.add(new item("DEC", "23", "오후 6시", "장소 미정", "크리스마스 콘서트"));
        itemArrayList.add(new item("DEC", "23", "오후 6시", "장소 미정", "크리스마스 콘서트2"));
        itemArrayList.add(new item("DEC", "23", "오후 6시", "장소 미정", "크리스마스 콘서트3"));

        mRecyclerView = (RecyclerView) v.findViewById(R.id.rvSchedule);
        mRecyclerView.setHasFixedSize(true);//옵션
        //Linear layout manager 사용
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //어답터 세팅
        mAdapter = new CardviewAdapter(itemArrayList); //스트링 배열 데이터 인자로
        mRecyclerView.setAdapter(mAdapter);


    // Inflate the layout for this fragment
        return inflater.inflate(R.layout.schedule_fragment, container, false);
    }

    public class item {
        String month;
        String date;
        String time;
        String location;
        String title;

        public item(String month, String date, String time, String location, String title) {
            this.month = month;
            this.date = date;
            this.time = time;
            this.location = location;
            this.title = title;
        }

        public String getMonth() {
            return month;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }

        public String getLocation() {
            return location;
        }

        public String getTitle() {
            return title;
        }
    }

}