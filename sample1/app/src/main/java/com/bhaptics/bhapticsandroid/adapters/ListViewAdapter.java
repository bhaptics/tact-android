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

import com.bhaptics.bhapticsandroid.App;
import com.bhaptics.bhapticsandroid.activities.LobbyActivity;
import com.bhaptics.bhapticsandroid.R;
import com.bhaptics.bhapticsmanger.BhapticsManager;
import com.bhaptics.bhapticsmanger.BhapticsModule;
import com.bhaptics.bhapticsmanger.SdkRequestHandler;
import com.bhaptics.commons.model.BhapticsDevice;
import com.bhaptics.service.SimpleBhapticsDevice;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    public static final String TAG = LobbyActivity.class.getSimpleName();

    private LayoutInflater inflater;
    private List<SimpleBhapticsDevice> data;
    private int layout;

    private final Activity context;

    public ListViewAdapter(final Activity context, List<SimpleBhapticsDevice> defaultDevices) {
        this.context = context;
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = defaultDevices;
        this.layout = R.layout.list_item_bhaptics_device;
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

        final SimpleBhapticsDevice bhapticsDevice = data.get(position);
        TextView deviceName = convertView.findViewById(R.id.device_name);
        deviceName.setText(bhapticsDevice.getDeviceName());

        TextView devicePosition = convertView.findViewById(R.id.device_position);
        devicePosition.setText(SimpleBhapticsDevice.positionToString(bhapticsDevice.getPosition()));

        TextView connection = convertView.findViewById(R.id.device_connection);
        connection.setText(bhapticsDevice.isConnected() ? "Connected" : "Disconnected");



        return convertView;
    }

    public void onChangeListUpdate(final List<SimpleBhapticsDevice> devices) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                data = devices;
                notifyDataSetChanged();
            }
        });
    }
}
