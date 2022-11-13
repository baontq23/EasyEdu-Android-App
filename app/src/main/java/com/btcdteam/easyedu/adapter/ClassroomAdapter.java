package com.btcdteam.easyedu.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.btcdteam.easyedu.R;
import com.btcdteam.easyedu.interfaces.IonClick;
import com.btcdteam.easyedu.models.Classroom;

import java.util.List;

public class ClassroomAdapter extends RecyclerView.Adapter<ClassroomAdapter.ClassroomVH> {
    private List<Classroom> list;
    private IonClick ionClick;

    public ClassroomAdapter(List<Classroom> list, IonClick ionClick) {
        this.list = list;
        this.ionClick = ionClick;
    }

    @NonNull
    @Override
    public ClassroomVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class, parent, false);
        return new ClassroomVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassroomVH holder, int position) {
        Classroom classroom = list.get(position);
        holder.tvClassName.setText(classroom.getName());
        holder.tvClassDescription.setText(classroom.getDescription());
        holder.tvQuantity.setText("Học sinh: " + classroom.getCount());
        holder.itemClass.setOnClickListener(v -> {
            ionClick.onClick();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ClassroomVH extends RecyclerView.ViewHolder {
        CardView itemClass;
        TextView tvClassName, tvClassDescription, tvQuantity;
        public ClassroomVH(@NonNull View itemView) {
            super(itemView);
            itemClass = itemView.findViewById(R.id.item_class);
            tvClassName = itemView.findViewById(R.id.tv_item_class_name);
            tvClassDescription = itemView.findViewById(R.id.tv_item_class_description);
            tvQuantity = itemView.findViewById(R.id.tv_item_class_quantity);
        }
    }
}
