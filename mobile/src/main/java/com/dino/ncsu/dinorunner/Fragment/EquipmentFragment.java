package com.dino.ncsu.dinorunner.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dino.ncsu.dinorunner.Activity.CustomItemClickListener;
import com.dino.ncsu.dinorunner.Activity.DinoListAdapter;
import com.dino.ncsu.dinorunner.Activity.ItemListAdapter;
import com.dino.ncsu.dinorunner.Activity.TrackListAdapter;
import com.dino.ncsu.dinorunner.Objects.Inventory;
import com.dino.ncsu.dinorunner.Objects.Item;
import com.dino.ncsu.dinorunner.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EquipmentFragment extends Fragment {
    //private variables for this class
    private ArrayList<Item> helms = new ArrayList<Item>();
    private ArrayList<Item> shoulders = new ArrayList<Item>();
    private ArrayList<Item> chests = new ArrayList<Item>();
    private ArrayList<Item> shirts = new ArrayList<Item>();
    private ArrayList<Item> gloves = new ArrayList<Item>();
    private ArrayList<Item> legs = new ArrayList<Item>();
    private ArrayList<Item> feet = new ArrayList<Item>();
    private ArrayList<Item> cape = new ArrayList<Item>();

    //Keys in Hashmap
    private String[] from = {"image", "name", "desc", "boost"};

    //IDs of views in listview layout
    private int[] to = {R.id.image, R.id.name, R.id.desc, R.id.boost};

    private ArrayList<Integer> imageId = new ArrayList<Integer>();
    private ArrayList<String> desc = new ArrayList<String>();
    private ArrayList<Float> boosts = new ArrayList<Float>();

    private ItemListAdapter mListAdapter;
    private ArrayList<String> items = new ArrayList<String>();
    private List<HashMap<String, String>> allItems;
    private RecyclerView lView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_equipment, container, false);

        setRetainInstance(true);
        setItemLists();

        //Each row of the list stores item name, image and description
        if (allItems == null) {
            allItems = new ArrayList<>();

            for (int i = 0; i < items.size(); i++) {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("image", Integer.toString(imageId.get(i)));
                hm.put("name", items.get(i));
                hm.put("desc", desc.get(i));
                hm.put("boost", Double.toString(boosts.get(i)));
                allItems.add(hm);
            }

            lView = (RecyclerView) rootView.findViewById(R.id.item_list_view);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            lView.setItemAnimator(new DefaultItemAnimator());
            lView.setLayoutManager(mLayoutManager);

            mListAdapter = new ItemListAdapter(getActivity(), allItems, R.layout.itempicker_list_single, from, to, new CustomItemClickListener() {
                @Override
                public void onItemClick(ItemListAdapter adapter, View v, int position) {
                    //Do Nothing
                    Log.d("Clicked item", "" + position);
                }

                @Override
                public void onItemClick(DinoListAdapter adapter, View v, int position) {

                }

                @Override
                protected void onItemClick(TrackListAdapter adapter, View v, int position) {

                }
            });
            lView.setAdapter(mListAdapter);
        }

        // Inflate the layout for this fragment
        return rootView;
    }

    /**
     * Sets the item lists for the appropriate slots
     */
    public void setItemLists() {
        ArrayList<Item> temp = Inventory.getInstance().getEquippableItems();

        //Sets all individual equipment arraylists
        for (int i = 0; i < temp.size(); i++) {
//            Log.d("testBlah!", temp.get(i).getName());
//            Log.d("testBlahSlot!", temp.get(i).getEquipSlot());
            switch (temp.get(i).getEquipSlot()) {
                case "HEAD":
                    helms.add(temp.get(i));
                    imageId.add(temp.get(i).getImageId());
                    desc.add(temp.get(i).getDescription());
                    boosts.add(temp.get(i).getSpeedBoost());
                    items.add(temp.get(i).getName());
                    break;
                case "SHOULDERS":
                    shoulders.add(temp.get(i));
                    imageId.add(temp.get(i).getImageId());
                    desc.add(temp.get(i).getDescription());
                    boosts.add(temp.get(i).getSpeedBoost());
                    items.add(temp.get(i).getName());
                    break;
                case "CHEST":
                    chests.add(temp.get(i));
                    imageId.add(temp.get(i).getImageId());
                    desc.add(temp.get(i).getDescription());
                    boosts.add(temp.get(i).getSpeedBoost());
                    items.add(temp.get(i).getName());
                    break;
                case "SHIRT":
                    shirts.add(temp.get(i));
                    imageId.add(temp.get(i).getImageId());
                    desc.add(temp.get(i).getDescription());
                    boosts.add(temp.get(i).getSpeedBoost());
                    items.add(temp.get(i).getName());
                    break;
                case "GLOVES":
                    gloves.add(temp.get(i));
                    imageId.add(temp.get(i).getImageId());
                    desc.add(temp.get(i).getDescription());
                    boosts.add(temp.get(i).getSpeedBoost());
                    items.add(temp.get(i).getName());
                    break;
                case "LEGS":
                    legs.add(temp.get(i));
                    imageId.add(temp.get(i).getImageId());
                    desc.add(temp.get(i).getDescription());
                    boosts.add(temp.get(i).getSpeedBoost());
                    items.add(temp.get(i).getName());
                    break;
                case "FEET":
                    feet.add(temp.get(i));
                    imageId.add(temp.get(i).getImageId());
                    desc.add(temp.get(i).getDescription());
                    boosts.add(temp.get(i).getSpeedBoost());
                    items.add(temp.get(i).getName());
                    break;
                case "CAPE":
                    cape.add(temp.get(i));
                    imageId.add(temp.get(i).getImageId());
                    desc.add(temp.get(i).getDescription());
                    boosts.add(temp.get(i).getSpeedBoost());
                    items.add(temp.get(i).getName());
                    break;
            }
        }
    }
}
