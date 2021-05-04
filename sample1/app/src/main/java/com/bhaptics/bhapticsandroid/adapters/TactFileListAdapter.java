package com.bhaptics.bhapticsandroid.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bhaptics.bhapticsandroid.App;
import com.bhaptics.bhapticsandroid.R;
import com.bhaptics.bhapticsandroid.models.TactFile;
import com.bhaptics.bhapticsandroid.utils.FileUtils;
import com.bhaptics.bhapticsmanger.SdkRequestHandler;

import java.util.List;

public class TactFileListAdapter  extends BaseAdapter {

    private LayoutInflater inflater;
    private int layout;
    private List<TactFile> files;
    private SdkRequestHandler sdkRequestHandler;

    public TactFileListAdapter(Activity context, String tacFileFolder) {
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout = R.layout.list_item_tact_file;

        files = FileUtils.listFile(context, tacFileFolder);
        sdkRequestHandler = App.getHandler(context);

        for (TactFile file : files) {
            sdkRequestHandler.register(file.getFileName(), file.getContent());
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
                    sdkRequestHandler.submitRegistered(tactFile.getFileName(), tactFile.getFileName(), 1f, 1f, 0, 0);
                }
            });
        }


        TextView deviceName = convertView.findViewById(R.id.file_name);
        deviceName.setText(tactFile.getFileName());
        return convertView;
    }
}
