package com.example.quanlyphonggym.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyphonggym.R;
import com.example.quanlyphonggym.adapter.LoaiGoiAdapter;
import com.example.quanlyphonggym.dao.LoaiGoiDAO;
import com.example.quanlyphonggym.model.ItemClick;
import com.example.quanlyphonggym.model.LoaiGoi;

import java.util.ArrayList;

public class QLLoaiGoiFragment extends Fragment {
    RecyclerView recyclerLoaiGoi;
    LoaiGoiDAO dao;
    EditText edtLoaiGoi;
    int magoi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlloaigoi, container, false);

        recyclerLoaiGoi = view.findViewById(R.id.recyclerLoaiGoi);
        edtLoaiGoi = view.findViewById(R.id.edtLoaiGoi);
        Button btnThem = view.findViewById(R.id.btnThem);
        Button btnSua = view.findViewById(R.id.btnSua);

        dao = new LoaiGoiDAO(getContext());

        loadData();

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenloai = edtLoaiGoi.getText().toString();

                if(dao.themLoaiGoi(tenloai)){
                    //thông báo + load danh sách
                    loadData();
                    edtLoaiGoi.setText("");
                }else {
                    Toast.makeText(getContext(), "Thêm loại gói thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenloai = edtLoaiGoi.getText().toString();
                LoaiGoi loaiGoi = new LoaiGoi(magoi, tenloai);
                if(dao.thayDoiLoaiGoi(loaiGoi)){
                    loadData();
                    edtLoaiGoi.setText("");
                }else {
                    Toast.makeText(getContext(), "Thay đổi thông tin không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void loadData(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerLoaiGoi.setLayoutManager(linearLayoutManager);
        ArrayList<LoaiGoi> list = dao.getDSLoaiGoi();
        LoaiGoiAdapter adapter = new LoaiGoiAdapter(getContext(), list, new ItemClick() {
            @Override
            public void onClickLoaiGoi(LoaiGoi loaiGoi) {
                edtLoaiGoi.setText(loaiGoi.getTenloai());
                magoi = loaiGoi.getId();
            }
        });
        recyclerLoaiGoi.setAdapter(adapter);
    }
}
