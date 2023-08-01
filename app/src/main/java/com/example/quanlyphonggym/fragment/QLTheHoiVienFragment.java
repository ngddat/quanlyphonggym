package com.example.quanlyphonggym.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyphonggym.R;
import com.example.quanlyphonggym.adapter.TheHoiVienAdapter;
import com.example.quanlyphonggym.dao.GoiDAO;
import com.example.quanlyphonggym.dao.HoiVienDAO;
import com.example.quanlyphonggym.dao.TheHoiVienDAO;
import com.example.quanlyphonggym.model.Goi;
import com.example.quanlyphonggym.model.HoiVien;
import com.example.quanlyphonggym.model.TheHoiVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class QLTheHoiVienFragment extends Fragment {
    TheHoiVienDAO theHoiVienDAO;
    RecyclerView recyclerQLTheHoiVien;
    ArrayList<TheHoiVien> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlthehoivien, container, false);

        recyclerQLTheHoiVien = view.findViewById(R.id.recyclerQLTheHoiVien);
        FloatingActionButton floatAdd = view.findViewById(R.id.floatAdd);

        loadData();

        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        return view;
    }

    private void loadData(){
        //data
        theHoiVienDAO = new TheHoiVienDAO(getContext());
        list = theHoiVienDAO.getDSTheHoiVien();

        //adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerQLTheHoiVien.setLayoutManager(linearLayoutManager);

        TheHoiVienAdapter adapter = new TheHoiVienAdapter(list, getContext());
        recyclerQLTheHoiVien.setAdapter(adapter);
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_thehoivien, null);

        Spinner spnHoiVien = view.findViewById(R.id.spnHoiVien);
        Spinner spnGoiTap = view.findViewById(R.id.spnGoiTap);
        getDataHoiVien(spnHoiVien);
        getDataGoiTap(spnGoiTap);
        builder.setView(view);

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //lấy mã hội viên
                HashMap<String, Object> hsHV = (HashMap<String, Object>) spnHoiVien.getSelectedItem();
                int mahv = (int) hsHV.get("mahv");

                //lấy mã gói
                HashMap<String, Object> hsGoiTap = (HashMap<String, Object>) spnGoiTap.getSelectedItem();
                int magoi = (int) hsGoiTap.get("magoi");

                int tien = (int) hsGoiTap.get("giagoi");

                themTheHoiVien(mahv, magoi, tien);
                loadData();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getDataHoiVien(Spinner spnHoiVien){
        HoiVienDAO hoiVienDAO = new HoiVienDAO(getContext());
        ArrayList<HoiVien> list = hoiVienDAO.getDSHoiVien();

        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for(HoiVien hv : list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("mahv", hv.getMahv());
            hs.put("hoten", hv.getHoten());
            listHM.add(hs);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), listHM, android.R.layout.simple_list_item_1, new String[]{"hoten"}, new int[]{android.R.id.text1});
        spnHoiVien.setAdapter(simpleAdapter);
    }

    private void getDataGoiTap(Spinner spnGoiTap){
        GoiDAO goiDAO = new GoiDAO(getContext());
        ArrayList<Goi> list = goiDAO.getDSDauGoi();

        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for(Goi gt : list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("magoi", gt.getMagoi());
            hs.put("tengoi", gt.getTengoi());
            hs.put("giagoi", gt.getGiagoi());
            listHM.add(hs);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), listHM, android.R.layout.simple_list_item_1, new String[]{"tengoi"}, new int[]{android.R.id.text1});
        spnGoiTap.setAdapter(simpleAdapter);
    }

    private void themTheHoiVien(int mahv, int magoi, int tien){
        //lấy mã nhân viên
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
        String manv = sharedPreferences.getString("manv", "");

        //lấy ngày hiện tại
        Date currenTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngay = simpleDateFormat.format(currenTime);

        TheHoiVien theHoiVien = new TheHoiVien(mahv, manv, magoi, ngay, 0, tien);
        boolean kiemtra = theHoiVienDAO.themTheHoiVien(theHoiVien);
        if(kiemtra){
//            Log.i("//CHECK_ql",theHoiVien.toString());
            Toast.makeText(getContext(), "Thêm thẻ thành viên thành công", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(), "Thêm thẻ hội viên thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}
