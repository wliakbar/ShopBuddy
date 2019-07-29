package com.example.shopbuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class customAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<Item> items=new ArrayList<>();

    public customAdapter(Context context,ArrayList<Item> items){
        mContext=context;
        this.items=items;
    }

    @Override
    public int getCount(){
        return items.size();
    }

    @Override
    public Item getItem(int position){
        return items.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.listview_item,parent,false);
        }
        Item temp=getItem(position);
        TextView itemName1=(TextView)convertView.findViewById(R.id.itemName1);
        TextView quantity1=(TextView)convertView.findViewById(R.id.quantity1);
        TextView price1=(TextView)convertView.findViewById(R.id.price1);

        itemName1.setText(temp.getName());
        quantity1.setText(""+temp.getQuantity());
        price1.setText(""+temp.getPrice());

        return convertView;


    }



}
