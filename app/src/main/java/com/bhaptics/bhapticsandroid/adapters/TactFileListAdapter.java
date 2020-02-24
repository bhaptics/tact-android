package com.bhaptics.bhapticsandroid.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bhaptics.bhapticsandroid.BhapticsModule;
import com.bhaptics.bhapticsandroid.R;
import com.bhaptics.bhapticsandroid.utils.FileUtils;
import com.bhaptics.bhapticsmanger.HapticPlayer;
import com.bhaptics.commons.model.RotationOption;
import com.bhaptics.commons.model.ScaleOption;
import com.bhaptics.commons.model.TactFile;

import java.util.List;

public class TactFileListAdapter  extends BaseAdapter {
    private HapticPlayer hapticPlayer;

    private LayoutInflater inflater;
    private int layout;
    private List<TactFile> files;

    public TactFileListAdapter(Activity context, String tacFileFolder) {
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout = R.layout.list_item_tact_file;

        hapticPlayer = BhapticsModule.getHapticPlayer();
        files = FileUtils.listFile(context, tacFileFolder);

        for (TactFile file : files) {
            hapticPlayer.register(file.getName(), file.getContent());
        }
    }

    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public Object getItem(int position) {
        return files.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final TactFile tactFile = files.get(position);

        if(convertView==null){
            convertView=inflater.inflate(layout,parent,false);
            Button playButton = convertView.findViewById(R.id.play_button);
            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hapticPlayer.submitForVest(tactFile.getName(), new RotationOption(0, 0), new ScaleOption(1, 1));
                }
            });
        }


        TextView deviceName = convertView.findViewById(R.id.file_name);
        deviceName.setText(tactFile.getName());
        return convertView;
    }
}
