package com.bhaptics.bhapticsandroid.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bhaptics.bhapticsandroid.activities.LobbyActivity;
import com.bhaptics.bhapticsandroid.R;
import com.bhaptics.bhapticsmanger.BhapticsManager;
import com.bhaptics.bhapticsmanger.BhapticsModule;
import com.bhaptics.commons.model.BhapticsDevice;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    public static final String TAG = LobbyActivity.class.getSimpleName();

    private LayoutInflater inflater;
    private List<BhapticsDevice> data;
    private int layout;

    private final Activity context;

    private BhapticsManager bhapticsManager;

    public ListViewAdapter(final Activity context, List<BhapticsDevice> defaultDevices) {
        this.context = context;
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = defaultDevices;
        this.layout = R.layout.list_item_bhaptics_device;

        bhapticsManager = BhapticsModule.getBhapticsManager();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=inflater.inflate(layout,parent,false);
        }

        if (position > data.size()) {
            return convertView;
        }

        final BhapticsDevice bhapticsDevice = data.get(position);
        TextView deviceName = convertView.findViewById(R.id.device_name);
        deviceName.setText(bhapticsDevice.getDeviceName());

        TextView devicePosition = convertView.findViewById(R.id.device_position);
        devicePosition.setText(bhapticsDevice.getPosition().toString());

        TextView connection = convertView.findViewById(R.id.device_connection);
        connection.setText(bhapticsDevice.getConnectionStatus().toString());
        Button button = convertView.findViewById(R.id.device_button);

        if (bhapticsDevice.isPaired()) {
            button.setText("Unpair");
        } else {
            button.setText("Pair");
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ");
                if (bhapticsDevice.isPaired()) {
                    bhapticsManager.unpair(bhapticsDevice.getAddress());
                } else {
                    bhapticsManager.pair(bhapticsDevice.getAddress());
                }
            }
        });



        return convertView;
    }

    public void onChangeListUpdate(final List<BhapticsDevice> devices) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                data = devices;
                notifyDataSetChanged();
            }
        });
    }
}
