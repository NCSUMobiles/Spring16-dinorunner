package com.dino.ncsu.dinorunner;

/**
 * Created by jianpingfan on 2/29/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<Dinosaur>{

    private final Activity context;
    private final Dinosaur[] dinos;
    public CustomList(Activity context,
                      Dinosaur[] dinos) {
        super(context, R.layout.list_single, dinos);
        this.context = context;
        this.dinos = dinos;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(dinos[position].getNameOfDino());

        imageView.setImageResource(dinos[position].getImageId());
        return rowView;
    }
}