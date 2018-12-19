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

import cse.moblie.ducks.R;
import cse.moblie.ducks.decorator.SaturdayDecorator;
import cse.moblie.ducks.decorator.SundayDecorator;
import cse.moblie.ducks.decorator.TodayDecorator;
import cse.moblie.ducks.recycler.CardAdapter;
import cse.moblie.ducks.recycler.ScheduleItem;

public class FragmentSchedule extends Fragment {

    private static MaterialCalendarView materialCalendarView;

    private RecyclerView mRecycler_schedule;
    private RecyclerView.LayoutManager mLayoutManager_schedule;
    ArrayList<ScheduleItem> arrayList_schedule = new ArrayList<>();
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



        arrayList_schedule.add(new ScheduleItem(0,"DEC", "25","18:00","20:30","크리스마스 파티","서울시 어쩌구 저쩌구",null,0));
        arrayList_schedule.add(new ScheduleItem(0,"DEC", "31","22:00","23:59","새해 파티","서울시 어쩌구 저쩌구",null,0));

        mRecycler_schedule = view.findViewById(R.id.rvSchedule);
        mRecycler_schedule.setHasFixedSize(true);

        mLayoutManager_schedule = new LinearLayoutManager(getContext());
        //((LinearLayoutManager) mLayoutManager_schedule).setOrientation(LinearLayout.HORIZONTAL);
        mRecycler_schedule.setLayoutManager(mLayoutManager_schedule);

        scheduleAdapter = new CardAdapter(arrayList_schedule);


        mRecycler_schedule.setAdapter(scheduleAdapter);

        scheduleAdapter.notifyDataSetChanged();
        return view;
    }
}