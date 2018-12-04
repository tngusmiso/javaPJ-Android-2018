package cse.moblie.ducks;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import cse.moblie.ducks.fragment.FragmentSchedule;

/**
 * Created by js on 2017-03-18.
 */

public class CardviewAdapter extends RecyclerView.Adapter<CardviewAdapter.ViewHolder> {

    private ArrayList<FragmentSchedule.item> scheduleList; //FragmentSchedule에 item class를 정의해 놓았음

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // 사용될 항목들 선언
        public TextView tvMonth;
        public TextView tvDate;
        public TextView tvTime;
        public TextView tvLocation;
        public TextView tvTitle;

        public ViewHolder(View v) {
            super(v);

            tvMonth = (TextView)v.findViewById(R.id.sch_month);
            tvDate = (TextView) v.findViewById(R.id.sch_date);
            tvTime = (TextView) v.findViewById(R.id.sch_time);
            tvLocation = (TextView) v.findViewById(R.id.sch_location);
            tvTitle = (TextView) v.findViewById(R.id.sch_title);
        }
    }

    // 생성자 - 넘어 오는 데이터파입에 유의해야 한다.
    public CardviewAdapter(ArrayList<FragmentSchedule.item> scheduleList) {
        this.scheduleList = scheduleList;
    }


    //뷰홀더
    // Create new views (invoked by the layout manager)
    @Override
    public CardviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_cardview, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.tvMonth.setText(scheduleList.get(position).getMonth());
        holder.tvDate.setText(scheduleList.get(position).getDate());
        holder.tvTime.setText(scheduleList.get(position).getTime());
        holder.tvLocation.setText(scheduleList.get(position).getLocation());
        holder.tvTitle.setText(scheduleList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }
}