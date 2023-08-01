package com.example.quanlyphonggym.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyphonggym.R;
import com.example.quanlyphonggym.adapter.GoiAdapter;
import com.example.quanlyphonggym.dao.GoiDAO;
import com.example.quanlyphonggym.dao.LoaiGoiDAO;
import com.example.quanlyphonggym.model.Goi;
import com.example.quanlyphonggym.model.LoaiGoi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class QLGoiFragment extends Fragment {
    GoiDAO goiDAO;
    RecyclerView recyclerGoi;
    ArrayList<Goi> list;
    GoiAdapter adapter;
    SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlgoi, container, false);

        searchView = view.findViewById(R.id.searchViewG);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterListGoi(newText);
                return true;
            }
        });
        recyclerGoi = view.findViewById(R.id.recyclerGoi);
        FloatingActionButton floatAdd = view.findViewById(R.id.floatAdd);

        goiDAO = new GoiDAO(getContext());
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
    private void filterListGoi(String text) {
        ArrayList<Goi> filteredListGoi = new ArrayList<>();
        for(Goi goi : list){
            if(goi.getTengoi().toLowerCase().contains(text.toLowerCase())){
                filteredListGoi.add(goi);
            }
            if(filteredListGoi.isEmpty()){
//                Toast.makeText(getContext(), "Tên này không có trong danh sách gói tập", Toast.LENGTH_SHORT).show();
            }else{
                adapter.setFilteredListGoi(filteredListGoi);
            }
        }
    }

    private void loadData(){
        list = goiDAO.getDSDauGoi();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerGoi.setLayoutManager(linearLayoutManager);
        adapter = new GoiAdapter(getContext(), list, getDSLoaiGoi(), goiDAO);
        recyclerGoi.setAdapter(adapter);
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_goi, null);
        builder.setView(view);

        EditText edtTenGoi = view.findViewById(R.id.edtTenGoi);
        EditText edtTien = view.findViewById(R.id.edtTien);
        Spinner spnLoaiGoi = view.findViewById(R.id.spnLoaiGoi);

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                getDSLoaiGoi(),
                android.R.layout.simple_list_item_1,
                new String[]{"tenloai"},
                new int[]{android.R.id.text1}
        );
        spnLoaiGoi.setAdapter(simpleAdapter);

        builder.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tengoi = edtTenGoi.getText().toString();
                int tien = Integer.parseInt(edtTien.getText().toString());
                HashMap<String, Object> hs = (HashMap<String, Object>) spnLoaiGoi.getSelectedItem();
                int maloai = (int) hs.get("maloai");

                boolean check = goiDAO.themGoiMoi(tengoi, tien, maloai);
                if(check){
                    Toast.makeText(getContext(), "Thêm loại gói thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                }else {
                    Toast.makeText(getContext(), "Thêm loại gói không thành công", Toast.LENGTH_SHORT).show();
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

    private ArrayList<HashMap<String, Object>> getDSLoaiGoi(){
        LoaiGoiDAO loaiGoiDAO = new LoaiGoiDAO(getContext());
        ArrayList<LoaiGoi> list = loaiGoiDAO.getDSLoaiGoi();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();

        for(LoaiGoi loai : list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("maloai", loai.getId());
            hs.put("tenloai", loai.getTenloai());
            listHM.add(hs);
        }

        return listHM;
    }
}
