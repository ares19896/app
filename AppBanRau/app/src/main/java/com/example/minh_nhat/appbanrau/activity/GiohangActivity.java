package com.example.minh_nhat.appbanrau.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.minh_nhat.appbanrau.R;
import com.example.minh_nhat.appbanrau.adapter.GiohangAdapter;

import java.text.DecimalFormat;

public class GiohangActivity extends AppCompatActivity {

    ListView lvgiohang;
    TextView txtthongbaogiohang;
    static TextView txttongtiengiohang;
    Button btnthanhtoan,btntieptucmuahang;
    Toolbar toolbargiohang;
    GiohangAdapter giohangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        Anhxa();
        ActionToolbar();
        Checkdata();
        EvenUltil();
        CactchOnItemListView();
    }

    private void CactchOnItemListView() {
        lvgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(GiohangActivity.this);
                builder.setTitle("Xác nhận xóa sản phẩm.");
                builder.setMessage("Bạn có muốn xóa sản phẩm này ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(MainActivity.manggiohang.size()<=0){
                            txtthongbaogiohang.setVisibility(View.VISIBLE);
                        }else {
                            MainActivity.manggiohang.remove(position);
                            giohangAdapter.notifyDataSetChanged();
                            EvenUltil();
                            if(MainActivity.manggiohang.size()<=0){
                                txtthongbaogiohang.setVisibility(View.VISIBLE);
                            }else {
                                txtthongbaogiohang.setVisibility(View.INVISIBLE);
                                giohangAdapter.notifyDataSetChanged();
                                EvenUltil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        giohangAdapter.notifyDataSetChanged();
                        EvenUltil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EvenUltil() {
        long tongtien=0;
        for (int i=0;i<MainActivity.manggiohang.size();i++){
            tongtien+=MainActivity.manggiohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        txttongtiengiohang.setText(decimalFormat.format(tongtien)+ "VND");

    }

    private void Checkdata() {
        if(MainActivity.manggiohang.size()<=0){
            giohangAdapter.notifyDataSetChanged();
            txtthongbaogiohang.setVisibility(View.VISIBLE);
            lvgiohang.setVisibility(View.INVISIBLE);
        }else {
            giohangAdapter.notifyDataSetChanged();
            txtthongbaogiohang.setVisibility(View.INVISIBLE);
            lvgiohang.setVisibility(View.VISIBLE);
        }

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void Anhxa() {
        lvgiohang=findViewById(R.id.listviewgiohang);
        txtthongbaogiohang=findViewById(R.id.textviewthongbao);
        txttongtiengiohang=findViewById(R.id.textviewtongtien);
        btnthanhtoan=findViewById(R.id.buttonthanhtoan);
        btntieptucmuahang=findViewById(R.id.buttontieptucmuahang);
        toolbargiohang=findViewById(R.id.toolbargiohang);
        giohangAdapter=new GiohangAdapter(GiohangActivity.this,MainActivity.manggiohang);
        lvgiohang.setAdapter(giohangAdapter);

    }
}
