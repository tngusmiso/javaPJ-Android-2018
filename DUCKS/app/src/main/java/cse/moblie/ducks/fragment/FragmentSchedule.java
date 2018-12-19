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

public class FragmentSchedule extends Fragment {


    private static MaterialCalendarView materialCalendarView;

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



    // Inflate the layout for this fragment
        return inflater.inflate(R.layout.schedule_fragment, container, false);
    }


}