package com.btcdteam.easyedu.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.btcdteam.easyedu.R;
import com.btcdteam.easyedu.interfaces.IonClick;
import com.btcdteam.easyedu.models.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static int TYPE_HEADER = 0;
    private static int TYPE_ITEM = 1;
    private IonClick ionClickItem;
    private IonClick ionClickHeader;
    private List<Student> list;

    public StudentAdapter(List<Student> list, IonClick onClickItem, IonClick onClickHeader) {
        this.ionClickItem = onClickItem;
        this.ionClickHeader = onClickHeader;
        this.list = list;
        this.list.add(0, new Student("Học Sinh", null, null, null));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (TYPE_HEADER == viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_recycleview, parent, false);
            return new HeaderVH(view);
        }else if (TYPE_ITEM == viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
            return new StudentVH(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Student student = list.get(position);

        if(TYPE_HEADER == holder.getItemViewType()){
            HeaderVH headerVH = (HeaderVH) holder;

            headerVH.tvTitle.setText(student.getName());
            headerVH.btnAdd.setOnClickListener(v -> {
                ionClickHeader.onClick(student);
            });
        }else if(TYPE_ITEM == holder.getItemViewType()){
            StudentVH studentVH = (StudentVH) holder;

            studentVH.tvStudentName.setText(student.getName());
            studentVH.itemStudent.setOnClickListener(v -> {
                ionClickItem.onClick(student);
            });
        }
    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }else{
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return TYPE_HEADER;
        }else{
            return TYPE_ITEM;
        }
    }

    public class HeaderVH extends RecyclerView.ViewHolder {
        ImageView btnAdd;
        TextView tvTitle;
        public HeaderVH(@NonNull View itemView) {
            super(itemView);
            btnAdd = itemView.findViewById(R.id.img_header_add);
            tvTitle = itemView.findViewById(R.id.tv_header_title);
        }
    }

    public class StudentVH extends RecyclerView.ViewHolder {
        TextView tvStudentName, tvRegularScore1, tvRegularScore2, tvRegularScore3, tvMidtermScore, tvFinalScore;
        CardView itemStudent;
        public StudentVH(@NonNull View itemView) {
            super(itemView);
            tvStudentName = itemView.findViewById(R.id.tv_student_name);
            tvRegularScore1 = itemView.findViewById(R.id.tv_regular_score1);
            tvRegularScore2 = itemView.findViewById(R.id.tv_regular_score2);
            tvRegularScore3 = itemView.findViewById(R.id.tv_regular_score3);
            tvMidtermScore = itemView.findViewById(R.id.tv_midterm_score);
            tvFinalScore = itemView.findViewById(R.id.tv_final_score);
            itemStudent = itemView.findViewById(R.id.item_student);
        }
    }
}
