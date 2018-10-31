package com.bhaptics.bhapticsandroid.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bhaptics.bhapticsandroid.activities.LobbyActivity;
import com.bhaptics.bhapticsandroid.R;
import com.bhaptics.tact.ble.TactosyDevice;
import com.bhaptics.tact.client.HapticPlayer;

import java.util.List;

public class ListViewAdapter extends BaseAdapter  implements HapticPlayer.DeviceListChangeCallback {
    public static final String TAG = LobbyActivity.class.getSimpleName();

    private LayoutInflater inflater;
    private List<TactosyDevice> data;
    private int layout;

    private Activity context;

    public ListViewAdapter(Activity context, List<TactosyDevice> defaultDevices) {
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
        TactosyDevice bhapticsDevice = data.get(position);
        TextView deviceName = convertView.findViewById(R.id.device_name);
        deviceName.setText(bhapticsDevice.getDeviceName());

        TextView devicePosition = convertView.findViewById(R.id.device_position);
        devicePosition.setText(bhapticsDevice.getPosition().toString());

        TextView connection = convertView.findViewById(R.id.device_connection);
        connection.setText(bhapticsDevice.getConnectionStatus().toString());

        return convertView;
    }

    @Override
    public void onChange(final List<TactosyDevice> list) {
        Log.e(TAG, "onChange: " + list );

        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                data = list;
                notifyDataSetChanged();
            }
        });


    }
}
