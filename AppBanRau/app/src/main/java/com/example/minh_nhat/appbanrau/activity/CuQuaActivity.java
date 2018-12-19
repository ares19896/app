package com.example.minh_nhat.appbanrau.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.minh_nhat.appbanrau.R;
import com.example.minh_nhat.appbanrau.adapter.CuquaAdapter;

import com.example.minh_nhat.appbanrau.model.Sanpham;
import com.example.minh_nhat.appbanrau.ultil.CheckConnection;
import com.example.minh_nhat.appbanrau.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CuQuaActivity extends AppCompatActivity {

    Toolbar toolbarcuqua;
    ListView lvcuqua;
    CuquaAdapter cuquaAdapter;
    ArrayList<Sanpham> mangcuqua;
    int idcuqua=0;
    int page=1;
    View footerview;
    boolean isLoading=false;
    boolean limitdata=false;
    mHandler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cu_qua);
        Anhxa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            GetIdloaisanpham();
            ActionToolbar();
            Getdata(page);
            LoadMoreData();
        }else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối!");
            finish();
        }

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
    private void LoadMoreData() {
        lvcuqua.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham",mangcuqua.get(position));
                startActivity(intent);
            }
        });
        lvcuqua.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstItem, int visibleItem, int totalItem) {
                if(firstItem+visibleItem==totalItem && totalItem!=0 && isLoading==false &&limitdata==false){
                    isLoading=true;
                    ThreadData threadData=new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void Anhxa() {
        toolbarcuqua=findViewById(R.id.toolbarcuqua);
        lvcuqua=findViewById(R.id.listviewcuqua);
        mangcuqua=new ArrayList<>();
        cuquaAdapter= new CuquaAdapter(getApplicationContext(),mangcuqua);
        lvcuqua.setAdapter(cuquaAdapter);
        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview=inflater.inflate(R.layout.progressbar,null);
        mHandler=new mHandler();
    }
    private void ActionToolbar() {
        setSupportActionBar(toolbarcuqua);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarcuqua.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void GetIdloaisanpham() {
        idcuqua=getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("giatriloaisanpham",idcuqua+"");
    }
    private void Getdata(int Page) {
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        String duongdan=Server.đuongdanrauxanh+String.valueOf(Page);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id =0;
                String Tencuqua="";
                int Giacuqua=0;
                String Hinhcuqqua="";
                String Motacuqua="";
                int Idcuqua=0;
                if(response !=null && response.length()!=2){
                    lvcuqua.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray=new JSONArray(response);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject= jsonArray.getJSONObject(i);
                            id=jsonObject.getInt("id");
                            Tencuqua=jsonObject.getString("tensp");
                            Giacuqua=jsonObject.getInt("giasp");
                            Hinhcuqqua=jsonObject.getString("hinhanhsp");
                            Motacuqua=jsonObject.getString("motasp");
                            Idcuqua=jsonObject.getInt("idsanpham");
                            mangcuqua.add(new Sanpham(id ,Tencuqua,Giacuqua,Hinhcuqqua,Motacuqua,Idcuqua));
                            cuquaAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    limitdata=true;
                    lvcuqua.removeFooterView(footerview);
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Đã hết dữ liệu.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param =new HashMap<String,String>();
                param.put("idsp",String.valueOf(idcuqua));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    public class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    lvcuqua.addFooterView(footerview);
                    break;
                case 1:
                    Getdata(++page);
                    isLoading=false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message=mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }

}
