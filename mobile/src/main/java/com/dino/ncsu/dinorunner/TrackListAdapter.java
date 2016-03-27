package com.dino.ncsu.dinorunner;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Kevin-Lenovo on 3/26/2016.
 */
public class TrackListAdapter extends RecyclerView.Adapter<TrackListAdapter.ViewHolder> {
    private Context context;
    private List<HashMap<String, String>> aList;
    private int layoutId;
    private String[] from;
    private static int[] to;
    private CustomItemClickListener listener;

    public TrackListAdapter(Context baseContext, List<HashMap<String, String>> aList, int trackpicker_list_single, String[] from, int[] to, CustomItemClickListener listener) {
        this.context = baseContext;
        this.aList = aList;
        this.layoutId = trackpicker_list_single;
        this.from = from;
        this.to = to;
        this.listener = listener;
    }

    public void setItem(int position, HashMap<String, String> stringStringHashMap) {
        aList.set(position, stringStringHashMap);
        notifyDataSetChanged();
    }

    /**
     * Class to refer to each view inside of the data items.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //List of views to maintain in this viewholder
        public ImageView iView;
        public TextView nameView;
        public TextView descView;
        public TextView diffView;

        /**
         * Constructor for the inner ViewHolder class
         * @param v The view to get other views from.
         */
        public ViewHolder(View v) {
            super(v);
            iView = (ImageView)v.findViewById(to[0]);
            nameView = (TextView)v.findViewById(to[1]);
            descView = (TextView)v.findViewById(to[2]);
            diffView = (TextView)v.findViewById(to[3]);
        }
    }

    @Override
    public TrackListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(layoutId, parent, false);

        final TrackListAdapter.ViewHolder vh = new TrackListAdapter.ViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.onItemClick(TrackListAdapter.this, v, vh.getPosition());
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.iView.setImageResource(Integer.parseInt(aList.get(position).get(from[0])));
        holder.nameView.setText(aList.get(position).get(from[1]));
        holder.descView.setText(aList.get(position).get(from[2]));
        holder.diffView.setText(aList.get(position).get(from[3]));
    }

    @Override
    public int getItemCount() {
        return aList.size();
    }

    public HashMap<String, String> getItem(int position) {
        return aList.get(position);
    }
}
