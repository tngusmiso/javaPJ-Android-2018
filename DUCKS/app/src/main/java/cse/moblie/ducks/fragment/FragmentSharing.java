package cse.moblie.ducks.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cse.moblie.ducks.R;
import cse.moblie.ducks.recycler.CardAdapter;
import cse.moblie.ducks.recycler.ScheduleItem;

public class FragmentSharing extends Fragment {
    private RecyclerView mRecycler_sharing;
    private RecyclerView.LayoutManager mLayoutManager_sharing;
    ArrayList<ScheduleItem> arrayList_sharing = new ArrayList<>();
    private CardAdapter sharingAdapter;

    public FragmentSharing() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        arrayList_sharing.add(new ScheduleItem(1,"곰돌이", "2019.12.10","17:20","2019.01.10","크리스마스 굿즈 나눔snasnasnandfnsdf","미개봉 어쩌구 저쩌구","EXO",0));
        arrayList_sharing.add(new ScheduleItem(1,"곰돌이", "2019.12.10","17:20","2019.01.10","연말콘서트 티켓 양도","직거래 어쩌구 저쩌구","EXO",12));

        View view = inflater.inflate(R.layout.sharing_fragment, container, false);
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

}