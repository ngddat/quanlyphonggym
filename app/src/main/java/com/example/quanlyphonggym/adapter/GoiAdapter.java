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
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyphonggym.R;
import com.example.quanlyphonggym.dao.GoiDAO;
import com.example.quanlyphonggym.model.Goi;

import java.util.ArrayList;
import java.util.HashMap;

public class GoiAdapter extends RecyclerView.Adapter<GoiAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Goi> list;
    private ArrayList<HashMap<String, Object>> listHM;
    private GoiDAO goiDAO;

    public GoiAdapter(Context context, ArrayList<Goi> list, ArrayList<HashMap<String, Object>> listHM, GoiDAO goiDAO) {
        this.context = context;
        this.list = list;
        this.listHM = listHM;
        this.goiDAO = goiDAO;
    }

    public void setFilteredListGoi(ArrayList<Goi> filteredListGoi){
        this.list = filteredListGoi;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_goi, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaGoi.setText("Mã gói: " + list.get(position).getMagoi());
        holder.txtTenGoi.setText("Tên gói: " + list.get(position).getTengoi());
        holder.txtGiaGoi.setText("Giá gói: " + list.get(position).getGiagoi());
        holder.txtMaLoai.setText("Mã loại: " + list.get(position).getMaloai());
        holder.txtTenLoai.setText("Tên loại: " + list.get(position).getTenloai());

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(list.get(holder.getAdapterPosition()));
            }
        });

        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = goiDAO.xoaGoi(list.get(holder.getAdapterPosition()).getMagoi());
                switch (check){
                    case 1:
                        Toast.makeText(context, "Xóa gói thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                        break;
                    case 0:
                        Toast.makeText(context, "Xóa gói không thành công", Toast.LENGTH_SHORT).show();
                        break;
                    case -1:
                        Toast.makeText(context, "Không xóa được gói này vì sách có trong phiếu mượn", Toast.LENGTH_SHORT).show();
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

        TextView txtMaGoi, txtTenGoi, txtGiaGoi, txtMaLoai, txtTenLoai;
        ImageView ivEdit, ivDel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMaGoi = itemView.findViewById(R.id.txtMaGoi);
            txtTenGoi = itemView.findViewById(R.id.txtTenGoi);
            txtGiaGoi = itemView.findViewById(R.id.txtGiaGoi);
            txtMaLoai = itemView.findViewById(R.id.txtMaLoai);
            txtTenLoai = itemView.findViewById(R.id.txtTenLoai);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDel = itemView.findViewById(R.id.ivDel);
        }
    }

    private void showDialog(Goi goi){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_suagoi, null);
        builder.setView(view);

        EditText edtTenGoi = view.findViewById(R.id.edtTenGoi);
        EditText edtTien = view.findViewById(R.id.edtTien);
        TextView txtMaGoi = view.findViewById(R.id.txtMaGoi);
        Spinner spnLoaiGoi = view.findViewById(R.id.spnLoaiGoi);

        txtMaGoi.setText("Mã gói:" + goi.getMagoi());
        edtTenGoi.setText(goi.getTengoi());
        edtTien.setText(String.valueOf(goi.getGiagoi()));

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tengoi"},
                new int[]{android.R.id.text1}
        );
        spnLoaiGoi.setAdapter(simpleAdapter);

        int index = 0;
        int postion = -1;
        for(HashMap<String, Object> item : listHM){
            if((int) item.get("maloai") == goi.getMaloai()){
                postion = index;
            }
            index++;
        }
        spnLoaiGoi.setSelection(postion);

        builder.setNegativeButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tengoi = edtTenGoi.getText().toString();
                int tien = Integer.parseInt(edtTien.getText().toString());
                HashMap<String, Object> hs = (HashMap<String, Object>) spnLoaiGoi.getSelectedItem();
                int maloai = (int) hs.get("maloai");

                boolean check = goiDAO.capNhatThongTinGoi(goi.getMagoi(), tengoi, tien, maloai);
                if(check){
                    Toast.makeText(context, "Cập nhật gói thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                }else {
                    Toast.makeText(context, "Cập nhật gói không thành công", Toast.LENGTH_SHORT).show();
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
        list = goiDAO.getDSDauGoi();
        notifyDataSetChanged();
    }
}
