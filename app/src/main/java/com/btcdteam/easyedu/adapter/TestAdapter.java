package com.btcdteam.easyedu.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.btcdteam.easyedu.R;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestHolder> {
    IonClick ionClick;

    public TestAdapter( IonClick ionClick) {
        this.ionClick = ionClick;
    }


    public interface IonClick{
        void onClick();
    }

    @NonNull
    @Override
    public TestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class, parent, false);
        return new TestHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestHolder holder, int position) {
        holder.itemClass.setOnClickListener(v -> {
            ionClick.onClick();
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class TestHolder extends RecyclerView.ViewHolder {
        CardView itemClass;
        public TestHolder(@NonNull View itemView) {
            super(itemView);
            itemClass = itemView.findViewById(R.id.item_class);
        }
    }
}
