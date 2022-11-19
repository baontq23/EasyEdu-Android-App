package com.btcdteam.easyedu.adapter;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.btcdteam.easyedu.R;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackVH> {

    @NonNull
    @Override
    public FeedbackVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback, parent, false);
        return new FeedbackVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackVH holder, int position) {

        holder.btnDelete.setOnClickListener(v -> {
            //xoá feedback
        });

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class FeedbackVH extends RecyclerView.ViewHolder {
        TextView tvContentFeedback, tvDateFeedback;
        LinearLayout item;
        ImageView btnDelete;

        public FeedbackVH(@NonNull View itemView) {
            super(itemView);
            tvContentFeedback = itemView.findViewById(R.id.tv_feedback_content);
            tvDateFeedback = itemView.findViewById(R.id.tv_feedback_date);
            item = itemView.findViewById(R.id.item_feedback);
            btnDelete = itemView.findViewById(R.id.img_feedback_delete);
        }
    }
}
