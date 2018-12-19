package cse.moblie.ducks.recycler;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cse.moblie.ducks.R;

public class ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ScheduleItem> arrayList;

    public ScheduleAdapter(ArrayList<ScheduleItem> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            default:
                View v1 = inflater.inflate(R.layout.schedule_cardview, viewGroup, false);
                viewHolder = new ScheduleViewHolder(v1);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            default:
                ScheduleViewHolder vh = (ScheduleViewHolder) viewHolder;
                configureScheduleViewHolder(vh, position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private void configureScheduleViewHolder(ScheduleViewHolder vh, int position) {
        vh.month.setText(arrayList.get(position).getMonth());
        vh.date.setText(arrayList.get(position).getDate());
        vh.startTime.setText(arrayList.get(position).getStartTime());
        vh.endTime.setText(arrayList.get(position).getEndTime());
        vh.title.setText(arrayList.get(position).getTitle());
        vh.address.setText(arrayList.get(position).getAddress());
    }


    private class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView month;
        TextView date;
        TextView startTime;
        TextView endTime;
        TextView title;
        TextView address;

        public ScheduleViewHolder(View view) {
            super(view);

            month=(TextView)view.findViewById(R.id.tvMonth);
            date=(TextView)view.findViewById(R.id.tvDate);
            startTime=(TextView)view.findViewById(R.id.tvStartTime);
            endTime=(TextView)view.findViewById(R.id.tvEndTime);
            title=(TextView)view.findViewById(R.id.tvSchedule);
            address=(TextView)view.findViewById(R.id.tvAddress);
        }
    }
}