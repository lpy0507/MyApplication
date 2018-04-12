package com.example.lpy.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lpy.myapplication.R;

/**
 * Created by LPY on 2017/9/22.
 */

public class ListViewAdapter extends BaseAdapter {
    private Context mContext;
    private String[] listString;

    public ListViewAdapter(Context context, String[] listString) {
        this.mContext = context;
        this.listString = listString;
    }

    @Override
    public int getCount() {
        return listString.length;
    }

    @Override
    public Object getItem(int position) {
        return listString[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_layout, null);
            holder.titleTv = (TextView) convertView.findViewById(R.id.titleTv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.titleTv.setText(listString[position]);
        return convertView;
    }

    static class ViewHolder {
        private TextView titleTv;
    }
}
