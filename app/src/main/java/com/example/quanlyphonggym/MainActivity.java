package com.example.quanlyphonggym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlyphonggym.dao.GoiDAO;
import com.example.quanlyphonggym.dao.NhanVienDAO;
import com.example.quanlyphonggym.fragment.DoanhThuFragment;
import com.example.quanlyphonggym.fragment.MapFragment;
import com.example.quanlyphonggym.fragment.QLGoiFragment;
import com.example.quanlyphonggym.fragment.QLHoiVienFragment;
import com.example.quanlyphonggym.fragment.QLLoaiGoiFragment;
import com.example.quanlyphonggym.fragment.QLTheHoiVienFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolBar);
        FrameLayout frameLayout = findViewById(R.id.frameLayout);
        FragmentManager manager = getSupportFragmentManager();
        QLHoiVienFragment qlHoiVienFragment = new QLHoiVienFragment();
        manager.beginTransaction().replace(R.id.frameLayout, qlHoiVienFragment).commit();

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);
        drawerLayout = findViewById(R.id.drawerLayout);

        View headerLayout = navigationView.getHeaderView(0);
        TextView txtTen = headerLayout.findViewById(R.id.txtTen);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.mQLHoiVien:
                        fragment = new QLHoiVienFragment();
                        break;
                    case R.id.mQLTheHoiVien:
                        fragment = new QLTheHoiVienFragment();
                        break;
                    case R.id.mQLGoi:
                        fragment = new QLGoiFragment();
                        break;
                    case R.id.mQLLoaiGoi:
                        fragment = new QLLoaiGoiFragment();
                        break;
                    case R.id.mQLDoanhThu:
                        fragment = new DoanhThuFragment();
                        break;
                    case R.id.mDiaChi:
                        fragment = new MapFragment();
                        break;
                    case R.id.mDoiMatKhau:
                        showDialogDoiMatKhau();
                        break;
                    case R.id.mThoat:
                        Intent intent = new Intent(MainActivity.this, DangNhapActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    default:
                        fragment = new QLHoiVienFragment();
                        break;
                }
                if(fragment != null){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();

                    toolbar.setTitle(item.getTitle());
                }

                drawerLayout.closeDrawer(GravityCompat.START);

                return false;
            }
        });

        //hiển thị một số chức năng cho Admin
//        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
//        String loaiTK = sharedPreferences.getString("loaitaikhoan", "");
//        if(!loaiTK.equals("Admin")){
//            Menu menu = navigationView.getMenu();
//            menu.findItem(R.id.mQLDoanhThu).setVisible(false);
//        }
//        String hoten = sharedPreferences.getString("hoten", "");
//        txtTen.setText(hoten + "(FPL DN K17)");

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialogDoiMatKhau() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setNegativeButton("Cập nhật", null)
                .setPositiveButton("Hủy", null);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_doimatkhau, null);

        EditText edtOldPass = view.findViewById(R.id.edtPassOld);
        EditText edtNewPass = view.findViewById(R.id.edtNewPass);
        EditText edtReNewPass = view.findViewById(R.id.edtReNewPass);

        builder.setView(view);

        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass = edtOldPass.getText().toString();
                String newPass = edtNewPass.getText().toString();
                String reNewPass = edtReNewPass.getText().toString();
                if(oldPass.equals("") || newPass.equals("") || reNewPass.equals("")){
                    Toast.makeText(MainActivity.this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    if (newPass.equals(reNewPass)) {
                        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
                        String manv = sharedPreferences.getString("manv", "");

                        //cập nhật
                        NhanVienDAO nhanVienDAO = new NhanVienDAO(MainActivity.this);
                        int check = nhanVienDAO.capNhatMatKhau(manv, oldPass, newPass);
                        if (check == 1) {
                            Toast.makeText(MainActivity.this, "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, DangNhapActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }else if(check == 0){
                            Toast.makeText(MainActivity.this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Cập nhật mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Nhập mật khẩu không trùng với nhau", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}