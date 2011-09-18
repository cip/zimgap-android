package com.trial.phonegap.plugin.zim;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.openzim.ZIMTypes.ZIMFile;
import org.openzim.ZIMTypes.ZIMReader;

import com.phonegap.*;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

public class DirectoryListingPhoneGapPluginActivity extends DroidGap {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("zimgap","Test");
        Log.d("zimgap",Environment.getExternalStorageDirectory().toString());
        File[] files = Environment.getExternalStorageDirectory().listFiles();
        for (int i=0;i<files.length;i++) {
        	Log.d("zimgap",files[i].getAbsolutePath());
        }
        super.loadUrl("file:///android_asset/www/index.html");
    }
}