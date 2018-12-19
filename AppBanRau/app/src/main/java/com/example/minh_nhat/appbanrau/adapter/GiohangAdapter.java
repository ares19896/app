package com.example.minh_nhat.appbanrau.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minh_nhat.appbanrau.R;
import com.example.minh_nhat.appbanrau.activity.GiohangActivity;
import com.example.minh_nhat.appbanrau.activity.MainActivity;
import com.example.minh_nhat.appbanrau.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GiohangAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHang> arraygiohang;

    public GiohangAdapter(Context context, ArrayList<GioHang> arraygiohang) {
        this.context = context;
        this.arraygiohang = arraygiohang;
    }

    @Override
    public int getCount() {
        return arraygiohang.size();
    }

    @Override
    public Object getItem(int position) {
        return arraygiohang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txttengiohang,txtgiagiohang;
        public ImageView imggiohang;
        public Button btngiam,btngiatri,btntang;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.dong_giohang,null);
            viewHolder.txttengiohang=convertView.findViewById(R.id.textviewtengiohang);
            viewHolder.txtgiagiohang=convertView.findViewById(R.id.textviewgiagiohang);
            viewHolder.imggiohang=convertView.findViewById(R.id.imagegiohang);
            viewHolder.btngiam=convertView.findViewById(R.id.buttongiam);
            viewHolder.btngiatri=convertView.findViewById(R.id.buttongiatri);
            viewHolder.btntang=convertView.findViewById(R.id.buttontang);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        GioHang giohang= (GioHang) getItem(position);
        viewHolder.txttengiohang.setText(giohang.getTensp());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.txtgiagiohang.setText(decimalFormat.format(giohang.getGiasp())+" VND");
        Picasso.with(context).load(giohang.getHinhsp())
                .placeholder(R.drawable.noimages)
                .error(R.drawable.error)
                .into(viewHolder.imggiohang);
        viewHolder.btngiatri.setText(giohang.getSoluongsp()+"");

        int sl= Integer.parseInt(viewHolder.btngiatri.getText().toString());
        if(sl <=1){
            viewHolder.btngiam.setVisibility(View.VISIBLE);
        }else if (sl >=1) {
            viewHolder.btngiam.setVisibility(View.VISIBLE);
            viewHolder.btntang.setVisibility(View.VISIBLE);
        }

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btntang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///////////////////////////
                int slmoinhat= Integer.parseInt(finalViewHolder.btngiatri.getText().toString())+1;
                int slht=MainActivity.manggiohang.get(position).getSoluongsp();
                long giaht=MainActivity.manggiohang.get(position).getGiasp();
                MainActivity.manggiohang.get(position).setSoluongsp(slmoinhat);
                long giamoinhat=(giaht*slmoinhat)/slht;
                MainActivity.manggiohang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
                finalViewHolder.txtgiagiohang.setText(decimalFormat.format(giamoinhat)+" VND");
                GiohangActivity.EvenUltil();
                if (slmoinhat>100){
                    finalViewHolder.btntang.setVisibility(View.INVISIBLE);
                    finalViewHolder.btngiam.setVisibility(View.VISIBLE);
                    finalViewHolder.btngiatri.setText(String.valueOf(slmoinhat));
                }else {
                    finalViewHolder.btntang.setVisibility(View.VISIBLE);
                    finalViewHolder.btngiam.setVisibility(View.VISIBLE);
                    finalViewHolder.btngiatri.setText(String.valueOf(slmoinhat));
                }
            }
        });
        viewHolder.btngiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///////////////////////////
                int slmoinhat= Integer.parseInt(finalViewHolder.btngiatri.getText().toString())-1;
                int slht=MainActivity.manggiohang.get(position).getSoluongsp();
                long giaht=MainActivity.manggiohang.get(position).getGiasp();
                MainActivity.manggiohang.get(position).setSoluongsp(slmoinhat);
                long giamoinhat=(giaht*slmoinhat)/slht;
                MainActivity.manggiohang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
                finalViewHolder.txtgiagiohang.setText(decimalFormat.format(giamoinhat)+" VND");
                GiohangActivity.EvenUltil();
                if (slmoinhat<2) {
                    finalViewHolder.btntang.setVisibility(View.VISIBLE);
                    finalViewHolder.btngiam.setVisibility(View.INVISIBLE);
                    finalViewHolder.btngiatri.setText(String.valueOf(slmoinhat));
                }else {
                    finalViewHolder.btntang.setVisibility(View.VISIBLE);
                    finalViewHolder.btngiam.setVisibility(View.VISIBLE);
                    finalViewHolder.btngiatri.setText(String.valueOf(slmoinhat));

                }
            }
        });
        return convertView;
    }
}
