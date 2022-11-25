package com.btcdteam.easyedu.adapter.parent;

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

public class StudentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private StudentItemListener listener;

    public interface StudentItemListener {
        void onItemClick(int position);
    }

    public StudentAdapter(StudentItemListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student_parent, parent, false);
        return new ParentVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ParentVH parentVH = (ParentVH) holder;

        parentVH.itemParent.setOnClickListener(v -> {
            listener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ParentVH extends RecyclerView.ViewHolder {
        TextView tvParentName;
        LinearLayout itemParent;

        public ParentVH(@NonNull View itemView) {
            super(itemView);
            tvParentName = itemView.findViewById(R.id.tv_parent_student_name);
            itemParent = itemView.findViewById(R.id.item_parent_student);
        }
    }
}
