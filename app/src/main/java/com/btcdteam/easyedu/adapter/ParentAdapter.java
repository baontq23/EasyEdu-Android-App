package com.btcdteam.easyedu.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.btcdteam.easyedu.R;
import com.btcdteam.easyedu.interfaces.IonClick;
import com.btcdteam.easyedu.models.Parent;
import com.btcdteam.easyedu.models.Student;

import java.util.List;

public class ParentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static int TYPE_HEADER = 0;
    private static int TYPE_ITEM = 1;
    private IonClick ionClickItem;
    private IonClick ionClickHeader;
    private IonClick ionClickBtnOption;
    private List<Parent> list;

    public ParentAdapter(List<Parent> list, IonClick onClickItem, IonClick onClickBtnOption, IonClick onClickHeader) {
        this.ionClickItem = onClickItem;
        this.ionClickHeader = onClickHeader;
        this.ionClickBtnOption = onClickBtnOption;
        this.list = list;
        this.list.add(0, new Parent("Phụ Huynh", null));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (TYPE_HEADER == viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_recycleview, parent, false);
            return new HeaderVH(view);
        }else if (TYPE_ITEM == viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent, parent, false);
            return new ParentVH(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Parent parent = list.get(position);

        if(TYPE_HEADER == holder.getItemViewType()){
            HeaderVH headerVH = (HeaderVH) holder;
            headerVH.title.setText("Phụ Huynh");
            headerVH.btnAdd.setOnClickListener(v -> {
                ionClickHeader.onClick(parent);
            });
        }else if(TYPE_ITEM == holder.getItemViewType()){
            ParentVH parentVH = (ParentVH) holder;

            parentVH.tvParentName.setText(parent.getName());

            parentVH.itemParent.setOnClickListener(v -> {
                ionClickItem.onClick(parent);
            });

            parentVH.btnOption.setOnClickListener(v -> {
                ionClickBtnOption.onClick(parent);
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
        TextView title;
        public HeaderVH(@NonNull View itemView) {
            super(itemView);
            btnAdd = itemView.findViewById(R.id.img_header_add);
            title = itemView.findViewById(R.id.tv_header_title);
        }
    }

    public class ParentVH extends RecyclerView.ViewHolder {
        TextView tvParentName;
        ImageView btnOption;
        LinearLayout itemParent;
        public ParentVH(@NonNull View itemView) {
            super(itemView);
            tvParentName = itemView.findViewById(R.id.tv_parent_name);
            btnOption = itemView.findViewById(R.id.img_parent_option);
            itemParent = itemView.findViewById(R.id.item_parent);
        }
    }
}
