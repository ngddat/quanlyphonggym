package com.example.quanlyphonggym.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyphonggym.R;
import com.example.quanlyphonggym.dao.TheHoiVienDAO;
import com.example.quanlyphonggym.model.TheHoiVien;

import java.util.ArrayList;

public class TheHoiVienAdapter extends RecyclerView.Adapter<TheHoiVienAdapter.ViewHolder>{

    private ArrayList<TheHoiVien> list;
    private Context context;

    public TheHoiVienAdapter(ArrayList<TheHoiVien> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_thehoivien, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaTHV.setText("Mã thẻ hội viên: " + list.get(position).getMathv());
        holder.txtMaHV.setText("Mã hội viên: " + list.get(position).getMahv());
        holder.txtTenHV.setText("Mã tên hội viên: " + list.get(position).getTenhv());
        holder.txtMaNV.setText("Mã nhân viên: " + list.get(position).getManv());
        holder.txtTenNV.setText("Tên nhân viên: " + list.get(position).getTennv());
        holder.txtMaGoi.setText("Mã gói: " + list.get(position).getMagoi());
        holder.txtTenGoi.setText("Tên gói: " + list.get(position).getTengoi());
        holder.txtNgay.setText("Ngày: " + list.get(position).getNgay());
        
        String trangthai = "";
        if(list.get(position).getTragoi() == 1){
            trangthai = "Trạng thái: Đã hoàn thành khóa học";
            holder.txtTrangThai.setTextColor(Color.parseColor("#ff0000"));
            holder.btnDangKy.setVisibility(View.GONE);
        }else {
            holder.btnDangKy.setVisibility(View.VISIBLE);
        }
        holder.txtTienThue.setText("Tiền thuê: " + list.get(position).getTienthue());
        holder.txtTrangThai.setText("" +trangthai);

        holder.btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TheHoiVienDAO theHoiVienDAO = new TheHoiVienDAO(context);
                boolean kiemtra = theHoiVienDAO.thaydoiTrangThai(list.get(holder.getAdapterPosition()).getMathv());
                if(kiemtra){
                    list.clear();
                    list = theHoiVienDAO.getDSTheHoiVien();
                    notifyDataSetChanged();
                }else {
                    Toast.makeText(context, "Thay đổi trạng thái không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaTHV, txtMaHV, txtTenHV, txtMaNV, txtTenNV, txtMaGoi, txtTenGoi, txtNgay, txtTienThue, txtTrangThai;
        Button btnDangKy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMaTHV = itemView.findViewById(R.id.txtMaTHV);
            txtMaHV = itemView.findViewById(R.id.txtMaHV);
            txtTenHV = itemView.findViewById(R.id.txtTenHV);
            txtMaNV = itemView.findViewById(R.id.txtMaNV);
            txtTenNV = itemView.findViewById(R.id.txtTenNV);
            txtMaGoi = itemView.findViewById(R.id.txtMaGoi);
            txtTenGoi = itemView.findViewById(R.id.txtTenGoi);
            txtNgay = itemView.findViewById(R.id.txtNgay);
            txtTienThue = itemView.findViewById(R.id.txtTienThue);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai);
            btnDangKy = itemView.findViewById(R.id.btnDangKy);
        }
    }
}
