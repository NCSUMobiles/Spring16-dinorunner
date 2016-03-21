package com.dino.ncsu.dinorunner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Toast;

public class ItemPickActivity extends Activity {
    private byte[] dino;
    private byte[] map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_pick);

        //load meta data here.
        Bundle infoBundle = getIntent().getExtras();
        dino = infoBundle.getByteArray("dinoPicked");
        map = infoBundle.getByteArray("mapPicked");

        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.start_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Clicked the button!", Toast.LENGTH_LONG).show();
                createDialog();
            }
        });
    }

    private void createDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_view_options, null);

        final SeekBar pickDist = (SeekBar) dialogView.findViewById(R.id.distance_picker);
        pickDist.setMax(39900);
        pickDist.setProgress(0);
        pickDist.incrementProgressBy(50);
        final EditText distValueText = (EditText) dialogView.findViewById(R.id.dist_value);

        distValueText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {

                if(!s.toString().isEmpty()) {
                    int j = Integer.parseInt(s.toString());
                    if (j >= 100 && j <= 40000)
                        pickDist.setProgress(j - 100);  //for the seekbar, 0-39900
                } else {
                    pickDist.setProgress(0);  //for the seekbar, 0-39900
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

                        if(distance < 100 || distance > 40000)
                            distance = ((SeekBar) ((AlertDialog) dialog).findViewById(R.id.distance_picker)).getProgress() + 100;

                        int laps = ((NumberPicker) ((AlertDialog) dialog).findViewById(R.id.laps_picker)).getValue();

                        Bundle dataBundle = new Bundle();
                        Intent intent = new Intent(getApplicationContext(), RunningActivity.class);
                        dataBundle.putByteArray("dinoPicked", dino);
                        dataBundle.putByteArray("mapPicked", map);
                        dataBundle.putInt("distancePicked", distance);
                        dataBundle.putInt("lapsPicked", laps);
                        intent.putExtras(dataBundle);
                        Toast.makeText(getApplicationContext(), "Start Running", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        Intent dataIntent = new Intent(getApplicationContext(), TrackPicker.class);
        Bundle bundle = new Bundle();
        bundle.putByteArray("dinoPicked", dino);
        dataIntent.putExtras(bundle);
        dataIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(dataIntent);
    }

}
