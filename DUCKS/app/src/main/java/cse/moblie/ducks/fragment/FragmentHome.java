package cse.moblie.ducks.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cse.moblie.ducks.MainActivity;
import cse.moblie.ducks.R;
import cse.moblie.ducks.recycler.CardAdapter;
import cse.moblie.ducks.recycler.ScheduleItem;

public class FragmentHome extends Fragment {


    private RecyclerView mRecycler_schedule;
    private RecyclerView.LayoutManager mLayoutManager_schedule;
    ArrayList<ScheduleItem> arrayList_schedule = new ArrayList<>();
    private CardAdapter scheduleAdapter;

    public FragmentHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        arrayList_schedule.add(new ScheduleItem(0,"DEC", "25","18:00","20:30","크리스마스 파티","서울시 어쩌구 저쩌구",null,0));
        arrayList_schedule.add(new ScheduleItem(0,"DEC", "31","22:00","23:59","새해 파티","서울시 어쩌구 저쩌구",null,0));

        View view = inflater.inflate(R.layout.home_fragment, container, false);
        mRecycler_schedule = view.findViewById(R.id.home_schedule_cardview);
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