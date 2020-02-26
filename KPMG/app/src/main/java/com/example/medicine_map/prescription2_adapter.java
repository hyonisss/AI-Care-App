package com.example.medicine_map;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class prescription2_adapter extends RecyclerView.Adapter<prescription2_adapter.CustomViewHolder>{
    private ArrayList<prescription2_data> arrayList;

    public prescription2_adapter(ArrayList<prescription2_data> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public prescription2_adapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_prescription2,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }
//추가될때
    @Override
    public void onBindViewHolder(@NonNull final prescription2_adapter.CustomViewHolder holder, int position) {
        holder.profile.setImageResource(arrayList.get(position).getProfile());
        holder.clock.setImageResource(arrayList.get(position).getClock());
        holder.disease_date.setText(arrayList.get(position).getDisease_date());
        holder.disease.setText(arrayList.get(position).getDisease());
        holder.hospital.setText(arrayList.get(position).getHospital());
        holder.date.setText(arrayList.get(position).getDate());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String curName = holder.disease.getText().toString();
                Toast.makeText(view.getContext(), curName, Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                remove(holder.getAdapterPosition());
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }
    //길게 누르면 사라지는 거
    public void remove(int position) {
        try {
            //리스트뷰 치우기
            arrayList.remove(position);
            //새로고침
            notifyItemRemoved(position);

        }catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView clock;
        protected ImageView profile;
        protected TextView date;
        protected TextView disease;
        protected TextView disease_date;
        protected TextView hospital;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.clock = (ImageView) itemView.findViewById(R.id.clock);
            this.profile = (ImageView) itemView.findViewById(R.id.profile);
            this.date = (TextView) itemView.findViewById(R.id.date);
            this.disease_date = (TextView) itemView.findViewById(R.id.disease_date);
            this.disease = (TextView) itemView.findViewById(R.id.disease);
            this.hospital = (TextView) itemView.findViewById(R.id.hospital);
        }
    }
}
