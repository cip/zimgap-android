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
        /* Open Issues
         *  1. open link
         *  	currently clicking links not working.
         *      potential solutions
         *      	1. use some javascript to handle url. (add javascript to index.html)
         *      		+ 
         *      		
         *      	2. Start new activity for link (default behavior). not working yet, see error message: 
         *      	//01-11 12:37:09.895: INFO/System.out(1714): startActivityForResult(intent,-1)
        			//01-11 12:37:09.905: INFO/ActivityManager(1291): Starting activity: Intent { cmp=com.trial.phonegap.plugin.zim/com.phonegap.DroidGap (has extras) }
        			//01-11 12:37:09.905: INFO/System.out(1714): Error loading url into DroidGap - file:///android_asset/www/Gemeinderat+(%C3%96sterreich):android.content.ActivityNotFoundException: Unable to find explicit activity class {com.trial.phonegap.plugin.zim/com.phonegap.DroidGap}; have you declared this activity in your AndroidManifest.xml?
					3. Use plugin shouldOverrideUrlLoading
						Not in phonegap 1.0.0, but available in head revisions		
         *  2. load images
         *  					See also http://stackoverflow.com/questions/3607558/android-show-pictures-in-webview-that-arent-in-filesystem (same problem, but no real solution)
         *  			
         *  	possible somehow in javascript??? 
         *  			Without any support by plugin would be nice. Possible?
         *  				looks interesting: https://developer.mozilla.org/en/Using_files_from_web_applications#Example.3a_Using_object_URLs_to_display_images
         *  					- Appearantly file can only be from file selection dialog.  (some option to create file objects only for mozilla plugins (security)
         *  						(However, may be possible to define it in java code. See also https://github.com/phonegap/phonegap-iphone/issues/280)
         *  					- probably not supported with android browser
         *  					- probably slow 
         *  			b1) (see below) could perhaps work without plugin support. (start some thread which traverses all image tags and replace source with base64 encoded string
         *  			Also  check how kiwix does it. (also combination javascript and native code)
         *  			Something  similar in qt: http://doc.qt.nokia.com/4.7/qtwebkit-bridge.html assignToHTMLImageElement		
         *  	With some plugin support. How? ContentProvider??? Save to local file system?
         *  			a) contentprovider?
         *  				Like http://www.techjini.com/blog/2009/01/10/android-tip-1-contentprovider-accessing-local-file-system-from-webview-showing-image-in-webview-using-content/
         *					Note that at least this example implies saving files to disk. (as in c)) (Actually this seems to a workaround for old android version not being able to open file:// urls in webview   
         *					Something about using same approach, but not storing data in file: http://markmail.org/message/eeistfvc7wpcu6zl#query:contentprovider%20webview+page:1+mid:ypoixyyng52qzhqa+state:results (but in socket, not really that nice...)
         *
         *  			b) inject image data in html on load. (May be slow)
         *  				and may not work: http://code.google.com/p/android/issues/detail?id=596         *
         *  				example: http://stackoverflow.com/questions/5267124/how-to-display-image-with-webview-loaddata
         *  			b1) same as b, but don't everything on load   				
         *  			c) save to local file system (probably slow)
         *  			d) by using WebViewClient.shouldInterceptRequest  	
         *			           	   Problem: 1. Only one WebViewClient can be used per webview, phonegap already uses webviewclient.
         *  						(Would need to be added to  phonegap, similar to shouldOverrideUrlLoadin (see above))
         *  								2. API-Level 11 => Honeycomb or newer !
         *  			e) Intercept urls 
         *  					Example for qt webview: http://qt.gitorious.org/qt-labs/graphics-dojo/blobs/master/url-rendering/main.cpp
         *  					Possible in android?
         *  			f) webkit browser plugin (NPAPI?) 
         *  					not sure wheter possible to interact with javascript. 
         *    
         *  			
         *  		 
         * 		Note that this image issues also exists for a native android app. (only shouldInterceptRequest easier to use, but this function something
         * 				for future devices (only tablets right now))
         *  3. performance
         *  	e.g. Graz ~20s (without images)
         *  		problem is skipping through the compressed stream. (~1mb chunks, article graz pretty at end of chunk)
         *  		potential solutions:
         *  		1. Perhaps froyo fast enough. (uses jit compiler).  (but rather something like 400% boost to expected,
         *  			which is not enough
         *  		2. Use native lib. :( Perhaps not that bad as newer ndk (dec 2010) appearantly support stl.
         */
        
        super.loadUrl("file:///android_asset/www/index.html");
    }
}