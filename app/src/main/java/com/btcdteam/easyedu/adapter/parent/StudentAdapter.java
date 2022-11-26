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

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentVH> {

    private StudentItemListener listener;

    public interface StudentItemListener {
        void onItemClick(int position);
    }

    public StudentAdapter(StudentItemListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public StudentVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student_parent, parent, false);
        return new StudentVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentVH holder, int position) {

        holder.item.setOnClickListener(v -> {
            listener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class StudentVH extends RecyclerView.ViewHolder {
        TextView tvName, tvTotalClass, tvAvgScore;
        LinearLayout item;

        public StudentVH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_parent_student_name);
            tvTotalClass = itemView.findViewById(R.id.tv_student_parent_total_class);
            tvAvgScore = itemView.findViewById(R.id.tv_student_parent_avg_score);
            item = itemView.findViewById(R.id.item_parent_student);
        }
    }
}
