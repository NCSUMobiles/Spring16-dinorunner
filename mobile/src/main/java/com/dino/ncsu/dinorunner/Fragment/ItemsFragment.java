package com.dino.ncsu.dinorunner.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dino.ncsu.dinorunner.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_items, container, false);

        setRetainInstance(true);

        // Inflate the layout for this fragment
        return rootView;
    }

}
