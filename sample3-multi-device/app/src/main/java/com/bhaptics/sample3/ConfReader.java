package com.bhaptics.sample3;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.bhaptics.commons.utils.LogUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ConfReader {
    public static final String TAG = LogUtils.makeLogTag(ConfReader.class);

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String readTactFile(Context context, int resourceId) {
        try {
            InputStream inputStream = context.getResources().openRawResource(resourceId);

            StringBuilder textBuilder = new StringBuilder();
            try (Reader reader = new BufferedReader(new InputStreamReader
                    (inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
                int c = 0;
                while ((c = reader.read()) != -1) {
                    textBuilder.append((char) c);
                }
            }


            return textBuilder.toString();
        } catch (IOException e) {
            Log.e(TAG, "readTactFile: ", e);
        }

        return null;

    }
}
