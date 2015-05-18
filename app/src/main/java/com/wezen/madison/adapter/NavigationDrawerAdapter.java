package com.wezen.madison.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wezen.madison.R;
import com.wezen.madison.model.NavigationDrawerItem;

import java.util.List;

/**
 * Created by eder on 5/14/15.
 */
public class NavigationDrawerAdapter extends BaseAdapter {

    private List<NavigationDrawerItem> list;
    private Context context;

    public NavigationDrawerAdapter(List<NavigationDrawerItem> list,Context context){
        this.list = list;
        this.context = context;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //only few items, no viewholder need it
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_nav_drawer,parent,false);
        ImageView icon = (ImageView)view.findViewById(R.id.imageViewNavDrawerItem);
        TextView title = (TextView)view.findViewById(R.id.textViewNavDrawerItem);
        NavigationDrawerItem item = list.get(position);
        icon.setImageResource(item.getIcon());
        title.setText(item.getTitle());
        return view;
    }


}
