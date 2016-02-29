package com.dino.ncsu.dinorunner;

/**
 * Created by jianpingfan on 2/29/2016.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] dinos;
    private final Integer[] imageId;
    public CustomList(Activity context,
                      String[] dinos, Integer[] imageId) {
        super(context, R.layout.list_single, dinos);
        this.context = context;
        this.dinos = dinos;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(dinos[position]);

        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}