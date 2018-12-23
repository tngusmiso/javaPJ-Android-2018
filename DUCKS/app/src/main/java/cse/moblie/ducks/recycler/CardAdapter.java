package cse.moblie.ducks.recycler;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cse.moblie.ducks.R;

public class CardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ScheduleItem> arrayList;
    private Button btComment;
    private OnItemClickListener onItemClickListener;

    public CardAdapter(ArrayList<ScheduleItem> arrayList,OnItemClickListener onItemClickListener) {
        this.arrayList = arrayList;
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case 0 :
                View v1 = inflater.inflate(R.layout.schedule_cardview, viewGroup, false);
                viewHolder = new ScheduleViewHolder(v1);
                break;
            case 1 :
                View v2 = inflater.inflate(R.layout.sharing_cardview, viewGroup, false);
                viewHolder = new SharingViewHolder(v2);
                break;
            case 2 :
                View v3 = inflater.inflate(R.layout.comment_view, viewGroup, false);
                viewHolder = new CommentViewHolder(v3);
                break;

            default:
                View v = inflater.inflate(R.layout.comment_view, viewGroup, false);
                viewHolder = new CommentViewHolder(v);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        switch (viewHolder.getItemViewType()) {
            case 0:
                ScheduleViewHolder vh1 = (ScheduleViewHolder) viewHolder;
                configureScheduleViewHolder(vh1, position);
                break;
            case 1:
                SharingViewHolder vh2 = (SharingViewHolder) viewHolder;
                vh2.getComments().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(v, position);
                    }
                });
                configureSharingViewHolder(vh2, position);
                break;

            default:
                CommentViewHolder vh3 = (CommentViewHolder) viewHolder;
                configureCommentViewHolder(vh3, position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return arrayList.get(position).Type;
    }

    private void configureScheduleViewHolder(ScheduleViewHolder vh, int position) {
        vh.month.setText(arrayList.get(position).getMonth());
        vh.date.setText(arrayList.get(position).getDate());
        vh.startTime.setText(arrayList.get(position).getStartTime());
        vh.endTime.setText(arrayList.get(position).getEndTime());
        vh.title.setText(arrayList.get(position).getTitle());
        vh.address.setText(arrayList.get(position).getAddress());
    }

    private void configureSharingViewHolder(SharingViewHolder vh, int position) {
        vh.writer.setText("작성자 : "+arrayList.get(position).getMonth());
        vh.writtenDate.setText(arrayList.get(position).getDate());
        vh.writtenTime.setText(arrayList.get(position).getStartTime());
        vh.dueTime.setText("마감일 : "+ arrayList.get(position).getEndTime());
        vh.title.setText(arrayList.get(position).getTitle());
        vh.content.setText(arrayList.get(position).getAddress());
        vh.duck.setText(arrayList.get(position).getDuck());
        vh.comments.setText("댓글보기("+arrayList.get(position).getComments()+")");
    }
    private void configureCommentViewHolder(CommentViewHolder vh, int position) {
        vh.tvWriter.setText(arrayList.get(position).getMonth());
        vh.tvComment.setText(arrayList.get(position).getDate());
        vh.tvDate.setText(arrayList.get(position).getStartTime());
        vh.tvTime.setText(arrayList.get(position).getEndTime());
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

    private class SharingViewHolder extends RecyclerView.ViewHolder {
        TextView writer;
        TextView writtenDate;
        TextView writtenTime;
        TextView dueTime;
        TextView duck;
        TextView title;
        TextView content;
        Button comments;

        public SharingViewHolder(View view) {
            super(view);

            writer=(TextView)view.findViewById(R.id.tvWriter);
            writtenDate=(TextView)view.findViewById(R.id.tvWrittenDate);
            writtenTime=(TextView)view.findViewById(R.id.tvWrittenTime);
            dueTime=(TextView)view.findViewById(R.id.tvDueTime);
            duck = (TextView)view.findViewById(R.id.tvWhichDuck);
            title=(TextView)view.findViewById(R.id.tvEachTitle);
            content=(TextView)view.findViewById(R.id.tvEachContent);
            comments = (Button)view.findViewById(R.id.btComments);
        }

        public Button getComments() {
            return comments;
        }
    }

    private class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView tvWriter;
        TextView tvComment;
        TextView tvDate;
        TextView tvTime;

        public CommentViewHolder(View view) {
            super(view);

            tvWriter=(TextView)view.findViewById(R.id.tvWriter);
            tvComment=(TextView)view.findViewById(R.id.tvComment);
            tvDate=(TextView)view.findViewById(R.id.tvDate);
            tvTime=(TextView)view.findViewById(R.id.tvTime);
        }
    }
}