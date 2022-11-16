package com.btcdteam.easyedu.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.btcdteam.easyedu.R;
import com.btcdteam.easyedu.models.Classroom;

import java.util.List;

public class ClassroomAdapter extends RecyclerView.Adapter<ClassroomAdapter.ClassroomVH> {
    private List<Classroom> list;
    private ClassRoomItemListener listener;

    public interface ClassRoomItemListener {
        void onItemLongClick(int position, Classroom classroom);

        void onItemClick(int position, Classroom classroom);

    }

    public ClassroomAdapter(List<Classroom> list, ClassRoomItemListener listene) {
        this.list = list;
        this.listener = listene;
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

        holder.itemClass.setOnLongClickListener(v -> {
            listener.onItemLongClick(position, classroom);
            return false;
        });

        holder.itemClass.setOnClickListener(v -> {
            listener.onItemClick(position, classroom);
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
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
