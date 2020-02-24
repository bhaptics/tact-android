package com.bhaptics.bhapticsandroid.utils;

import android.content.Context;
import android.util.Log;

import com.bhaptics.bhapticsandroid.adapters.TactFileListAdapter;
import com.bhaptics.commons.model.TactFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
            StringBuilder textBuilder = new StringBuilder();
            try (Reader reader = new BufferedReader(new InputStreamReader
                    (is, Charset.forName(StandardCharsets.UTF_8.name())))) {
                int c = 0;
                while ((c = reader.read()) != -1) {
                    textBuilder.append((char) c);
                }
            }


            return textBuilder.toString();
        } catch (IOException e) {
            Log.e(TAG, "read: ", e);
        }
        return null;
    }
}
