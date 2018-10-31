package com.bhaptics.bhapticsandroid.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.bhaptics.bhapticsandroid.R;
import com.bhaptics.bhapticsandroid.adapters.TactFileListAdapter;

public class TactFileActivity extends Activity implements View.OnClickListener{
    public static final String TAG = TactFileActivity.class.getSimpleName();

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tact_file);

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(this);

        String tacFileFolder = "tactFiles/tactosy";
        ListView tactFileListView = findViewById(R.id.tactosy_file_list);
        tactFileListView.setAdapter(new TactFileListAdapter(this, tacFileFolder));


        String tactotFileFolder = "tactFiles/tactot";
        ListView tactotFileListView = findViewById(R.id.tactot_file_list);
        tactotFileListView.setAdapter(new TactFileListAdapter(this, tactotFileFolder));
    }

    @Override
    public void onClick(View v) {
        if (backButton.getId() == v.getId()) {
            finish();
        } else {
            Log.e(TAG, "onClick: ");
        }
//        finish();
    }
}
