package com.btcdteam.easyedu.adapter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.btcdteam.easyedu.R;

public class PreviewAdapter extends RecyclerView.Adapter<PreviewAdapter.PreviewVH> {

    @NonNull
    @Override
    public PreviewVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_preview, parent, false);
        return new PreviewVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviewVH holder, int position) {
        if (position % 2 == 0){
            holder.item.setBackgroundResource(R.color.bg_primary);
        }

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class PreviewVH extends RecyclerView.ViewHolder {
        TextView tvStudentName, tvParentName, tvStudentDOB, tvParentPhoneNumber;
        TableLayout item;
        public PreviewVH(@NonNull View itemView) {
            super(itemView);
            tvStudentName = itemView.findViewById(R.id.tv_preview_student_name);
            tvParentName = itemView.findViewById(R.id.tv_preview_parent_name);
            tvStudentDOB = itemView.findViewById(R.id.tv_preview_student_dob);
            tvParentPhoneNumber = itemView.findViewById(R.id.tv_preview_parent_phone_number);
            item = itemView.findViewById(R.id.item_preview);
        }
    }
}
