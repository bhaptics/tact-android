/*
 * Copyright (c) 2019. bHaptics Inc. All Right Reserved
 */

package com.bhaptics.sample3;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bhaptics.commons.model.BhapticsDevice;
import com.bhaptics.commons.model.ConnectionStatus;
import com.bhaptics.commons.utils.LogUtils;

public class BhapticsDeviceArrayAdapter extends ArrayAdapter<BhapticsDevice> {
    public static final String TAG = LogUtils.makeLogTag(BhapticsDeviceArrayAdapter.class);
    public BhapticsDeviceArrayAdapter(final Context activity, int resource) {
        super(activity, resource);
        Log.e(TAG, "BhapticsDeviceArrayAdapter: " + App.getBhapticsManager().getDeviceList().size() );
    }

    @Override
    public int getCount() {
        return App.getBhapticsManager().getDeviceList().size();
    }

    @Override
    public BhapticsDevice getItem(int position) {

        try {
            return App.getBhapticsManager().getDeviceList().get(position);
        } catch (Exception e) {
            Log.w(TAG, "getItem: " + position );
        }
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        final BhapticsDevice device = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.bhaptics_device_item, parent, false);

        }

        if (device == null) {
            return convertView;
        }

        final Button changePosition = convertView.findViewById(R.id.buttonChangePosition);
        final Button buttonPing = convertView.findViewById(R.id.buttonPing);
        final Button buttonPair = convertView.findViewById(R.id.buttonPair);
        final Button buttonChangeIndex = convertView.findViewById(R.id.buttonChangeIndex);
        changePosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "togglePosition: " + device.getAddress() );
                App.getBhapticsManager().togglePosition(device.getAddress());
                notifyDataSetChanged();

            }
        });

        buttonPing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.getBhapticsManager().ping(device.getAddress());
            }
        });

        buttonPair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (device.isPaired()) {
                    App.getBhapticsManager().unpair(device.getAddress());
                } else {
                   App.getBhapticsManager().pair(device.getAddress());
                }
            }
        });

        buttonChangeIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newIndex = device.getIndex() + 1;
                if (newIndex >= 5) {
                    newIndex = 0;
                }
                App.getBhapticsManager().changeIndex(device.getAddress(), newIndex);
            }
        });
//
        if (device.isPaired()) {
            buttonPair.setText("Unpair");
        } else {
            buttonPair.setText("Pair");
        }

        TextView positionText = convertView.findViewById(R.id.textViewPosition);
        TextView deviceNameText = convertView.findViewById(R.id.textViewDeviceName);
        positionText.setText(device.getAddress());
        deviceNameText.setText(
                (device.getConnectionStatus() == ConnectionStatus.Connected ? "Connected " : "Disconnected ") +
               device.getPosition() + ", index: " + device.getIndex());

        return convertView;
    }
}
