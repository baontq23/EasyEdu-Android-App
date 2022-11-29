package com.btcdteam.easyedu.adapter.teacher;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.btcdteam.easyedu.R;
import com.btcdteam.easyedu.models.Parent;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackVH> {
    private List<Parent> list;
    private FeedbackCallback feedbackCallback;

    public FeedbackAdapter(List<Parent> list, FeedbackCallback feedbackCallback) {
        this.list = list;
        this.feedbackCallback = feedbackCallback;
    }

    public interface FeedbackCallback {
        void callWithPhoneNumber(Parent parent);

        void smsTextWithPhoneNumber(Parent parent);
    }

    @NonNull
    @Override
    public FeedbackVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback, parent, false);
        return new FeedbackVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackVH holder, int position) {
        Parent parent = list.get(position);
        if (parent.getFcmToken() == null || parent.getFcmToken().equals("")) {
            holder.btnSms.setColorFilter(Color.parseColor("#D50000"));
        } else {
            holder.btnSms.setColorFilter(Color.parseColor("#3787FF"));
        }
        holder.tvContentFeedback.setText(parent.getName());
        holder.tvDateFeedback.setText(parent.getPhone());
        holder.btnPhone.setOnClickListener(v -> feedbackCallback.callWithPhoneNumber(parent));
        holder.btnSms.setOnClickListener(v -> feedbackCallback.smsTextWithPhoneNumber(parent));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FeedbackVH extends RecyclerView.ViewHolder {
        TextView tvContentFeedback, tvDateFeedback;
        LinearLayout item;
        ImageView btnSms, btnPhone;

        public FeedbackVH(@NonNull View itemView) {
            super(itemView);
            tvContentFeedback = itemView.findViewById(R.id.tv_feedback_content);
            tvDateFeedback = itemView.findViewById(R.id.tv_feedback_date);
            item = itemView.findViewById(R.id.item_feedback);
            btnSms = itemView.findViewById(R.id.img_feedback_sms);
            btnPhone = itemView.findViewById(R.id.img_feedback_call);
        }
    }
}
