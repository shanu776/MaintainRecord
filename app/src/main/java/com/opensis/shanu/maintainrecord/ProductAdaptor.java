package com.opensis.shanu.maintainrecord;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


/**
 * Created by Shanu on 8/23/2017.
 */

public class ProductAdaptor extends ArrayAdapter{
    String[] id;
    String[] pname;
    String[] pquantity;
    String[] pprice;
    Context context;
    LayoutInflater inflater;


    public ProductAdaptor(@NonNull Context context,String[] id,String[] pname, String[] pquantity,String[] pprice) {
        super(context,R.layout.model,id);
        this.id = id;
        this.pname=pname;
        this.pquantity=pquantity;
        this.pprice=pprice;
        this.context=context;
    }

    class ViewHolder{
        TextView firebaseid;
        TextView pname;
        TextView pquantity;
        TextView pprice;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null)
        {
            inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.model,null);
        }
        final ViewHolder view=new ViewHolder();
       // view.firebaseid = (TextView) convertView.findViewById(R.id.firebaseid);
        view.pname = (TextView) convertView.findViewById(R.id.showname);
        view.pquantity = (TextView) convertView.findViewById(R.id.showquantity);
        view.pprice = (TextView) convertView.findViewById(R.id.showprice);

        //view.firebaseid.setText(id[position]);
        view.pname.setText(pname[position]);
        view.pquantity.setText(pquantity[position]);
        view.pprice.setText(pprice[position]);

        return convertView;
    }
}
