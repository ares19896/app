package com.example.minh_nhat.appbanrau.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minh_nhat.appbanrau.R;
import com.example.minh_nhat.appbanrau.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CuquaAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arraycuqua;

    public CuquaAdapter(Context context, ArrayList<Sanpham> arraycuqua) {
        this.context = context;
        this.arraycuqua = arraycuqua;
    }

    @Override
    public int getCount() {
        return arraycuqua.size();
    }

    @Override
    public Object getItem(int position) {
        return arraycuqua.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txttencuqua,txtgiacuqua,txtmotacuqua;
        public ImageView imgcuqua;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(view==null)
        {
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.dong_cuqua,null);
            viewHolder.txttencuqua=view.findViewById(R.id.textviewtencuqua);
            viewHolder.txtgiacuqua=view.findViewById(R.id.textviewgiacuqua);
            viewHolder.txtmotacuqua=view.findViewById(R.id.textviewmotacuqua);
            viewHolder.imgcuqua=view.findViewById(R.id.imageviewcuqua);
            view.setTag(viewHolder);
        }else {
            viewHolder= (CuquaAdapter.ViewHolder) view.getTag();
        }
        Sanpham sanpham= (Sanpham) getItem(position);
        viewHolder.txttencuqua.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.txtgiacuqua.setText("Giá : " +decimalFormat.format(sanpham.getGiasanpham())+ "VND");
        viewHolder.txtmotacuqua.setMaxLines(2);
        viewHolder.txtmotacuqua.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotacuqua.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.noimages)
                .error(R.drawable.error)
                .into(viewHolder.imgcuqua);
        return view;
    }
}
