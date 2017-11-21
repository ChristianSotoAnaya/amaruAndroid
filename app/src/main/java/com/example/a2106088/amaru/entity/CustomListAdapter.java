package com.example.a2106088.amaru.entity;

/**
 * Created by 2107641 on 11/21/17.
 */
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a2106088.amaru.R;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final String[] imgid;
    private final String[] descr;

    public CustomListAdapter(Activity context, String[] itemname, String[] imgid,String[] descr) {
        super(context, R.layout.mylist, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
        this.descr=descr;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        WebView imageView = (WebView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(itemname[position]);
        imageView.setBackgroundColor(0);
        imageView.loadDataWithBaseURL("","<img src='"+imgid[position]+"'/>","text/html", "UTF-8", "");
        extratxt.setText("Description "+descr[position]);
        return rowView;

    };
}