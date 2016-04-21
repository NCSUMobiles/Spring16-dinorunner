package com.dino.ncsu.dinorunner.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dino.ncsu.dinorunner.Objects.Inventory;
import com.dino.ncsu.dinorunner.Objects.Item;
import com.dino.ncsu.dinorunner.Objects.Player;
import com.dino.ncsu.dinorunner.Pedometer.RunningActivity;
import com.dino.ncsu.dinorunner.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.widget.EditText.OnClickListener;

/**
 * This class holds the necessary functionality for the ItemPickActivity
 * When the user picks items from the list, the previous data along with
 * the list of equipped items is carried over to the RunningActivity.
 */
public class ItemPickActivity extends Activity {
    //private variables for this class
    private ArrayList<Item> helms = new ArrayList<Item>();
    private ArrayList<Item> shoulders = new ArrayList<Item>();
    private ArrayList<Item> chests = new ArrayList<Item>();
    private ArrayList<Item> shirts = new ArrayList<Item>();
    private ArrayList<Item> gloves = new ArrayList<Item>();
    private ArrayList<Item> legs = new ArrayList<Item>();
    private ArrayList<Item> feet = new ArrayList<Item>();
    private ArrayList<Item> cape = new ArrayList<Item>();

    private int helmIndex;
    private int shouldersIndex;
    private int chestsIndex;
    private int shirtsIndex;
    private int glovesIndex;
    private int legIndex;
    private int feetIndex;
    private int capeIndex;

    ArrayList<String> items = new ArrayList<String>();

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
        //Sets indexs for Index array
        Log.d("helms", "" + helms.size());
        Log.d("shoulders", "" + shoulders.size());
        Log.d("chest", "" + chests.size());
        Log.d("shirts", "" + shirts.size());
        Log.d("gloves", "" + gloves.size());
        Log.d("legs", "" + legs.size());
        Log.d("feet", "" + feet.size());
        Log.d("capes", "" + cape.size());

        helmIndex = 0;
        shouldersIndex = helms.size();
        chestsIndex += shoulders.size() + shouldersIndex;
        shirtsIndex += chests.size() + chestsIndex;
        glovesIndex += shirts.size() + shirtsIndex;
        legIndex += gloves.size() + glovesIndex;
        feetIndex += legs.size() + legIndex;
        capeIndex += feet.size() + feetIndex;


    //Sets boosts, desc, imageId of helms
        for (int i = 0; i < helms.size(); i++) {
            imageId.add(helms.get(i).getImageId());
            desc.add(helms.get(i).getDescription());
            boosts.add(helms.get(i).getSpeedBoost());
        }

        //Sets boosts, desc, imageId of shoulders
        for (int i = 0; i < shoulders.size(); i++) {
            imageId.add(shoulders.get(i).getImageId());
            desc.add(shoulders.get(i).getDescription());
            boosts.add(shoulders.get(i).getSpeedBoost());
        }
        //Sets boosts, desc, imageId of chests
        for (int i = 0; i < chests.size(); i++) {
            imageId.add(chests.get(i).getImageId());
            desc.add(chests.get(i).getDescription());
            boosts.add(chests.get(i).getSpeedBoost());
        }
        //Sets boosts, desc, imageId of shirts
        for (int i = 0; i < shirts.size(); i++) {
            imageId.add(shirts.get(i).getImageId());
            desc.add(shirts.get(i).getDescription());
            boosts.add(shirts.get(i).getSpeedBoost());
        }
        //Sets boosts, desc, imageId of gloves
        for (int i = 0; i < gloves.size(); i++) {
            imageId.add(gloves.get(i).getImageId());
            desc.add(gloves.get(i).getDescription());
            boosts.add(gloves.get(i).getSpeedBoost());
        }
        //Sets boosts, desc, imageId of legs
        for (int i = 0; i < legs.size(); i++) {
            imageId.add(legs.get(i).getImageId());
            desc.add(legs.get(i).getDescription());
            boosts.add(legs.get(i).getSpeedBoost());
        }
        //Sets boosts, desc, imageId of feet
        for (int i = 0; i < feet.size(); i++) {
            imageId.add(feet.get(i).getImageId());
            desc.add(feet.get(i).getDescription());
            boosts.add(feet.get(i).getSpeedBoost());
        }
        //Sets boosts, desc, imageId of cape
        for (int i = 0; i < cape.size(); i++) {
            imageId.add(cape.get(i).getImageId());
            desc.add(cape.get(i).getDescription());
            boosts.add(cape.get(i).getSpeedBoost());
        }


    }

    private ArrayList<Integer> imageId = new ArrayList<Integer>();

    private ArrayList<String> desc = new ArrayList<String>();

    private ArrayList<Float> boosts = new ArrayList<Float>();

    private String[] itemTypes = new String[]{"Choose your Helmet", "Choose your Shoulder Item", "Choose your Armor", "Choose your Shirt", "Choose your Gloves", "Choose your Pants", "Choose your Shoes", "Choose your Cape"};


    List<Integer> defaultIndices = new ArrayList<Integer>();

    private RecyclerView lView;
    private List<HashMap<String, String>> itemList;
    private List<HashMap<String, String>> allItems;

    //Keys in Hashmap
    private String[] from = {"image", "name", "desc", "boost"};

    //IDs of views in listview layout
    private int[] to = {R.id.image, R.id.name, R.id.desc, R.id.boost};

    private ItemListAdapter mListAdapter;
    static Inventory inventory = Inventory.getInstance();
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;

    private static final int PREFERENCE_MODE_PRIVATE = 0;
    private static final String PREFERENCE_FILE = "_DinoRunnerUserData";
    private static final String EQUIPPED_TAG = "dino_set_equipped";


    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState The bundle of data carried from the previous activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_pick);
        setItemLists();
        for (int i = 0; i < Inventory.getInstance().getEquippedItemsMap().length; i++) {
            Log.d("test", "EquippedItemMap: " + Inventory.getInstance().getEquippedItemsMap()[i]);
        }

        defaultIndices = Arrays.asList(helmIndex, shouldersIndex, chestsIndex, shirtsIndex, glovesIndex, legIndex, feetIndex, capeIndex, items.size());
        preferenceSettings = getSharedPreferences(PREFERENCE_FILE, PREFERENCE_MODE_PRIVATE);

        Typeface oldLondon = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Blackwood Castle.ttf");

        ((TextView) findViewById(R.id.textView)).setTypeface(oldLondon);
        //Each row of the list stores item name, image and description
        if (itemList == null) {
            itemList = new ArrayList<>();
            allItems = new ArrayList<>();

            Set<String> prevEquipped = preferenceSettings.getStringSet(EQUIPPED_TAG, null);
            boolean isListSet = false;

            if (prevEquipped != null) {
                if (prevEquipped.size() > 0)
                    isListSet = true;

                String[] tempEquip = prevEquipped.toArray(new String[prevEquipped.size()]);
                for (int i = 0; i < tempEquip.length; i++)
                    Log.d("tag" + i, tempEquip[i]);

                for (int j = 0; j < tempEquip.length; j++) {
                    Inventory.getInstance().equipItem(tempEquip[j]);
                    Item item = Inventory.getInstance().getEquippedItems()[j];
                    HashMap<String, String> hm = new HashMap<String, String>();
                    hm.put("image", Integer.toString(item.getImageId()));
                    hm.put("name", item.getName());
                    hm.put("desc", item.getDescription());
                    hm.put("boost", Double.toString(item.getSpeedBoost()));
                    itemList.add(hm);
                }
            }

            for (int i = 0; i < items.size(); i++) {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("image", Integer.toString(imageId.get(i)));
                hm.put("name", items.get(i));
                hm.put("desc", desc.get(i));
                hm.put("boost", Double.toString(boosts.get(i)));
                allItems.add(hm);

                if (!isListSet && defaultIndices.indexOf(i) != -1)
                    itemList.add(hm);
            }

            lView = (RecyclerView) findViewById(R.id.item_list_view);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(ItemPickActivity.this);
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            lView.setItemAnimator(new DefaultItemAnimator());
            lView.setLayoutManager(mLayoutManager);

            mListAdapter = new ItemListAdapter(getBaseContext(), itemList, R.layout.itempicker_list_single, from, to, new CustomItemClickListener() {
                @Override
                public void onItemClick(ItemListAdapter adapter, View v, int position) {
                    createItemDialog(position, adapter);
                }

                @Override
                void onItemClick(DinoListAdapter adapter, View v, int position) {
                    //Do Nothing
                }

                @Override
                void onItemClick(TrackListAdapter adapter, View v, int position) {
                    //Do Nothing
                }
            });
            lView.setAdapter(mListAdapter);
        }
        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.start_button);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Clicked the button!", Toast.LENGTH_LONG).show();
                createRunDialog();
            }
        });
    }

    /**
     * Creates the dialog to choose an item for the specific category
     *
     * @param position The type of item to choose in the dialog
     * @param adapter  The adapter that holds the items currently equipped
     */
    private void createItemDialog(final int position, ItemListAdapter adapter) {
        adapter.getItem(position);
        View dialogView = getLayoutInflater().inflate(R.layout.list_items, null);

        RecyclerView rView = (RecyclerView) dialogView.findViewById(R.id.listView2);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ItemPickActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rView.setItemAnimator(new DefaultItemAnimator());
        rView.setLayoutManager(mLayoutManager);
        final List<HashMap<String, String>> newList = allItems.subList(defaultIndices.get(position), defaultIndices.get(position + 1));

        final AlertDialog newDialog = new AlertDialog.Builder(ItemPickActivity.this, AlertDialog.THEME_HOLO_LIGHT)
                .setTitle(itemTypes[position])
                .setView(dialogView)
                .create();

        rView.setAdapter(new ItemListAdapter(getBaseContext(), newList, R.layout.itempicker_list_single, from, to, new CustomItemClickListener() {
            @Override
            public void onItemClick(ItemListAdapter adapter, View v, int position2) {
                if(position2 != 0) {
                    mListAdapter.setItem(position, newList.get(position2));
                    Collections.swap(allItems, defaultIndices.get(position), defaultIndices.get(position) + position2);
                    newDialog.dismiss();
                }
            }

            @Override
            void onItemClick(DinoListAdapter adapter, View v, int position) {
                //Do nothing for now
            }

            @Override
            void onItemClick(TrackListAdapter adapter, View v, int position) {
                //Do Nothing
            }
        }));

        newDialog.show();
    }

    /**
     * Creates the dialog necessary to set the distance
     * and laps to run for the run
     */
    private void createRunDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_view_options, null);

        final SeekBar pickDist = (SeekBar) dialogView.findViewById(R.id.distance_picker);
        pickDist.setMax(4900);
        pickDist.setProgress(0);
        pickDist.incrementProgressBy(50);
        final EditText distValueText = (EditText) dialogView.findViewById(R.id.dist_value);
        distValueText.setSelection(distValueText.length());

        distValueText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    int j = Integer.parseInt(s.toString());
                    if (j >= 100 && j <= 5000)
                        pickDist.setProgress(j - 100);  //for the seekbar, 0-4900
                } else {
                    pickDist.setProgress(0);  //for the seekbar, 0-4900
                    distValueText.setText("100");
                }
            }
        });

        pickDist.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean user) {
                progress /= 50;
                progress *= 50;
                int posOfCursor = distValueText.getSelectionStart();
                distValueText.setText(String.valueOf(progress + 100));
                distValueText.setSelection(posOfCursor);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        NumberPicker pickLaps = (NumberPicker) dialogView.findViewById(R.id.laps_picker);
        pickLaps.setMinValue(1);
        pickLaps.setMaxValue(30);

        new AlertDialog.Builder(ItemPickActivity.this, AlertDialog.THEME_HOLO_LIGHT)
                .setTitle("Options")
                .setView(dialogView)
                .setPositiveButton("Run", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int distance = Integer.parseInt(((EditText) ((AlertDialog) dialog).findViewById(R.id.dist_value)).getText().toString());

                        if (distance < 100 || distance > 5000)
                            distance = ((SeekBar) ((AlertDialog) dialog).findViewById(R.id.distance_picker)).getProgress() + 100;

                        int laps = ((NumberPicker) ((AlertDialog) dialog).findViewById(R.id.laps_picker)).getValue();

                        for (int i = 0; i < itemList.size(); i++) {
                            HashMap<String, String> iMap = mListAdapter.getItem(i);

                            //EquippedItems.getInstance().setItemAtIndex(i, item);
//                            Item item = new Item(iMap.get(from[1]), 1);
//                            //This will set all of the other values for the item
//                            ItemManager.getInstance().setItemVariables(item);

//                            Log.d("name", item.getName());
//                            Log.d("amt", "" + item.getAmount());
//                            Log.d("descript", item.getDescription());
//                            Log.d("ItemType", "" + item.getType());

                            inventory.equipItem(iMap.get(from[1]));
                            Log.d("ItemAdded", "" + inventory.getEquippedItems()[i].getName());
                            Log.d("MapAdded", "" + inventory.getEquippedItemsMap().length);
                        }

                        Bundle dataBundle = new Bundle();
                        Intent intent = new Intent(getApplicationContext(), RunningActivity.class);
                        dataBundle.putInt("distancePicked", distance);
                        dataBundle.putInt("lapsPicked", laps);

                        preferenceEditor = preferenceSettings.edit();

                        preferenceEditor.putStringSet(EQUIPPED_TAG, new HashSet<String>(Arrays.asList(Inventory.getInstance().getEquippedItemsMap())));
                        preferenceEditor.commit();
                        Player.getInstance().setListOfItems(new ArrayList<Item>(Arrays.asList(inventory.getEquippedItems())));
                        intent.putExtras(dataBundle);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing for right now
                    }
                })
                .show();
    }
}
