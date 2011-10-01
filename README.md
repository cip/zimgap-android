zimgap-android
======

The target of this project is to check the feasibility of implementing a wikipedia offline reader for
[zim files](http://openzim.org) using [Phonegap](http://www.phonegap.com/) on android. Next step would then
to define and implement the zim API calls and to extend it to other platforms.

Open Issues
-----------
### Load images
How to load images from zimfile in phonegap. (=webview). Some potential methods considered:		
			
#### Purely in javascript 
That is, plugin only provides api call which returns image data
Definitely better reuse and less effort, but feasibility very questionable. Variants:

1. Object URLs
	See https://developer.mozilla.org/en/Using_files_from_web_applications#Example.3a_Using_object_URLs_to_display_images
	Appearantly file can only be from file selection dialog.  (some option to create file objects only for mozilla plugins (security)
    (However, may be possible to define it in java code. See also https://github.com/phonegap/phonegap-iphone/issues/280)
 	probably not supported with android browser
    probably slow 
2. Replace image links with data urls 
	(See also 2. below)
	 Works without plugin support:
	 start some thread which traverses all image tags and replace source with base64 encoded string    
   Currently implemented. Not working that bad.     
#### With plugin support

1. contentprovider

	Like http://www.techjini.com/blog/2009/01/10/android-tip-1-contentprovider-accessing-local-file-system-from-webview-showing-image-in-webview-using-content/
    Note that at least this example implies saving files to disk. (as in c)) (Actually this seems to a workaround for old android version not being able to open file:// urls in webview   
    Something about using same approach, but not storing data in file: http://markmail.org/message/eeistfvc7wpcu6zl#query:contentprovider%20webview+page:1+mid:ypoixyyng52qzhqa+state:results (but in socket, not really that nice...)         
2. inject image data in html on load
  
	Probably slow and may not work: http://code.google.com/p/android/issues/detail?id=596         *
    example: http://stackoverflow.com/questions/5267124/how-to-display-image-with-webview-loaddata
	Something  similar in qt: http://doc.qt.nokia.com/4.7/qtwebkit-bridge.html assignToHTMLImageElement
3. same as 2., but don't load everything on load
4. save to local file system

	Main disadvantage is higher complexity, because local file cache needs to be maintained. Would make sense to keep
	as simple as possible, but still extra complexity: For example cleanup after application crash on next app start, or
	what happens if multiple activities active?
5. By using WebViewClient.shouldInterceptRequest

	Nice solution, but two major issues: 
	1. Only one WebViewClient can be used per webview, phonegap already uses webviewclient. (Would need to be added to  phonegap, similar to shouldOverrideUrlLoading (see below))
  	2. API-Level 11 => Honeycomb or newer !
6. Intercept urls

 	Example for qt webview: http://qt.gitorious.org/qt-labs/graphics-dojo/blobs/master/url-rendering/main.cpp
    Apparently not possible in android
    See also http://stackoverflow.com/questions/3607558/android-show-pictures-in-webview-that-arent-in-filesystem (same problem, but no real solution)
7. webkit browser plugin (NPAPI?) 

	Not sure whether possible to interact with javascript.           
8. Other? E.g. how is kiwix doing it?

Note that this image issues also exists for a native android app. (only shouldInterceptRequest easier to use, but this is anyway only
possible for future devices (only tablets right now).

### Performance

For example article Graz in [wikipedia-de.zim](http://openzim.org/download/zim5/wikipedia-de.zim): 

~20s (without images)

Test mobile phone: Orange Boston running Android Eclair.

Problem is skipping through the compressed stream. (~1mb chunks, article graz pretty at end of chunk)

Potential solutions:

1. Perhaps newer android versions/more modern devices fast enough.

	Test phone is pretty low end, uses old android version (Eclair) without a just-in-timer 
compiler. Newer android versions should be faster. However,  rather something like 400% boost to expected,
           			which would not be enough.
2. Reduce cluster size in zim files.
	
	Would reduce compression rate as well. 
	
3. Use native lib. 

	Extra effort (not available right now, but in particular much worse maintenance)  
	Could use native zimlib (Perhaps porting not that much effort as newer ndks (>= dec 2010) apparently support stl.
	Other variant is to only use native xz-library (or even only some parts of it)

Note that performance is not only related to loading of article from zim file: 

For example on orange boston load of Graz/wikipedia-de.zim:

```
Load time 21260 ms
	 readfrom zim 17.4 s
	 toString 0.2 s
	 PluginResult(=JSON encoding) 0.9 s
	 Other (javascript?)  ~ 3.7 s
Render time 485 ms 
```  	

Native readfrom zim should be in range of 200 ms. Therefore at least for newer mobiles phones,
expected that acceptable article load time can be reached. TODO confirm on Galaxy S.
 
 
### Open links
Not exactly unsolvable, but still needs to be decided how to handle this:        

1. use some javascript to handle url. (add javascript to index.html)
2. Start new activity for link (default behavior). not working yet, see error message:

	```
	//01-11 12:37:09.895: INFO/System.out(1714): startActivityForResult(intent,-1)
	//01-11 12:37:09.905: INFO/ActivityManager(1291): Starting activity: Intent { cmp=com.trial.phonegap.plugin.zim/com.phonegap.DroidGap (has extras) }
	//01-11 12:37:09.905: INFO/System.out(1714): Error loading url into DroidGap - file:///android_asset/www/Gemeinderat+(%C3%96sterreich):android.content.ActivityNotFoundException: Unable to find explicit activity class {com.trial.phonegap.plugin.zim/com.phonegap.DroidGap}; have you declared this activity in your AndroidManifest.xml?
    ```
3. Use plugin shouldOverrideUrlLoading

	Not in phonegap 1.0.0, but available in head revisions
      