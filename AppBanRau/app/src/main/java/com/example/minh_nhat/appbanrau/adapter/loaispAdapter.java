package com.example.minh_nhat.appbanrau.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.minh_nhat.appbanrau.R;
import com.example.minh_nhat.appbanrau.model.loaisp;
import com.squareup.picasso.Picasso;

public class loaispAdapter extends BaseAdapter {

    ArrayList<loaisp> arraylistloaisp;
    Context context;

    public loaispAdapter(ArrayList<loaisp> arraylistloaisp, Context context) {
        this.arraylistloaisp = arraylistloaisp;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arraylistloaisp.size();
    }

    @Override
    public Object getItem(int position) {
        return arraylistloaisp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        TextView txttenloaisp;
        ImageView imgloaisp;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(view==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.dong_listview_loaisp,null);
            viewHolder.txttenloaisp=view.findViewById(R.id.textviewloaisp);
            viewHolder.imgloaisp=view.findViewById(R.id.imageviewloaisp);
            view.setTag(viewHolder);

        }else {
            viewHolder= (ViewHolder) view.getTag();

        }
        loaisp loaisp= (com.example.minh_nhat.appbanrau.model.loaisp) getItem(position);
        viewHolder.txttenloaisp.setText(loaisp.getTenloaisp());
        Picasso.with(context).load(loaisp.getHinhanhsp())
                .placeholder(R.drawable.noimages)
                .error(R.drawable.error)
                .into(viewHolder.imgloaisp);

        return view;
    }
}
