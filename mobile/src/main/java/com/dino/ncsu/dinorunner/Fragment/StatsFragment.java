package com.dino.ncsu.dinorunner.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dino.ncsu.dinorunner.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatsFragment extends Fragment {

    //Private variables for this class
    private SharedPreferences preferenceSettings;
    private static final int PREFERENCE_MODE_PRIVATE = 0;
    private static final String PREFERENCE_FILE = "_DinoRunnerUserData";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stats, container, false);

        setRetainInstance(true);
        preferenceSettings = getActivity().getSharedPreferences(PREFERENCE_FILE, PREFERENCE_MODE_PRIVATE);
        int currentXP = preferenceSettings.getInt("_xp", 0);
        int currentGold = preferenceSettings.getInt("_gold", 0);

        TextView xpText = (TextView) rootView.findViewById(R.id.xp_text);
        xpText.setText("Experience Points (XP): " + currentXP);

        TextView goldText = (TextView) rootView.findViewById(R.id.gold_text);
        goldText.setText("Gold Earned: " + currentGold);

        // Inflate the layout for this fragment
        return rootView;
    }

}
