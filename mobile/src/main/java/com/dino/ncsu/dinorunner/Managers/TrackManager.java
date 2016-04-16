package com.dino.ncsu.dinorunner.Managers;

import com.dino.ncsu.dinorunner.Objects.Tile;
import com.dino.ncsu.dinorunner.Objects.Track;
import com.dino.ncsu.dinorunner.R;

import java.util.ArrayList;

/**
 * Created by jiminfan on 4/11/2016.
 */
public class TrackManager {
    private float scale_width;
    private float scale_height;

    public void setTrack(float scale_x, float scale_y) {
        scale_width = scale_x;
        scale_height = scale_y;
        switch(Track.getInstance().getTrackImageId()) {
            case R.mipmap.track1:
                Track.getInstance().setTrackName("Forst of A'alath");
                Track.getInstance().setTrackImageId(R.mipmap.track1);
                setTiles(scale_width, scale_height);
                break;

        }
    }

    public void setTiles(float scale_width, float scale_height) {

        switch(Track.getInstance().getTrackImageId()) {
            case R.mipmap.track1:
                ArrayList<Tile> tiles = new ArrayList<Tile>();

                tiles.add(new Tile(193 * scale_width, 155 * scale_height, "dirt", 1, 0));

                tiles.add(new Tile(552 * scale_width, 155 * scale_height, "dirt", 0, -1));

                tiles.add(new Tile(552 * scale_width, 364 * scale_height, "dirt", 1, 0));

                tiles.add(new Tile(921 * scale_width, 364 * scale_height, "dirt", 0, -1));

                tiles.add(new Tile(921 * scale_width, 902 * scale_height, "dirt", -1, 0));

                tiles.add(new Tile(573 * scale_width, 902 * scale_height, "dirt", 0, 1));

                tiles.add(new Tile(573 * scale_width, 664 * scale_height, "dirt", -1, 0));

                tiles.add(new Tile(193 * scale_width, 664 * scale_height, "dirt", 0, 1));
        }
    }
}
