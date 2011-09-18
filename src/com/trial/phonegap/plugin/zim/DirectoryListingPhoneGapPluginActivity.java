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
        File zimFile = new File("/sdcard/wikipedia-de.zim");
        Log.d("zimgap",""+zimFile.exists());
        ZIMFile file = new ZIMFile("/sdcard/wikipedia-de.zim");

		// Associate the Zim File with a Reader
		ZIMReader zReader = new ZIMReader(file);

		try {
			// args[1] is the name of the articles that is
 			// to be fetched
			String article = "Graz"; 
			ByteArrayOutputStream articleData = zReader.getArticleData(article,'A');
			if (articleData==null) {
				Log.w("zimgap", "Article \""+article+"\" not found");
			} else {
				String articleText =articleData.toString("utf-8"); 
				Log.d("zimgap","Article read successfully");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        super.loadUrl("file:///android_asset/www/index.html");
    }
}