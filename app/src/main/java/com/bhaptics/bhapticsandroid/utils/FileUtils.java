package com.bhaptics.bhapticsandroid.utils;

import android.content.Context;
import android.util.Log;

import com.bhaptics.bhapticsandroid.adapters.TactFileListAdapter;
import com.bhaptics.tact.nav.model.TactFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileUtils {
    public static final String TAG = TactFileListAdapter.class.getSimpleName();

    public static List<TactFile> listFile(Context context, String tacFileFolder) {
        try {
            String[] tactosyFiles = context.getAssets().list(tacFileFolder);
            List<TactFile> files = new ArrayList<>();
            for (String tactFile : tactosyFiles) {
                String content = read(context, tacFileFolder + "/" + tactFile);
                Log.e(TAG, "listFile: " + tactFile + ", " + content);

                if (content != null) {
                    TactFile file = new TactFile(tactFile, content);
                    files.add(file);
                }

            }
            return files;
        } catch (IOException e) {
            Log.e(TAG, "listFile: ", e);
        }

        return Collections.emptyList();
    }

    public static String read(Context context, String fileName) {
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte [] buffer = new byte[size];

            is.read(buffer);
            is.close();
            String json = new String(buffer,"UTF-8");

            return json;
        } catch (IOException e) {
            Log.e(TAG, "read: ", e);
        }
        return null;
    }
}
