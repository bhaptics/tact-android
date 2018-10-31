package com.bhaptics.bhapticsandroid.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bhaptics.bhapticsandroid.utils.FileUtils;
import com.bhaptics.bhapticsandroid.R;
import com.bhaptics.tact.nav.NativeHapticPlayer;
import com.bhaptics.tact.nav.model.RotationOption;
import com.bhaptics.tact.nav.model.ScaleOption;
import com.bhaptics.tact.nav.model.TactFile;

import java.util.List;

public class VestDemoActivity extends Activity implements View.OnClickListener, View.OnTouchListener, SeekBar.OnSeekBarChangeListener {
    public static final String TAG = VestDemoActivity.class.getSimpleName();
    private Button backButton, playButton, toggleFrontBackButton;
    private ImageView frontImage, backImage;
    private SeekBar durationSeekBar, intensitySeekBar;
    private TextView durationText, intensityText;

    private Spinner tactFileSpinner;

    private NativeHapticPlayer hapticPlayer;

    private float duration, intensity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vest_demo);

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(this);

        playButton = findViewById(R.id.play_button);
        playButton.setOnClickListener(this);

        toggleFrontBackButton = findViewById(R.id.toggle_front_back_button);
        toggleFrontBackButton.setOnClickListener(this);

        frontImage = findViewById(R.id.front_image_view);
        backImage = findViewById(R.id.back_image_view);

        durationSeekBar = findViewById(R.id.duration_ration_seek_bar);
        durationSeekBar.setOnSeekBarChangeListener(this);
        intensitySeekBar = findViewById(R.id.intensity_ratio_seek_bar);
        intensitySeekBar.setOnSeekBarChangeListener(this);

        durationText = findViewById(R.id.duration_ratio_text_view);
        intensityText = findViewById(R.id.intensity_ration_text_view);

        duration = toRatio(4);
        intensity = toRatio(4);

        durationText.setText(String.valueOf(duration));
        intensityText.setText(String.valueOf(intensity));

        frontImage.setOnTouchListener(this);
        backImage.setOnTouchListener(this);

        hapticPlayer = NativeHapticPlayer.getInstance(this);

        tactFileSpinner = (Spinner) findViewById(R.id.tactot_feedback_spinner);

        String tactotFileFolder = "tactFiles/tactotDemo";
        List<TactFile> files = FileUtils.listFile(this, tactotFileFolder);
        String[] arraySpinner = new String[files.size()];

        for (int index = 0; index < files.size(); index++) {
            arraySpinner[index] = files.get(index).getName();
            hapticPlayer.register(files.get(index).getName(), files.get(index).getContent());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tactFileSpinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_button) {
            finish();
        } else if (v.getId() == R.id.toggle_front_back_button) {
            if ("Front Side".equals(toggleFrontBackButton.getText())) {
                toggleFrontBackButton.setText("Back Side");
                frontImage.setVisibility(View.GONE);
                backImage.setVisibility(View.VISIBLE);
            } else {
                toggleFrontBackButton.setText("Front Side");
                frontImage.setVisibility(View.VISIBLE);
                backImage.setVisibility(View.GONE);
            }
        } else if (v.getId() == R.id.play_button) {
            hapticPlayer.submit(tactFileSpinner.getSelectedItem().toString());
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean isFront = v.getId() == R.id.front_image_view;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int width = frontImage.getWidth();
                int height = frontImage.getHeight();

                float x = event.getX() / width;
                float y = event.getY() / height;
                if (x < 0.2f || x > 0.8f) {
                    break;
                }

                Log.e(TAG, "onTouch: " + isFront + ", " + x + ", " + y);
                float offsetY = -y + 0.5f;
                if (isFront) {
                    float offsetX = (-x + 0.5f) * 0.3f * 360f;
                    hapticPlayer.submitForVest(tactFileSpinner.getSelectedItem().toString(),
                            new RotationOption(offsetX  , offsetY),
                            new ScaleOption(intensity, duration));
                } else {
                    hapticPlayer.submitForVest(tactFileSpinner.getSelectedItem().toString(),
                            new RotationOption((x - 0.5f) * 0.3f * 360f + 180f  , offsetY),
                            new ScaleOption(intensity, duration));
                }


                break;
        }


        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar.getId() == R.id.intensity_ratio_seek_bar) {
            intensity = toRatio(progress);
            String text = String.valueOf(intensity);
            intensityText.setText(text);
        } else if (seekBar.getId() == R.id.duration_ration_seek_bar){
            duration = toRatio(progress);
            String text = String.valueOf(duration);
            durationText.setText(text);
        }

    }

    private float toRatio(int progress) {
        switch (progress) {
            case 0:
                return 0.2f;
            case 1:
                return 0.4f;
            case 2:
                return 0.6f;
            case 3:
                return 0.8f;
            case 4:
                return 1;
            case 5:
                return 2;
            case 6:
                return 3;
            case 7:
                return 4;
            case 8:
                return 5;
            default:
                return 1;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}
