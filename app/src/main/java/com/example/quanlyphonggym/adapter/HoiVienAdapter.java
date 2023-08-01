package com.example.quanlyphonggym.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyphonggym.R;
import com.example.quanlyphonggym.dao.HoiVienDAO;
import com.example.quanlyphonggym.model.HoiVien;

import java.util.ArrayList;

public class HoiVienAdapter extends RecyclerView.Adapter<HoiVienAdapter.ViewHolder>{
    private Context context;
    private ArrayList<HoiVien> list;
    private HoiVienDAO hoiVienDAO;

    public HoiVienAdapter(Context context, ArrayList<HoiVien> list, HoiVienDAO hoiVienDAO) {
        this.context = context;
        this.list = list;
        this.hoiVienDAO = hoiVienDAO;
    }

    public void setFilteredListHV(ArrayList<HoiVien> filteredListHV){
        this.list = filteredListHV;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_hoivien, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaHV.setText("Mã hội viên: " + list.get(position).getMahv());
        holder.txtHoTen.setText("Họ tên: " + list.get(position).getHoten());
        holder.txtNamSinh.setText("Năm sinh: " + list.get(position).getNamsinh());

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogCapNhatTT(list.get(holder.getAdapterPosition()));
            }
        });

        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = hoiVienDAO.xoaThongTinHV(list.get(holder.getAdapterPosition()).getMahv());
                switch (check){
                    case 1:
                        Toast.makeText(context, "Xóa hội viên thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                        break;
                    case 0:
                        Toast.makeText(context, "Xóa hội viên thất bại", Toast.LENGTH_SHORT).show();
                        break;
                    case -1:
                        Toast.makeText(context, "Hội viên tồn tại có thẻ hội viên, không được phép xóa", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtMaHV, txtHoTen, txtNamSinh;
        ImageView ivEdit, ivDel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMaHV = itemView.findViewById(R.id.txtMaHV);
            txtHoTen = itemView.findViewById(R.id.txtHoTen);
            txtNamSinh = itemView.findViewById(R.id.txtNamSinh);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDel = itemView.findViewById(R.id.ivDel);
        }
    }

    private void showDialogCapNhatTT(HoiVien hoiVien){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_chinhsua_hoivien, null);
        builder.setView(view);

        TextView txtMaHV = view.findViewById(R.id.txtMaHV);
        EditText edtHoTen = view.findViewById(R.id.edtHoTen);
        EditText edtNamSinh = view.findViewById(R.id.edtNamSinh);

        txtMaHV.setText("Mã hội viên: " + hoiVien.getMahv());
        edtHoTen.setText(hoiVien.getHoten());
        edtNamSinh.setText(hoiVien.getNamsinh());

        builder.setNegativeButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String hoten = edtHoTen.getText().toString();
                String namsinh = edtNamSinh.getText().toString();
                int id = hoiVien.getMahv();

                boolean check = hoiVienDAO.capNhatThongTinHV(id, hoten, namsinh);
                if(check){
                    Toast.makeText(context, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                }else {
                    Toast.makeText(context, "Cập nhật thông tin không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void loadData(){
        list.clear();
        list = hoiVienDAO.getDSHoiVien();
        notifyDataSetChanged();
    }
}
