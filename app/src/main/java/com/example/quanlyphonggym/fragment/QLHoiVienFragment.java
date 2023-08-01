package com.example.quanlyphonggym.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyphonggym.MainActivity;
import com.example.quanlyphonggym.R;
import com.example.quanlyphonggym.adapter.HoiVienAdapter;
import com.example.quanlyphonggym.dao.HoiVienDAO;
import com.example.quanlyphonggym.model.HoiVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class QLHoiVienFragment extends Fragment {
    HoiVienDAO hoiVienDAO;
    RecyclerView recyclerHoiVien;
    ArrayList<HoiVien> list;
    SearchView searchView;
    HoiVienAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlhoivien, container, false);

        searchView = view.findViewById(R.id.searchViewHV);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterListHV(newText);
                return true;
            }
        });

        recyclerHoiVien = view.findViewById(R.id.recyclerQLHoiVien);
        FloatingActionButton floatAdd = view.findViewById(R.id.floatAdd);

        hoiVienDAO = new HoiVienDAO(getContext());
        loadData();

        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        return view;
    }

    //tìm kiếm
    private void filterListHV(String text) {
        ArrayList<HoiVien> filteredListHV = new ArrayList<>();
        for(HoiVien hoiVien : list){
            if(hoiVien.getHoten().toLowerCase().contains(text.toLowerCase())){
                filteredListHV.add(hoiVien);
            }
            if(filteredListHV.isEmpty()){
//                Toast.makeText(getContext(), "Tên không có trong danh sách hội viên", Toast.LENGTH_SHORT).show();
            } else{
                adapter.setFilteredListHV(filteredListHV);
            }
        }
    }

    private void loadData(){
        list = hoiVienDAO.getDSHoiVien();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerHoiVien.setLayoutManager(linearLayoutManager);
        adapter = new HoiVienAdapter(getContext(), list, hoiVienDAO);
        recyclerHoiVien.setAdapter(adapter);
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_hoivien, null);
        builder.setView(view);

        EditText edtHoTen = view.findViewById(R.id.edtHoTen);
        EditText edtNamSinh = view.findViewById(R.id.edtNamSinh);

        builder.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String hoten = edtHoTen.getText().toString();
                String namsinh = edtNamSinh.getText().toString();

                boolean check = hoiVienDAO.themHoiVien(hoten, namsinh);
                if (check) {
                    Toast.makeText(getContext(), "Thêm hội viên thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                } else {
                    Toast.makeText(getContext(), "Thêm hội viên thất bại", Toast.LENGTH_SHORT).show();
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
}
