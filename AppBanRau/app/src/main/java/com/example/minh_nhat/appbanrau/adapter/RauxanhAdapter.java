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

public class RauxanhAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arrayrauxanh;

    public RauxanhAdapter(Context context, ArrayList<Sanpham> arrayrauxanh) {
        this.context = context;
        this.arrayrauxanh = arrayrauxanh;
    }

    @Override
    public int getCount() {
        return arrayrauxanh.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayrauxanh.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txttenrauxanh,txtgiarauxanh,txtmotarauxanh;
        public ImageView imgrauxanh;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(view==null)
        {
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.dong_rauxanh,null);
            viewHolder.txttenrauxanh=view.findViewById(R.id.textviewrauxanh);
            viewHolder.txtgiarauxanh=view.findViewById(R.id.textviewgiarauxanh);
            viewHolder.txtmotarauxanh=view.findViewById(R.id.textviewmotarauxanh);
            viewHolder.imgrauxanh=view.findViewById(R.id.imageviewrauxanh);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        Sanpham sanpham= (Sanpham) getItem(position);
        viewHolder.txttenrauxanh.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.txtgiarauxanh.setText("Giá : " +decimalFormat.format(sanpham.getGiasanpham())+ "VND");
        viewHolder.txtmotarauxanh.setMaxLines(2);
        viewHolder.txtmotarauxanh.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotarauxanh.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.noimages)
                .error(R.drawable.error)
                .into(viewHolder.imgrauxanh);
        return view;
    }
}
