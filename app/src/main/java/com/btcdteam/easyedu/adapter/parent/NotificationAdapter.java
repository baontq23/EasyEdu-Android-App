package com.btcdteam.easyedu.adapter.parent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.btcdteam.easyedu.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotiVH> {


    @NonNull
    @Override
    public NotiVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotiVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotiVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class NotiVH extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDate, tvContent;

        public NotiVH(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_noti_title);
            tvDate = itemView.findViewById(R.id.tv_noti_date);
            tvContent = itemView.findViewById(R.id.tv_noti_content);
        }
    }
}
