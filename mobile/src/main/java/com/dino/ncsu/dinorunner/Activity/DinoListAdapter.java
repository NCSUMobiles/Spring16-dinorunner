package com.dino.ncsu.dinorunner.Activity;

import android.content.Context;
import android.graphics.Typeface;
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
 * <p>
 * Contains the list adapter that will be used to
 * maintain the list of monsters to be selected by the user.
 */
public class DinoListAdapter extends RecyclerView.Adapter<DinoListAdapter.ViewHolder> {
    //private variables for this class
    private static Context context;
    private List<HashMap<String, String>> aList;
    private int layoutId;
    private String[] from;
    private static int[] to;
    private CustomItemClickListener listener;

    /**
     * Constructor for the class.
     *
     * @param baseContext            Context to inflate the layout for the dinoPicker
     * @param aList                  The list of dinosaurs in a list of hashmaps
     * @param dinopicker_list_single The resource ID of the layout for a single monster item
     * @param from                   The list of keys to attach each resource in the RecyclerView
     * @param to                     The list of IDs of each component of the layout in this view
     * @param listener               The custom click listener to attach to this class
     */
    public DinoListAdapter(Context baseContext, List<HashMap<String, String>> aList, int dinopicker_list_single, String[] from, int[] to, CustomItemClickListener listener) {
        this.context = baseContext;
        this.aList = aList;
        this.layoutId = dinopicker_list_single;
        this.from = from;
        this.to = to;
        this.listener = listener;
    }

    /**
     * Class to refer to each view inside of the data items.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //List of views to maintain in this viewholder
        public ImageView iView;
        public TextView nameView;
        public TextView descView;

        /**
         * Constructor for the inner ViewHolder class
         *
         * @param v The view to get other views from.
         */
        public ViewHolder(View v) {
            super(v);
            Typeface oldLondon = Typeface.createFromAsset(context.getAssets(), "fonts/Blackwood Castle.ttf");

            iView = (ImageView) v.findViewById(to[0]);
            nameView = (TextView) v.findViewById(to[1]);
            nameView.setTypeface(oldLondon);
            descView = (TextView) v.findViewById(to[2]);
        }
    }

    /**
     * Creates the ViewHolder to contain the linearlayout for
     * this list of items
     *
     * @param parent   The parent view to hold
     * @param viewType The type of view to hold
     * @return the ViewHolder for the RecyclerView
     */
    @Override
    public DinoListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(layoutId, parent, false);

        final DinoListAdapter.ViewHolder vh = new DinoListAdapter.ViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.onItemClick(DinoListAdapter.this, v, vh.getPosition());
            }
        });
        return vh;
    }

    /**
     * Binds the item in the list to each component of the view
     * @param holder The holder that holds the view
     * @param position The position in the list to put items in
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.iView.setImageResource(Integer.parseInt(aList.get(position).get(from[0])));
        holder.nameView.setText(aList.get(position).get(from[1]));
        holder.descView.setText(aList.get(position).get(from[2]));
    }

    /**
     * Gets the item count
     *
     * @return The size of the list
     */
    @Override
    public int getItemCount() {
        return aList.size();
    }

    /**
     * Gets an item in the specified position
     *
     * @param position The position to get the item inside of the list
     * @return The item inside of the specified position
     */
    public HashMap<String, String> getItem(int position) {
        return aList.get(position);
    }
}

