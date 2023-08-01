package com.example.quanlyphonggym.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyphonggym.R;
import com.example.quanlyphonggym.dao.LoaiGoiDAO;
import com.example.quanlyphonggym.model.ItemClick;
import com.example.quanlyphonggym.model.LoaiGoi;

import java.util.ArrayList;

public class LoaiGoiAdapter extends RecyclerView.Adapter<LoaiGoiAdapter.ViewHolder> {
    private Context context;
    ArrayList<LoaiGoi> list;
    private ItemClick itemClick;

    public LoaiGoiAdapter(Context context, ArrayList<LoaiGoi> list, ItemClick itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_loaigoi, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       holder.txtTenLoai.setText("Tên loại: " + list.get(position).getTenloai());
        holder.txtMaLoai.setText("Mã loại: " + String.valueOf(list.get(position).getId()));

        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoaiGoiDAO loaiGoiDAO = new LoaiGoiDAO(context);
                int check = loaiGoiDAO.xoaLoaiGoi(list.get(holder.getAdapterPosition()).getId());
                switch (check){
                    case 1:
                        //load lại data
                        list.clear();
                        list = loaiGoiDAO.getDSLoaiGoi();
                        notifyDataSetChanged();
                        break;
                    case -1:
                        Toast.makeText(context, "Không thể xóa loại gói này vì đã có sách thuộc thể loại này", Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        Toast.makeText(context, "Xóa loại gói này không thành công", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoaiGoi loaiGoi = list.get(holder.getAdapterPosition());
                itemClick.onClickLoaiGoi(loaiGoi);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaLoai, txtTenLoai;
        ImageView ivDel, ivEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMaLoai = itemView.findViewById(R.id.txtMaLoai);
            txtTenLoai = itemView.findViewById(R.id.txtTenLoai);
            ivDel = itemView.findViewById(R.id.ivDel);
            ivEdit = itemView.findViewById(R.id.ivEdit);
        }
    }
}
