package com.example.minh_nhat.appbanrau.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.minh_nhat.appbanrau.R;
import com.example.minh_nhat.appbanrau.model.GioHang;
import com.example.minh_nhat.appbanrau.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class ChiTietSanPham extends AppCompatActivity {

    Toolbar toolbarChitiet;
    ImageView imgChitiet;
    TextView txten,txtgia,txtmota;
    Spinner spinner;
    Button btndatmua;
    int id=0;
    String TenChiTiet="";
    int GiaChiTiet=0;
    String HinhanhChitiet="";
    String MotaChiTiet="";
    int Idsanpham=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        Anhxa();
        ActionToolbar();
        GetInformation();
        CatchEventSpinner();
        XulyButton();
    }
    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent=new Intent(getApplicationContext(),GiohangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    //

    private void XulyButton() {
        btndatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.manggiohang.size()>0){
                    int s1=Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exits=false;
                    for (int i=0;i<MainActivity.manggiohang.size();i++){
                        if(MainActivity.manggiohang.get(i).getIdsp()==id){
                            MainActivity.manggiohang.get(i).setSoluongsp(MainActivity.manggiohang.get(i).getSoluongsp()+s1);
                            if(MainActivity.manggiohang.get(i).getSoluongsp()>=10){
                                MainActivity.manggiohang.get(i).setSoluongsp(10);
                            }
                            MainActivity.manggiohang.get(i).setGiasp(GiaChiTiet*MainActivity.manggiohang.get(i).getSoluongsp());
                            exits=true;
                        }
                    }
                    if (exits==false){
                        int soluong=Integer.parseInt(spinner.getSelectedItem().toString());
                        long Tonggia=soluong*GiaChiTiet;
                        MainActivity.manggiohang.add(new GioHang(id,TenChiTiet,Tonggia,HinhanhChitiet,soluong));
                    }
                }else {
                    int soluong=Integer.parseInt(spinner.getSelectedItem().toString());
                    long Tonggia=soluong*GiaChiTiet;
                    MainActivity.manggiohang.add(new GioHang(id,TenChiTiet,Tonggia,HinhanhChitiet,soluong));
                }
                Intent intent=new Intent(getApplicationContext(),GiohangActivity.class);
                startActivity(intent);

            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] soluong=new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter=new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInformation() {

        Sanpham sanpham= (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        id=sanpham.getID();
        TenChiTiet=sanpham.getTensanpham();
        GiaChiTiet=sanpham.getGiasanpham();
        HinhanhChitiet=sanpham.getHinhanhsanpham();
        MotaChiTiet=sanpham.getMotasanpham();
        Idsanpham=sanpham.getIDSanpham();
        txten.setText(TenChiTiet);
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        txtgia.setText("Giá : " + decimalFormat.format(GiaChiTiet)+ "VND");
        txtmota.setText(MotaChiTiet);
        Picasso.with(getApplicationContext()).load(HinhanhChitiet)
                .placeholder(R.drawable.noimages)
                .error(R.drawable.error)
                .into(imgChitiet);

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarChitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbarChitiet=findViewById(R.id.toolbarchitietsanpham);
        imgChitiet=findViewById(R.id.imagechitietsanpham);
        txten=findViewById(R.id.textviewtenchitietsanpham);
        txtgia=(TextView) findViewById(R.id.textviewgiachitietsanpham);
        txtmota=findViewById(R.id.textviewmotachitietsanpham);
        spinner=findViewById(R.id.spinner);
        btndatmua=findViewById(R.id.btndatmua);
    }
}
